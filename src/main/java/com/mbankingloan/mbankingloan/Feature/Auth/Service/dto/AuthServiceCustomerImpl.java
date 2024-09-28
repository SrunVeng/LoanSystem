package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto;


import com.mbankingloan.mbankingloan.Domain.Customer;
import com.mbankingloan.mbankingloan.Domain.LoanAccount;
import com.mbankingloan.mbankingloan.Feature.Auth.Repository.EmailRepository;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.CustomerRegisterVerify;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.LoginCustomer;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.RefreshTokenRequest;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Response.JwtResponse;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.CustomerRepository;
import com.mbankingloan.mbankingloan.Feature.CSAOfficer.Repository.LoanAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class AuthServiceCustomerImpl implements AuthServiceCustomer {

    private final EmailRepository emailRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoderAccessToken;
    private final JwtEncoder jwtEncoderRefreshToken;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final LoanAccountRepository loanAccountRepository;

    // Constructor with @Qualifier applied
    public AuthServiceCustomerImpl(EmailRepository emailRepository,
                                   CustomerRepository customerRepository,
                                   PasswordEncoder passwordEncoder,
                                   JwtEncoder jwtEncoderAccessToken,
                                   JwtEncoder jwtEncoderRefreshToken,
                                   @Qualifier("customerAuthenticationProvider") DaoAuthenticationProvider daoAuthenticationProvider,
                                   JwtAuthenticationProvider jwtAuthenticationProvider, LoanAccountRepository loanAccountRepository) {
        this.emailRepository = emailRepository;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoderAccessToken = jwtEncoderAccessToken;
        this.jwtEncoderRefreshToken = jwtEncoderRefreshToken;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.loanAccountRepository = loanAccountRepository;
    }


    @Override
    public JwtResponse loginCustomer(LoginCustomer loginCustomer) {
        //1. Authenticate
        Authentication auth = new UsernamePasswordAuthenticationToken(loginCustomer.PhoneNumber()
                , loginCustomer.password());
        auth = daoAuthenticationProvider.authenticate(auth);

        String scope = auth.
                getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Generate JWT token to response

        //1. Define JwtClaimSet  (Payload)
        Instant now = Instant.now();
        JwtClaimsSet accessJwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject(auth.getName())
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.MINUTES))
                .audience(List.of("REACT JS"))
                .claim("scope", scope)
                .build();

        JwtClaimsSet refreshJwtClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject(auth.getName())
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(5, ChronoUnit.DAYS))
                .audience(List.of("REACT JS"))
                .claim("scope", scope)
                .build();

        //2. Generate Token
        String accessToken = jwtEncoderAccessToken.encode(JwtEncoderParameters.from(accessJwtClaimsSet)).getTokenValue();
        String refreshToken = jwtEncoderRefreshToken.encode(JwtEncoderParameters.from(refreshJwtClaimsSet)).getTokenValue();


        return JwtResponse.builder()
                .accessToken(accessToken)
                .tokenType("AccessToken")
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JwtResponse refreshTokenCustomer(RefreshTokenRequest refreshTokenRequest) {

        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenRequest.refreshToken());

        auth = jwtAuthenticationProvider.authenticate(auth);


        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));


        Instant now = Instant.now();

        Jwt jwt = (Jwt) auth.getPrincipal();

        // Create access token claims set
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .subject(auth.getName())
                .issuedAt(now)
                .issuer("web")
                .audience(List.of("nextjs", "reactjs"))
                .subject("Access Token")
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .claim("scope", scope)
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(jwtClaimsSet);
        Jwt encodedJwt = jwtEncoderAccessToken.encode(jwtEncoderParameters);

        String accessToken = encodedJwt.getTokenValue();
        String refreshToken = refreshTokenRequest.refreshToken();

        if (Duration.between(Instant.now(), jwt.getExpiresAt()).toDays() < 2) {
            // Create refresh token claims set
            JwtClaimsSet jwtClaimsSetRefreshToken = JwtClaimsSet.builder()
                    .id(auth.getName())
                    .issuedAt(now)
                    .subject(auth.getName())
                    .issuer("web")
                    .audience(List.of("nextjs", "reactjs"))
                    .subject("Refresh Token")
                    .expiresAt(now.plus(7, ChronoUnit.DAYS))
                    .build();
            JwtEncoderParameters jwtEncoderParametersRefreshToken = JwtEncoderParameters.from(jwtClaimsSetRefreshToken);
            Jwt jwtRefreshToken = jwtEncoderRefreshToken.encode(jwtEncoderParametersRefreshToken);
            refreshToken = jwtRefreshToken.getTokenValue();
        }

        return JwtResponse.builder()
                .tokenType("Refresh Token")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


//    @Override
//    public void registerVerifyCustomer(CustomerRegisterVerify customerRegisterVerify) {
//
//        if (!customerRepository.existsByEmail(customerRegisterVerify.email())) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email is incorrect");
//        }
//
//
//        if (!emailRepository.existsByVerificationCode(customerRegisterVerify.verificationCode())) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Verification code is incorrect");
//        }
//
//        if (!customerRegisterVerify.newPassword().equals(customerRegisterVerify.confirmPassword())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
//        }
//
//        Customer customer = customerRepository.findByEmail(customerRegisterVerify.email());
//        customer.setPin(passwordEncoder.encode(customerRegisterVerify.Pin()));
//        customer.setPassword(passwordEncoder.encode(customerRegisterVerify.newPassword()));
//        customer.setIsVerified(true);
//        customer.setIsBlock(false);
//
//        customerRepository.save(customer);
//    }
}

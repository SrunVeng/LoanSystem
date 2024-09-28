package com.mbankingloan.mbankingloan.Feature.Auth.Service.dto;


import com.mbankingloan.mbankingloan.Domain.User;
import com.mbankingloan.mbankingloan.Feature.Admin.Repository.UserRepository;
import com.mbankingloan.mbankingloan.Feature.Auth.Repository.EmailRepository;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.Login;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.RefreshTokenRequest;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Request.StaffRegisterVerify;
import com.mbankingloan.mbankingloan.Feature.Auth.Service.dto.Response.JwtResponse;
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
public class AuthServiceImpl implements AuthService {

    private final EmailRepository emailRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoderAccessToken;
    private final JwtEncoder jwtEncoderRefreshToken;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    // Constructor with @Qualifier applied
    public AuthServiceImpl(EmailRepository emailRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtEncoder jwtEncoderAccessToken,
                           JwtEncoder jwtEncoderRefreshToken,
                           @Qualifier("userAuthenticationProvider") DaoAuthenticationProvider daoAuthenticationProvider,
                           JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.emailRepository = emailRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoderAccessToken = jwtEncoderAccessToken;
        this.jwtEncoderRefreshToken = jwtEncoderRefreshToken;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }


    @Override
    public JwtResponse login(Login login) {
        //1. Authenticate
        Authentication auth = new UsernamePasswordAuthenticationToken(login.StaffId()
                , login.password());
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
    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
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
                .issuedAt(now)
                .subject(auth.getName())
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


    @Override
    public void registerVerify(StaffRegisterVerify staffRegisterVerify) {

        if (!userRepository.existsByEmail(staffRegisterVerify.email())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Email is incorrect");
        }

        if (!emailRepository.existsByVerificationCode(staffRegisterVerify.verificationCode())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Verification code is incorrect");
        }

        if (!staffRegisterVerify.newPassword().equals(staffRegisterVerify.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }


        User user = userRepository.findByEmail(staffRegisterVerify.email());
        user.setPassword(passwordEncoder.encode(staffRegisterVerify.newPassword()));
        user.setIsVerified(true);
        user.setIsBlock(false);
        user.setIsDeleted(false);
        userRepository.save(user);
    }


}

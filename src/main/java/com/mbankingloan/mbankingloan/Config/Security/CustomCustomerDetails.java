package com.mbankingloan.mbankingloan.Config.Security;

import com.mbankingloan.mbankingloan.Domain.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@NoArgsConstructor
@Component
public class CustomCustomerDetails implements UserDetails {

    private Customer customer;

    @Override
    public boolean isEnabled() {

        return !customer.getIsBlock();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return customer.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return customer.getIsAccountNonLocked();
    }

    @Override
    public boolean isAccountNonExpired() {
        return customer.getIsAccountNonExpired();
    }

    @Override
    public String getUsername() {
        return customer.getPhoneNumber();
    }

    @Override
    public String getPassword() {
        return customer.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Assuming customer.getRoles() returns a single role as a List or Set
        return Collections.singletonList(customer.getRoles());
    }


}

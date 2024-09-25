package com.mbankingloan.mbankingloan.Config.Security;


import com.mbankingloan.mbankingloan.Domain.User;
import jdk.jfr.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Getter
@Setter
@NoArgsConstructor
@Component
public class CustomerUserDetails implements UserDetails {

    private User user;

    @Override
    public boolean isEnabled() {
        return !user.getIsDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsAccountNonLocked();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsAccountNonExpired();
    }

    @Override
    public String getUsername() {
        return user.getStaffId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }
}

package ua.balu.toyshop.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.balu.toyshop.constant.Role;
import ua.balu.toyshop.model.Status;
import ua.balu.toyshop.model.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private final String userEmail;
    private final String password;
    private final List<? extends GrantedAuthority> authorities;

    public SecurityUser(String userEmail, String password, List<SimpleGrantedAuthority> authorities) {
        this.userEmail = userEmail;
        this.password = password;
        this.authorities = authorities;
    }

    public static SecurityUser create(User user){
        List<SimpleGrantedAuthority> authorities;
        if (user.getRole()!=null){
            authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
        }else {
            authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.USER.getName()));
        }
        return new SecurityUser(user.getName(),user.getPassword(),authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

package gympoint.backend.workoutservice.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Since we're using JWT, we don't need to load the user from a database
        // Instead, we create a UserDetails object with the username and role from the JWT token
        return new User(
            username,
            "", // No password needed as we're using JWT
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENT"))
        );
    }
} 
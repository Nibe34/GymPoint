package gympoint.backend.authservice.dto;

import lombok.Data;
import java.util.Collection;
import java.util.Collections;

@Data
public class UserDetailsResponse {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Collection<String> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public UserDetailsResponse() {
        this.authorities = Collections.emptyList();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }
} 
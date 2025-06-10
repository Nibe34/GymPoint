package gympoint.backend.authservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminRegisterRequest extends RegisterRequest {
    // Admin doesn't have any additional fields
} 
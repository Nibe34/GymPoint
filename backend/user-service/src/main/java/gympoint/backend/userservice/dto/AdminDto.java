package gympoint.backend.userservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDto extends UserDto {
    // Admin doesn't have any additional fields yet
}
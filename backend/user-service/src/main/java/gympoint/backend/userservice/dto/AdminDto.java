package gympoint.backend.userservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDto extends UserDto {
    private String department;
    private String position;
}
package gympoint.backend.userservice.entity;

import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "admins")
@DiscriminatorValue("ROLE_ADMIN")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {



}
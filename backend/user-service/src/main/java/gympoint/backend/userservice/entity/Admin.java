package gympoint.backend.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admins")
@DiscriminatorValue("ADMIN")
public class Admin extends User {



}
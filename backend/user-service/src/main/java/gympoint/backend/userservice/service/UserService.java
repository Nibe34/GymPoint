package gympoint.backend.userservice.service;


import gympoint.backend.userservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    Optional<User> getById(Long id);
    List<User> getAllUsers();
    User update(Long id, User updatedUser);
    void delete(Long id);
    Optional<User> findByEmail(String email);
}
package go.cows.service;

import go.cows.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    User get(Long id);
    void delete(Long id);
    List<User> getAll();
}

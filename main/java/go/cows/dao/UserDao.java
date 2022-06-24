package go.cows.dao;

import go.cows.model.User;

import java.util.List;

public interface UserDao {
    void save(User user);
    User get(Long id);
    void delete(Long id);
    List<User> getAll();
}

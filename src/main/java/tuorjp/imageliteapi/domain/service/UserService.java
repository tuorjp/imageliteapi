package tuorjp.imageliteapi.domain.service;

import tuorjp.imageliteapi.domain.AccessToken;
import tuorjp.imageliteapi.domain.entity.User;

public interface UserService {
    User getByEmail(String email);
    User save(User user);
    AccessToken authenticate(String email, String password);
}

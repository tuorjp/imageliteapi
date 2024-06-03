package tuorjp.imageliteapi.application.users;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tuorjp.imageliteapi.application.jwt.JwtService;
import tuorjp.imageliteapi.domain.AccessToken;
import tuorjp.imageliteapi.domain.entity.User;
import tuorjp.imageliteapi.domain.exception.DuplicatedTupleException;
import tuorjp.imageliteapi.domain.service.UserService;
import tuorjp.imageliteapi.infra.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        var existentUser = getByEmail(user.getEmail());

        if(existentUser != null) {
            throw new DuplicatedTupleException("User already exists");
        }

        encodePwd(user);

        return userRepository.save(user);
    }

    @Override
    public AccessToken authenticate(String email, String password) {
        var user = getByEmail(email);

        if(user == null) {
            return null;
        }

        var matches = passwordEncoder.matches(password, user.getPassword());

        if(matches){
            return jwtService.generateToken(user);
        }

        return null;
    }

    public void encodePwd(User user) {
        String rawpwd = user.getPassword();
        String encryptedPwd = passwordEncoder.encode(rawpwd);
        user.setPassword(encryptedPwd);
    }
}

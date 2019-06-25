package com.bs.library.user;

import com.bs.library.exception.UserAlreadyExists;
import com.bs.library.jwt.JwtProvider;
import com.bs.library.jwt.JwtResponse;
import com.bs.library.security.LoginForm;
import com.bs.library.security.SignUpForm;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Data
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtProvider jwtProvider;

    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );

        return UserPrinciple.build(user);
    }

    public Boolean checkIfExistsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void registerUser(SignUpForm signUpForm) {
        if (userRepository.existsByUsername(signUpForm.getUsername()) || userRepository.existsByUsername(signUpForm.getEmail())) {
            throw new UserAlreadyExists();
        } else {
            User user = new User(signUpForm.getUsername(),
                    encoder.encode(signUpForm.getPassword()), signUpForm.getEmail(), signUpForm.getRole());
            userRepository.save(user);
        }
    }

    public JwtResponse authenticateUser(LoginForm loginForm) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getUsername(),
                        loginForm.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

}

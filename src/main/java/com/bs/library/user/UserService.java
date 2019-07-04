package com.bs.library.user;

import com.bs.library.exception.UserAlreadyExists;
import com.bs.library.exception.UserAccountBalanceException;
import com.bs.library.exception.UserNotFoundException;
import com.bs.library.jwt.JwtProvider;
import com.bs.library.jwt.JwtResponse;
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

import java.math.BigDecimal;


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

    public User getUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with -> username or email : " + username)
                );

        return user;
    }

    public Boolean checkAccountBalance(User user, BigDecimal price) {
        BigDecimal value = user.getAccountBalance().subtract(price);
        if (value.compareTo(BigDecimal.ZERO) > 0) {
            return true;
        } else {
            throw new UserAccountBalanceException();
        }
    }

    public void registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExists();
        } else {
            User current = new User(user.getUsername(),
                    encoder.encode(user.getPass()), user.getEmail(), user.getRole(), user.getAccountBalance());
            userRepository.save(current);
        }
    }

    public JwtResponse authenticateUser(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        return new JwtResponse(jwt);
    }

    public void updateUser(Long id, User book) {
        User currentUser;
        currentUser = userRepository.findById(id).map(
                user -> {
                    user.setUsername(book.getUsername());
                    user.setEmail(book.getEmail());
                    user.setPass(book.getPass());
                    user.setRole(book.getRole());
                    user.setOrders(book.getOrders());
                    user.setAccountBalance(book.getAccountBalance());
                    return user;
                }
        ).orElseThrow(UserNotFoundException::new);

        userRepository.save(currentUser);
    }

}

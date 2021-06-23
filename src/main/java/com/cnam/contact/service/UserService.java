package com.cnam.contact.service;

import com.cnam.contact.bean.Role;
import com.cnam.contact.bean.User;
//import com.cnam.contact.exception.UserAlreadyExistException;
import com.cnam.contact.exception.UserAlreadyExistException;
import com.cnam.contact.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerNewUserAccount(User postUser) throws UserAlreadyExistException {
        if (userExists(postUser.getUsername())) {
            throw new UserAlreadyExistException("Utilisateur existant: "
                    + postUser.getUsername());
        }
        User user = new User();
        String passwordEncoded = passwordEncoder.encode(postUser.getPasswordPlain());

        user.setFirstName(postUser.getFirstName());
        user.setLastName(postUser.getLastName());
        user.setPassword(passwordEncoded);
        user.setPasswordPlain(passwordEncoded);
        user.setMatchingPassword(passwordEncoded);
        user.setUsername(postUser.getUsername());
        user.setRoles(Collections.singletonList(new Role("user")));
        System.out.println("saving : " + user);

        return userRepository.save(user);
    }

    private boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> optionalUser = Optional.ofNullable(userRepository.findByUsername(username));

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new UsernameNotFoundException(MessageFormat.format("Aucun utilisateur {0} de trouver.", username));
        }
    }

    public User getLoggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return (User)loadUserByUsername(username);
    }
}

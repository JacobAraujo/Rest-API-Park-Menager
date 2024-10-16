package com.JacobAraujo.park_api.service;

import com.JacobAraujo.park_api.entity.User;
import com.JacobAraujo.park_api.exception.EntityNotFoundException;
import com.JacobAraujo.park_api.exception.UsernameUniqueViolationException;
import com.JacobAraujo.park_api.exception.InvalidPasswordException;
import com.JacobAraujo.park_api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username %s already exists", user.getUsername()));
        }
    }

    @Transactional(readOnly = true)
    public User searchById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User id=%s not found.", id))
        );
    }

    @Transactional
    public User updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)){
            throw new InvalidPasswordException("New password is not the same of confirm password.");
        }
        User user = searchById(id);
        if (!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new InvalidPasswordException("Password is wrong.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        return user;
    }

    @Transactional(readOnly = true)
    public List<User> searchAll() {
        return userRepository.findAll();
    }

    public User searchByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("User username=%s not found.", username))
        );
    }

    public User.Role searchRoleByUsername(String username) {
        return searchByUsername(username).getRole(); // diferente -> ver se funciona
    }
}

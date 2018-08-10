package com.baciu.filestorage.service;

import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.converter.UserConverter;
import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.exception.EmailExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.exception.UsernameExistsException;
import com.baciu.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    public UserDTO getUser(Long id) throws UserNotExistsException {
        return userConverter.toDTO(userRepository.findById(id)
                .orElseThrow(UserNotExistsException::new), true);
    }

    public UserDTO addUser(UserDTO userDTO) throws EmailExistsException, UsernameExistsException {
        User user = userConverter.toEntity(userDTO);

        if (userRepository.findByUsername(user.getUsername()) != null)
            throw new UsernameExistsException();

        if (userRepository.findByEmail(user.getEmail()) != null)
            throw new EmailExistsException();

        return userConverter.toDTO(userRepository.save(user), false);
    }

    public UserDTO getByEmail(String email) throws UserNotExistsException {
        Optional<User> user = userRepository.findByEmail(email);
        return userConverter.toDTO(user.orElseThrow(UserNotExistsException::new), false);
    }

    public UserDTO updateUser(UserDTO userDTO) throws EmailExistsException, UsernameExistsException, UserNotExistsException {
        User user = userConverter.toEntity(userDTO);
        User existedUser = userRepository
                .findById(user.getId())
                .orElseThrow(UserNotExistsException::new);

        if (usernameExists(user.getUsername(), existedUser.getUsername()))
           throw new UsernameExistsException();

        if (emailExists(user.getEmail(), existedUser.getEmail()))
            throw new EmailExistsException();

        User newUser = User.builder()
                .id(existedUser.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(existedUser.getPassword())
                .build();

        return userConverter.toDTO(userRepository.save(newUser), false);
    }

    private boolean usernameExists(String newUsername, String existedUsername) throws UsernameExistsException {
        userRepository.findByUsername(newUsername).orElseThrow(UsernameExistsException::new);
        return !newUsername.equals(existedUsername);
    }

    private boolean emailExists(String newEmail, String existedEmail) throws EmailExistsException {
        userRepository.findByEmail(newEmail).orElseThrow(EmailExistsException::new);
        return !newEmail.equals(existedEmail);
    }

    public void deleteUser(Long id) throws UserNotExistsException {
        User user = userRepository.findById(id).orElseThrow(UserNotExistsException::new);
        userRepository.delete(user);
    }
}

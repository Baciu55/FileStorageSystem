package com.baciu.filestorage.service;

import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.converter.UserConverter;
import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.exception.EmailExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.exception.UsernameExistsException;
import com.baciu.filestorage.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    public UserDTO getUser(Long id) throws UserNotExistsException {
        return userConverter.toDTO(userRepository.findById(id)
                .orElseThrow(UserNotExistsException::new));
    }

    public UserDTO addUser(UserDTO userDTO) throws EmailExistsException, UsernameExistsException {
        User user = userConverter.toEntity(userDTO);

        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new UsernameExistsException();

        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EmailExistsException();

        return userConverter.toDTO(userRepository.save(user));
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

        copyNotNullValues(user, existedUser);

        return userConverter.toDTO(userRepository.save(existedUser));
    }

    private void copyNotNullValues(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private boolean usernameExists(String newUsername, String existedUsername) {
        Boolean usernameExists = userRepository.findByUsername(newUsername).isPresent();
        return newUsername.equals(existedUsername) && !usernameExists;
    }

    private boolean emailExists(String newEmail, String existedEmail) {
        Boolean emailExists = userRepository.findByEmail(newEmail).isPresent();
        return newEmail.equals(existedEmail) && !emailExists;
    }

    public void deleteUser(Long id) throws UserNotExistsException {
        User user = userRepository.findById(id).orElseThrow(UserNotExistsException::new);
        userRepository.delete(user);
    }
}

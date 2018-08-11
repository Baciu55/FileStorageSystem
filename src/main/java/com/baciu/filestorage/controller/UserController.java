package com.baciu.filestorage.controller;

import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.exception.EmailExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.exception.UsernameExistsException;
import com.baciu.filestorage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) throws UserNotExistsException {
        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
    }

    @PostMapping("users")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO) throws EmailExistsException, UsernameExistsException {
        return new ResponseEntity<>(userService.addUser(userDTO), HttpStatus.OK);
    }

    @PutMapping("users")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO) throws EmailExistsException, UsernameExistsException, UserNotExistsException {
        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) throws UserNotExistsException {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

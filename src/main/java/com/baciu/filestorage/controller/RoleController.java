package com.baciu.filestorage.controller;

import com.baciu.filestorage.dto.RoleDTO;
import com.baciu.filestorage.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins="*")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("roles")
    public ResponseEntity<?> getRoles() {
        return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
    }

    @PostMapping("roles")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleDTO role) {
        return new ResponseEntity<>(roleService.addRole(role), HttpStatus.OK);
    }

}

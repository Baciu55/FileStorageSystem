package com.baciu.filestorage.controller;

import com.baciu.filestorage.converter.GroupConverter;
import com.baciu.filestorage.dto.FileDTO;
import com.baciu.filestorage.dto.GroupDTO;
import com.baciu.filestorage.dto.UserDTO;
import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.exception.GroupNotExistsException;
import com.baciu.filestorage.exception.UserNotExistsException;
import com.baciu.filestorage.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins="*")
public class GroupController {

    @Autowired
    private GroupService groupService;

     @GetMapping("groups/{id}")
    public ResponseEntity<?> getGroup(@PathVariable Long id) throws GroupNotExistsException {
        return new ResponseEntity<>(groupService.getGroup(id), HttpStatus.OK);
    }

    @PostMapping("groups")
    public ResponseEntity<?> addGroup(@Valid @RequestBody GroupDTO groupDTO) {
        return new ResponseEntity<>(groupService.addGroup(groupDTO), HttpStatus.OK);
    }

    @PostMapping("groups/{groupId}/users")
    public ResponseEntity<?> addGroupUsers(@RequestBody UserDTO userDTO, @PathVariable("groupId") Long groupId) throws UserNotExistsException, GroupNotExistsException {
        return new ResponseEntity<>(groupService.addGroupUsers(userDTO, groupId), HttpStatus.OK);
    }

    @PostMapping("groups/{groupId}/files")
    public ResponseEntity<?> addGroupFiles(@RequestBody Set<FileDTO> filesDTO, @PathVariable("groupId") Long groupId) throws GroupNotExistsException {
        return new ResponseEntity<>(groupService.addGroupFiles(filesDTO, groupId), HttpStatus.OK);
    }

    @PostMapping("groups/{groupId}/file")
    public ResponseEntity<?> addGroupFile(@RequestBody FileDTO fileDTO, @PathVariable("groupId") Long groupId) throws GroupNotExistsException {
        return new ResponseEntity<>(groupService.addGroupFile(fileDTO, groupId), HttpStatus.OK);
    }

    @PutMapping("groups")
    public ResponseEntity<?> updateGroup(@RequestBody GroupDTO groupDTO) {
        return new ResponseEntity<>(groupService.updateGroup(groupDTO), HttpStatus.OK);
    }

    @PutMapping("groups/{groupId}/user/add/{userId}")
    public ResponseEntity<?> addUserToGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        return new ResponseEntity<>(groupService.addUser(groupId, userId), HttpStatus.OK);
    }

    @DeleteMapping("groups/{groupId}")
    public ResponseEntity<?> deleteGroup(@PathVariable("groupId") Long groupId) throws GroupNotExistsException {
        return new ResponseEntity<>(groupService.deleteGroup(groupId), HttpStatus.OK);
    }

    @DeleteMapping("groups/{groupId}/files/{fileId}")
    public ResponseEntity<?> deleteGroupFile(@PathVariable("groupId") Long groupId, @PathVariable("fileId") Long fileId) {
        return new ResponseEntity<>(groupService.deleteGroupFile(groupId, fileId), HttpStatus.OK);
    }

}

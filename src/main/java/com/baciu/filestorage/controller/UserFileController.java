package com.baciu.filestorage.controller;

import com.baciu.filestorage.dto.UserFileDTO;
import com.baciu.filestorage.exception.UserFileExistsException;
import com.baciu.filestorage.exception.UserFileNotExistsException;
import com.baciu.filestorage.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@CrossOrigin(origins="*")
public class UserFileController {

    @Autowired
    private UserFileService userFileService;

    @GetMapping("/files/{fileId}")
    public ResponseEntity<?> getFile(@PathVariable("fileId") Long fileId) throws IOException, UserFileNotExistsException {
        ByteArrayResource resource = userFileService.getFile(fileId);

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @GetMapping("files/users/{userId}")
    public ResponseEntity<?> getUserFiles(@PathVariable("userId") Long userId) throws UserFileNotExistsException {
        return new ResponseEntity<>(userFileService.getUserFiles(userId), HttpStatus.OK);
    }

    @PostMapping("files/users/{userId}")
    public ResponseEntity<?> addFile(@RequestParam("file") MultipartFile multipartFile, @Valid @RequestBody UserFileDTO userFileDTO, @PathVariable("userId") Long userId) throws UserFileExistsException {
        return new ResponseEntity<>(userFileService.addFile(multipartFile, userFileDTO, userId), HttpStatus.OK);
    }

    @DeleteMapping("files/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable Long fileId) throws Exception {
        userFileService.deleteFile(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("files/getIt")
    public ResponseEntity<?> getImg() throws IOException {
        return new ResponseEntity<>(userFileService.getImg(), HttpStatus.OK);
    }

}

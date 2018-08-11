package com.baciu.filestorage.service;

import com.baciu.filestorage.converter.UserFileConverter;
import com.baciu.filestorage.dto.UserFileDTO;
import com.baciu.filestorage.entity.UserFile;
import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.exception.UserFileExistsException;
import com.baciu.filestorage.exception.UserFileNotExistsException;
import com.baciu.filestorage.repository.UserFileRepository;
import com.baciu.filestorage.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Set;

@Service
public class UserFileService {

    private final static String UPLOAD_FOLDER = "uploads/";

    @Autowired
    private UserFileRepository userFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFileConverter userFileConverter;

    public UserFile addFile(MultipartFile multipartFile, UserFileDTO userFileDTO, Long userId) throws UserFileExistsException {
        try {
            uploadFile(multipartFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        UserFile userFile = UserFile.builder()
                .name(userFileDTO.getName())
                .description(userFileDTO.getDescription())
                .path(UPLOAD_FOLDER + multipartFile.getOriginalFilename())
                .size(multipartFile.getSize())
                .user(User.builder().id(userId).build())
                .build();

        return userFileRepository.save(userFile);
    }

    public Set<UserFileDTO> getUserFiles(Long userId) throws UserFileNotExistsException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserFileNotExistsException::new);
        return userFileConverter.toDTO(user.getUserFiles());
    }

    public void deleteFile(Long fileId) throws UserFileNotExistsException, IOException {
        UserFile userFile = userFileRepository.findById(fileId)
                .orElseThrow(UserFileNotExistsException::new);
        Path path = Paths.get(UPLOAD_FOLDER + userFile.getName());
        Files.delete(path);

        userFileRepository.delete(userFile);
    }

    public ByteArrayResource getFile(Long id) throws IOException, UserFileNotExistsException {
        UserFile userFile = userFileRepository.findById(id)
                .orElseThrow(UserFileNotExistsException::new);

        File file = new File(Paths.get(UPLOAD_FOLDER) + userFile.getName());

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return resource;
    }

    public ByteArrayResource getImg() throws IOException {
        File uploadFile = new File(Paths.get(UPLOAD_FOLDER) + "/Java 8. Przewodnik doświadczonego programisty - Cay S. Horstmann [HQ].pdf");

        Path path = Paths.get(uploadFile.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        return resource;
    }

    // test IOException
    // IOException wrong handling
    private void uploadFile(MultipartFile file) throws IOException, UserFileExistsException {
        Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
        if (Files.exists(path))
            throw new UserFileExistsException();

        byte[] bytes = file.getBytes();
        Files.write(path, bytes);
    }
}

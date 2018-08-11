package com.baciu.filestorage;


import com.baciu.filestorage.converter.UserConverter;
import com.baciu.filestorage.dto.RoleDTO;
import com.baciu.filestorage.entity.UserFile;
import com.baciu.filestorage.entity.Group;
import com.baciu.filestorage.entity.Role;
import com.baciu.filestorage.entity.User;
import com.baciu.filestorage.exception.EmailExistsException;
import com.baciu.filestorage.exception.RoleExistsException;
import com.baciu.filestorage.exception.UsernameExistsException;
import com.baciu.filestorage.repository.GroupRepository;
import com.baciu.filestorage.repository.RoleRepository;
import com.baciu.filestorage.repository.UserFileRepository;
import com.baciu.filestorage.repository.UserRepository;
import com.baciu.filestorage.service.GroupService;
import com.baciu.filestorage.service.RoleService;
import com.baciu.filestorage.service.UserService;
import org.hibernate.validator.constraints.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserFileRepository userFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GroupRepository groupRepository;

    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Override
    public void run(String...args) throws Exception {
        logger.info("@@@@Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));
        initData();
    }


    private void initData() throws RoleExistsException, EmailExistsException, UsernameExistsException {
        Role role1 = Role.builder()
                .name("ROLE_USER")
                .build();
        Role role2 = Role.builder()
                .name("ROLE_ADMIN")
                .build();

        roleRepository.save(role1);
        roleRepository.save(role2);

        User user1 = User.builder()
                .username("username")
                .email("email@gmail.com")
                .password("13456")
                .build();
        User user2 = User.builder()
                .username("testowy")
                .email("email12@gmail.com")
                .password("654321")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        Group group1 = Group.builder()
                .name("grupa1")
                .description("grupa numer jeden")
                .build();
        Group group2 = Group.builder()
                .name("grupa2")
                .description("grupa numer dwa")
                .build();
        groupRepository.save(group1);
        groupRepository.save(group2);

        UserFile userFile = UserFile.builder()
                .name("my photo")
                .description("me and my family")
                .path("/photos/photo1.jpg")
                .size(12551l)
                .user(user1)
                .build();
        userFileRepository.save(userFile);

    }
}
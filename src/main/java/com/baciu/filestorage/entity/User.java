package com.baciu.filestorage.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Data
@Setter
@EqualsAndHashCode(exclude = {"userFiles", "groups"})
@ToString(exclude = {"userFiles", "groups"})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date registerDate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Group> groups = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<UserFile> userFiles = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>(0);

    @PrePersist
    private void initializeData() {
        this.registerDate = new Date();
        this.roles = new HashSet<>(Arrays.asList(Role.builder().id(1l).build()));
    }

}

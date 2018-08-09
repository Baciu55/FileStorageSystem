package com.baciu.filestorage.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = {"users", "files"})
@ToString(exclude = {"users", "files"})
@Entity
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "groups_users", joinColumns = {
            @JoinColumn(name = "groups_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "users_id", nullable = false)})
    private Set<User> users = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "file_group", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "file_id", nullable = false)
            })
    private Set<File> files = new HashSet<>(0);

    @Builder
    private Group(String name, String description, Set<User> users, Set<File> files) {
        this.name = name;
        this.description = description;
        this.users = users;
        this.files = files;
    }
}

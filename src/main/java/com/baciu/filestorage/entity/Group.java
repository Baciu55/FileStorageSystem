package com.baciu.filestorage.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(exclude = {"users", "userFiles"})
@ToString(exclude = {"users", "userFiles"})
@Entity
@Table(name = "groups")
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
    @JoinTable(name = "userfiles_groups", joinColumns = {
            @JoinColumn(name = "group_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "user_file_id", nullable = false)
            })
    private Set<UserFile> userFiles = new HashSet<>(0);
}

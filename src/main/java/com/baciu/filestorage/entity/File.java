package com.baciu.filestorage.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(exclude = {"user", "groups"})
@ToString(exclude = {"user", "groups"})
@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Date uploadDate;

    @Column(nullable = false)
    private Long size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "files")
    private Set<Group> groups = new HashSet<>(0);

    @PrePersist
    private void setDate() {
        this.uploadDate = new Date();
    }

    @Builder
    private File(String path, String name, String description, Date uploadDate, Long size, User user, Set<Group> groups) {
        this.path = path;
        this.name = name;
        this.description = description;
        this.uploadDate = uploadDate;
        this.size = size;
        this.user = user;
        this.groups = groups;
    }
}

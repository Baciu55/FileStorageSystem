package com.baciu.filestorage.repository;

import com.baciu.filestorage.entity.UserFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileRepository extends CrudRepository<UserFile, Long> {
}

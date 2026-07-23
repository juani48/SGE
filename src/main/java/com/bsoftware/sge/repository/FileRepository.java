package com.bsoftware.sge.repository;

import org.springframework.data.repository.CrudRepository;

import com.bsoftware.sge.model.File;

public interface FileRepository extends CrudRepository<File, Long> {

}

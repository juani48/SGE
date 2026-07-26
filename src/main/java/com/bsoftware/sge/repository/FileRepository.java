package com.bsoftware.sge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bsoftware.sge.model.File;

public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAll();
}

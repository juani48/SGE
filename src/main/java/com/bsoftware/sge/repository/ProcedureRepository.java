package com.bsoftware.sge.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bsoftware.sge.model.Procedure;

public interface ProcedureRepository extends JpaRepository<Procedure, Long> {
    Optional<Procedure> findById(Long id);
    Page<Procedure> findByFileId(Long fileId, Pageable pageable);
}

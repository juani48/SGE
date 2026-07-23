package com.bsoftware.sge.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.bsoftware.sge.model.Procedure;

public interface ProcedureRepository extends CrudRepository<Procedure, Long> {
    Optional<Procedure> findById(Long id);
    Iterable<Procedure> findAllByFileId(Long fileId);
}

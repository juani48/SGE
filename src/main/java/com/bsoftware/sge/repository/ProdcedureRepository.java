package com.bsoftware.sge.repository;

import org.springframework.data.repository.CrudRepository;

import com.bsoftware.sge.model.File;

public interface ProdcedureRepository extends CrudRepository<File, Long> {

}

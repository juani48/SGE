package com.bsoftware.sge.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bsoftware.sge.auxiliar.ProcedureState;
import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.model.File;
import com.bsoftware.sge.model.Procedure;
import com.bsoftware.sge.repository.ProcedureRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProcedureService {
    private final ProcedureRepository procedureRepository;
    private final FileService fileService;
    private final UserService userService;

    public Result<Procedure> create(String content, Long fileId, Long userId) {
        Result<File> fileResult = fileService.getById(fileId);
        if (!fileResult.isOk()) {
            return Result.fail(fileResult.getError());
        }
        Result<ApplicationUser> userResult = userService.findById(userId);
        if (!userResult.isOk()) {
            return Result.fail(userResult.getError());
        }
        Procedure savedProcedure = procedureRepository.save(this.setData(content, fileResult.getData(), userResult.getData()));
        return Result.ok(savedProcedure);
    }

    public Result<Procedure> getById(Long id) {
        Optional<Procedure> opt = procedureRepository.findById(id);
        if (opt.isPresent()) {
            return Result.ok(opt.get());
        } else {
            return Result.fail("Procedure not found with id: " + id);
        }
    }

    public Result<Iterable<Procedure>> getAllByFileId(Long fileId) {
        try {
            Iterable<Procedure> procedures = procedureRepository.findAllByFileId(fileId);
            return Result.ok(procedures);
        } catch (Exception e) {
            return Result.fail("Error retrieving procedures: " + e.getMessage());
        }
    }

    public Result<Procedure> update(String content, Long id, String state, Long userId) {
        Result<ApplicationUser> userResult = userService.findById(userId);
        if (!userResult.isOk()) {
            return Result.fail(userResult.getError());
        }
        Result<Procedure> procedureResult = getById(id);
        if (!procedureResult.isOk()) {
            return Result.fail(procedureResult.getError());
        }
        Procedure procedure = procedureResult.getData();
        procedure.setContent(content);
        procedure.setState(ProcedureState.valueOf(state));
        procedure.setModificationUser(userResult.getData());
        Procedure updatedProcedure = procedureRepository.save(procedure);
        return Result.ok(updatedProcedure);
    }

    public Result<Void> delete(Long id) {
        try {
            procedureRepository.deleteById(id);
            return Result.ok(null);
        } catch (Exception e) {
            return Result.fail("Error deleting procedure: " + e.getMessage());
        }
    }

    private Procedure setData(String content, File file, ApplicationUser user) {
        Procedure procedure = new Procedure();
        procedure.setContent(content);
        procedure.setFile(file);
        procedure.setCreation(LocalDate.now());
        procedure.setModification(LocalDate.now());
        procedure.setState(ProcedureState.DOCUMENT_SUBMITTED);
        procedure.setModificationUser(user);
        return procedure;
    }
}

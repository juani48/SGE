package com.bsoftware.sge.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bsoftware.sge.auxiliar.FileState;
import com.bsoftware.sge.auxiliar.Result;
import com.bsoftware.sge.model.ApplicationUser;
import com.bsoftware.sge.model.File;
import com.bsoftware.sge.repository.FileRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FileService {
    private final FileRepository fileStateService;
    private final UserService userService;

    public Result<File> create(File file, Long userId) {
        try {
            Result<ApplicationUser> userResult = userService.findById(userId);
            if (!userResult.isOk()) {
                return Result.fail(userResult.getError());
            }
            file.setModificationUser(userResult.getData());
            file.setCreation(LocalDate.now());
            file.setModification(LocalDate.now());
            file.setState(FileState.NEWLY_STARTED);
            File savedFile = fileStateService.save(file);
            return Result.ok(savedFile);
        } catch (Exception e) {
            return Result.fail("Error saving file: " + e.getMessage());
        }
    }

    public Result<File> getById(Long id) {
        Optional<File> opt = fileStateService.findById(id);
        if (opt.isPresent()) {
            return Result.ok(opt.get());
        } else {
            return Result.fail("File not found with id: " + id);
        }
    }

    public Result<Iterable<File>> getAll() {
        try {
            Iterable<File> files = fileStateService.findAll();
            return Result.ok(files);
        } catch (Exception e) {
            return Result.fail("Error retrieving files: " + e.getMessage());
        }
    }

    public Result<File> update(File file) {
        try {
            File updatedFile = fileStateService.save(file);
            return Result.ok(updatedFile);
        } catch (Exception e) {
            return Result.fail("Error updating file: " + e.getMessage());
        }
    }

    public Result<Void> delete(Long id) {
        try {
            fileStateService.deleteById(id);
            return Result.ok(null);
        } catch (Exception e) {
            return Result.fail("Error deleting file: " + e.getMessage());
        }
    }
}

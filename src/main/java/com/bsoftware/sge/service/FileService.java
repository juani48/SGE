package com.bsoftware.sge.service;

import java.time.LocalDate;
import java.util.List;
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
    private final FileRepository fileRepository;
    private final UserService userService;

    public Result<File> create(String cover, Long userId) {
        try {
            Result<ApplicationUser> userResult = userService.findById(userId);
            if (!userResult.isOk()) {
                return Result.fail(userResult.getError());
            }
            File savedFile = fileRepository.save(this.setData(cover, userResult.getData()));
            return Result.ok(savedFile);
        } catch (Exception e) {
            return Result.fail("Error saving file: " + e.getMessage());
        }
    }

    public Result<File> getById(Long id) {
        Optional<File> opt = fileRepository.findById(id);
        if (opt.isPresent()) {
            return Result.ok(opt.get());
        } else {
            return Result.fail("File not found with id: " + id);
        }
    }

    public List<File> getAll() {
        List<File> files = fileRepository.findAll();
        return files.stream().toList();
    }

    public Result<File> update(Long id, String cover, String status, Long userId) {
        try {
            Result<ApplicationUser> userResult = userService.findById(userId);
            if (!userResult.isOk()) {
                return Result.fail(userResult.getError());
            }
            Optional<File> opt = fileRepository.findById(id);
            if (!opt.isPresent()) {
                return Result.fail("File not found with id: " + id);
            }
            File file = opt.get();
            file.setCover(cover);
            file.setState(FileState.valueOf(status));
            file.setModificationUser(userResult.getData());
            file.setModification(LocalDate.now());
            File updatedFile = fileRepository.save(file);
            return Result.ok(updatedFile);
        } catch (Exception e) {
            return Result.fail("Error updating file: " + e.getMessage());
        }
    }

    public Result<Void> delete(Long id) {
        try {
            fileRepository.deleteById(id);
            return Result.ok(null);
        } catch (Exception e) {
            return Result.fail("Error deleting file: " + e.getMessage());
        }
    }

    private File setData(String cover, ApplicationUser user) {
        File file = new File();
        file.setCover(cover);
        file.setModificationUser(user);
        file.setCreation(LocalDate.now());
        file.setModification(LocalDate.now());
        file.setState(FileState.NEWLY_STARTED);
        return file;
    }
}

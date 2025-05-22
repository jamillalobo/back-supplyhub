package com.supplyhub.domain.manager;

import com.supplyhub.domain.manager.dto.*;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/managers")
@RequiredArgsConstructor
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @GetMapping("/{id}")
    public ResponseEntity<DetailsDataManagerDto> findById(@PathVariable Long id) {
        DetailsDataManagerDto employee = managerService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<ListDataManagersDto>> findAll() {
        List<ListDataManagersDto> employees = managerService.findAll();
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateDataManagerData data) {
        ManagerResponseDto updated = managerService.update(id, data);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        managerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

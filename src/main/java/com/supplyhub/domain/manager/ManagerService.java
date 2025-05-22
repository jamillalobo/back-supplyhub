package com.supplyhub.domain.manager;



import com.supplyhub.domain.employee.dto.*;
import com.supplyhub.domain.manager.dto.*;
import com.supplyhub.domain.user.UserRepository;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
import com.supplyhub.utils.EmailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ManagerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DetailsDataManagerDto findById(Long id) {
        Manager manager = managerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manager not found"));
        return DetailsDataManagerDto.fromManager(manager);
    }


    public List<ListDataManagersDto> findAll() {
        List<Manager> managers = managerRepository.findAll();
        return managers.stream()
                .map(ListDataManagersDto::new)
                .toList();
    }

    public ManagerResponseDto update(Long id, UpdateDataManagerData data) {
        Manager employee = managerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Manager not found"));

        employee.setUsername(data.username());
        employee.setPassword(data.password());

        Manager updated = managerRepository.save(employee);
        return new ManagerResponseDto(updated);
    }

    public void delete(Long id) {
        if (!managerRepository.existsById(id)) {
            throw new EntityNotFoundException("Manager not found");
        }
        managerRepository.deleteById(id);
    }
}

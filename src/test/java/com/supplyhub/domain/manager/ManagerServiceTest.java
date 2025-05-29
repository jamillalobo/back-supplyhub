package com.supplyhub.domain.manager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.supplyhub.domain.manager.dto.DetailsDataManagerDto;
import com.supplyhub.domain.manager.dto.ListDataManagersDto;
import com.supplyhub.domain.manager.dto.ManagerResponseDto;
import com.supplyhub.domain.manager.dto.UpdateDataManagerData;
import com.supplyhub.domain.user.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ManagerServiceTest {
    
    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ManagerService managerService;

    @Test
    void findById_WithValidId_ShouldReturnManagerDetails() {
        // Arrange
        Manager mockManager = new Manager();
        mockManager.setId(1L);
        mockManager.setUsername("Gerente");
        mockManager.setEmail("gerente@supplyhub.com");

        when(managerRepository.findById(1L)).thenReturn(Optional.of(mockManager));

        // Act
        DetailsDataManagerDto result = managerService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Gerente", result.username());
        assertEquals("gerente@supplyhub.com", result.email());
        verify(managerRepository).findById(1L);
    }

    @Test
    void findById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(managerRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> managerService.findById(99L));
        verify(managerRepository).findById(99L);
    }


    @Test
    void findAll_ShouldReturnListOfManagers() {
        // Arrange
        Manager manager1 = new Manager();
        manager1.setId(1L);
        manager1.setUsername("Manager 1");

        Manager manager2 = new Manager();
        manager2.setId(2L);
        manager2.setUsername("Manager 2");

        when(managerRepository.findAll()).thenReturn(List.of(manager1, manager2));

        // Act
        List<ListDataManagersDto> result = managerService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Manager 1", result.get(0).username());
        assertEquals("Manager 2", result.get(1).username());
        verify(managerRepository).findAll();
    }   

    @Test
    void update_WithValidId_ShouldUpdateManager() {
        // Arrange
        Manager existingManager = new Manager();
        existingManager.setId(1L);
        existingManager.setUsername("Antigo");
        existingManager.setPassword("senhaAntiga");

        UpdateDataManagerData updateDto = new UpdateDataManagerData(1L, "NovoNome", "novo@email.com", "novaSenha");

        when(managerRepository.findById(1L)).thenReturn(Optional.of(existingManager));
        when(managerRepository.save(any(Manager.class))).thenReturn(existingManager);

        // Act
        ManagerResponseDto result = managerService.update(1L, updateDto);

        // Assert
        assertNotNull(result);
        assertEquals("NovoNome", result.getUsername());
        verify(managerRepository).findById(1L);
        verify(managerRepository).save(existingManager);
    }

    @Test
    void delete_WithValidId_ShouldDeleteManager() {
        // Arrange
        when(managerRepository.existsById(1L)).thenReturn(true);

        // Act
        managerService.delete(1L);

        // Assert
        verify(managerRepository).deleteById(1L);
    }

    @Test
    void delete_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(managerRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> managerService.delete(99L));
        verify(managerRepository, never()).deleteById(any());
    }
}

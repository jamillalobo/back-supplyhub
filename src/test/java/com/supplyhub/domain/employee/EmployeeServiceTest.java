package com.supplyhub.domain.employee;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.supplyhub.domain.employee.dto.CreateDataEmployeeDto;
import com.supplyhub.domain.employee.dto.DetailsDataEmployeeDto;
import com.supplyhub.domain.employee.dto.EmployeeResponseDto;
import com.supplyhub.domain.employee.dto.ListDataEmployeesDto;
import com.supplyhub.domain.employee.dto.UpdateDataEmployeeData;
import com.supplyhub.domain.user.UserRepository;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
import com.supplyhub.utils.EmailService;

import jakarta.persistence.EntityNotFoundException;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    
    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private EmployeeResponseDto employeeResponseDto;

    @InjectMocks
    private EmployeeService employeeService;

    // TEST: createEmployee()
    @Test
    void createEmployee_ShouldReturnEmployeeWithEncodedPassword() {
        // Arrange
        CreateDataUserDto userDto = new CreateDataUserDto(
            "Lucas", 
            "lucas@gmail.com", 
            "12345678", 
            "70322463467"
        );

        String encodedPassword = "fake_encoded_12345678";
        when(passwordEncoder.encode(userDto.password())).thenReturn(encodedPassword);

        Employee expectedEmployee = new Employee(userDto);
        expectedEmployee.setPassword(encodedPassword);
        
        when(userRepository.save(any(Employee.class))).thenReturn(expectedEmployee);

        // Act
        Employee createdEmployee = employeeService.createEmployee(userDto);

        // Assert
        assertNotNull(createdEmployee);
        assertEquals(encodedPassword, createdEmployee.getPassword());
        verify(passwordEncoder).encode(userDto.password());  // o encode() foi chamado com a senha correta?
        verify(userRepository).save(any(Employee.class));  // o .save() foi chamado com um objeto do tipo Employee?
    }

    // TEST: findById()
    @Test
    void findById_WithExistingEmployee_ShouldReturnDetails() {
        // Arrange
        Long id = 1L;
        Employee mockEmployee = new Employee();
        mockEmployee.setId(id);
        mockEmployee.setUsername("Lucas");
        mockEmployee.setEmail("lucas@email.com");
        mockEmployee.setCpf("12345678901");

        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee));

        // Act
        DetailsDataEmployeeDto result = employeeService.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals("Lucas", result.username());
        assertEquals("lucas@email.com", result.email());
        verify(employeeRepository).findById(id);
    }

    @Test
    void findById_WithNonExistingEmployee_ShouldThrowException() {
        // Arrange
        Long id = 999L;
        when(employeeRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> employeeService.findById(id));
        verify(employeeRepository).findById(id);
    }

    @Test
    void findAll_ShouldReturnListOfEmployeeDtos() {
        // Arrange
        Employee e1 = new Employee();
        e1.setUsername("Lucas");
        e1.setCpf("123");
        e1.setEmail("lucas@email.com");

        Employee e2 = new Employee();
        e2.setUsername("Ana");
        e2.setCpf("456");
        e2.setEmail("ana@email.com");

        when(employeeRepository.findAll()).thenReturn(List.of(e1, e2));

        // Act
        List<ListDataEmployeesDto> result = employeeService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Lucas", result.get(0).username());
        assertEquals("Ana", result.get(1).username());
        verify(employeeRepository).findAll();
    }

    @Test
    void update_WithValidData_ShouldUpdateEmployee() {
        // Arrange
        Long id = 1L;
        UpdateDataEmployeeData updateData = new UpdateDataEmployeeData(
            id,                       
            "NovoNome",
            "novo@email.com",
            "novaSenha"           
        );

        Employee existingEmployee = new Employee();
        existingEmployee.setId(id);
        existingEmployee.setUsername("AntigoNome");
        existingEmployee.setPassword("senhaAntiga");

        when(employeeRepository.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        EmployeeResponseDto result = employeeService.update(id, updateData);

        // Assert
        assertEquals(updateData.id(), result.getId());
        assertEquals(updateData.username(), result.getUsername());
        verify(employeeRepository).findById(id);
        verify(employeeRepository).save(existingEmployee);
    }

    @Test
    void delete_WithExistingEmployee_ShouldDeleteEmployee() {
        // Arrange
        Long id = 1L;
        when(employeeRepository.existsById(id)).thenReturn(true);

        // Act
        employeeService.delete(id);

        // Assert
        verify(employeeRepository).deleteById(id);
    }

    @Test
    void delete_WithNonExistingEmployee_ShouldThrowException() {
        // Arrange
        Long id = 999L;
        when(employeeRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> employeeService.delete(id));
        verify(employeeRepository, never()).deleteById(id);
    }
}

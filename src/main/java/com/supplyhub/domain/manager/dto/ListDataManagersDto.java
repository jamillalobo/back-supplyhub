package com.supplyhub.domain.manager.dto;

import com.supplyhub.domain.manager.Manager;

public record ListDataManagersDto(
        Long id,
        String username,
        String email,
        String cpf
) {
    public ListDataManagersDto(Manager manager) {
        this(manager.getId(), manager.getUsername(), manager.getEmail(), manager.getCpf());
    }

}

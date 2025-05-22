package com.supplyhub.domain.manager.dto;

import com.supplyhub.domain.manager.Manager;

public class ManagerResponseDto {
    private Long id;
    private String username;

    public ManagerResponseDto(Manager manager) {
        this.id = manager.getId();
        this.username = manager.getUsername();
    }
}

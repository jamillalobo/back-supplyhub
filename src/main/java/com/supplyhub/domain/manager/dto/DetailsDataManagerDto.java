package com.supplyhub.domain.manager.dto;


import com.supplyhub.domain.manager.Manager;

public record DetailsDataManagerDto(
        String username,
        String email,
        String password
) {
    public static DetailsDataManagerDto fromManager(Manager manager) {
        return new DetailsDataManagerDto(
                manager.getUsername(),
                manager.getEmail(),
                manager.getPassword()
        );
    }
}

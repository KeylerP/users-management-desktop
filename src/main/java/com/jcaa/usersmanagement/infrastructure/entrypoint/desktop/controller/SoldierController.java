
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.ManageSoldierUseCase;
import com.jcaa.usersmanagement.domain.model.SoldierModel;

import java.util.List;
import java.util.Optional;

public final class SoldierController {

    private final ManageSoldierUseCase useCase;

    public SoldierController(ManageSoldierUseCase useCase) {
        this.useCase = useCase;
    }

    public SoldierModel create(SoldierModel soldier) {
        return useCase.create(soldier);
    }

    public Optional<SoldierModel> findById(String id) {
        return useCase.findById(id);
    }

    public List<SoldierModel> findAll() {
        return useCase.findAll();
    }

    public SoldierModel update(SoldierModel soldier) {
        return useCase.update(soldier);
    }

    public void deleteById(String id) {
        useCase.deleteById(id);
    }
}

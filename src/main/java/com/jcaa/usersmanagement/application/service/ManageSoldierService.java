package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.ManageSoldierUseCase;
import com.jcaa.usersmanagement.application.port.out.SoldierRepositoryPort;
import com.jcaa.usersmanagement.domain.model.SoldierModel;

import java.util.List;
import java.util.Optional;

public final class ManageSoldierService implements ManageSoldierUseCase {

    private final SoldierRepositoryPort repository;

    public ManageSoldierService(SoldierRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public SoldierModel create(SoldierModel soldier) {
        return repository.save(soldier);
    }

    @Override
    public Optional<SoldierModel> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<SoldierModel> findAll() {
        return repository.findAll();
    }

    @Override
    public SoldierModel update(SoldierModel soldier) {
        return repository.update(soldier);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
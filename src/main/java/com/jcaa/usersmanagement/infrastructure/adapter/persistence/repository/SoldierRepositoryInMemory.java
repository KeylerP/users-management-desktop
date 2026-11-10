package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.SoldierRepositoryPort;
import com.jcaa.usersmanagement.domain.model.SoldierModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class SoldierRepositoryInMemory implements SoldierRepositoryPort {

    private final Map<String, SoldierModel> soldiers =
            new ConcurrentHashMap<>();

    @Override
    public SoldierModel save(SoldierModel soldier) {
        if (soldier == null || soldier.getId() == null
                || soldier.getId().isBlank()) {
            throw new IllegalArgumentException(
                    "El soldado debe tener un identificador");
        }

        if (soldiers.putIfAbsent(soldier.getId(), soldier) != null) {
            throw new IllegalArgumentException(
                    "Ya existe un soldado con ese identificador");
        }

        return soldier;
    }

    @Override
    public Optional<SoldierModel> findById(String id) {
        return Optional.ofNullable(soldiers.get(id));
    }

    @Override
    public List<SoldierModel> findAll() {
        return new ArrayList<>(soldiers.values());
    }

    @Override
    public SoldierModel update(SoldierModel soldier) {
        if (soldier == null || soldier.getId() == null
                || !soldiers.containsKey(soldier.getId())) {
            throw new IllegalArgumentException(
                    "No existe el soldado que deseas actualizar");
        }

        soldiers.put(soldier.getId(), soldier);
        return soldier;
    }

    @Override
    public void deleteById(String id) {
        if (soldiers.remove(id) == null) {
            throw new IllegalArgumentException(
                    "No existe el soldado que deseas eliminar");
        }
    }
}
package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.SoldierModel;
import java.util.List;
import java.util.Optional;

public interface SoldierRepositoryPort {

    SoldierModel save(SoldierModel soldier);

    Optional<SoldierModel> findById(String id);

    List<SoldierModel> findAll();

    SoldierModel update(SoldierModel soldier);

    void deleteById(String id);
}
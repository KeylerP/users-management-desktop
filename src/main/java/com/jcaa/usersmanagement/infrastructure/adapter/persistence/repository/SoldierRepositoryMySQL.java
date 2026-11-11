
package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.SoldierRepositoryPort;
import com.jcaa.usersmanagement.domain.model.SoldierModel;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class SoldierRepositoryMySQL implements SoldierRepositoryPort {

    private final Connection connection;

    public SoldierRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    private static final String SQL_INSERT =
            "INSERT INTO soldiers "
            + "(id, code, identity_document, first_name, second_name, "
            + "first_surname, second_surname, birth_date, gender, "
            + "initial_height, initial_weight, address, phone, email, military_rank) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_BY_ID =
            "SELECT * FROM soldiers WHERE id = ?";

    private static final String SQL_FIND_ALL =
            "SELECT * FROM soldiers ORDER BY first_surname, first_name";

    private static final String SQL_UPDATE =
            "UPDATE soldiers SET "
            + "code = ?, identity_document = ?, first_name = ?, "
            + "second_name = ?, first_surname = ?, second_surname = ?, "
            + "birth_date = ?, gender = ?, initial_height = ?, "
            + "initial_weight = ?, address = ?, phone = ?, "
            + "email = ?, military_rank = ? WHERE id = ?";

    private static final String SQL_DELETE =
            "DELETE FROM soldiers WHERE id = ?";

    @Override
    public SoldierModel save(SoldierModel soldier) {
        validateSoldier(soldier);

        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_INSERT)) {

            statement.setString(1, soldier.getId());
            setSoldierFields(statement, soldier, 2);

            statement.executeUpdate();
            return soldier;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo guardar el soldado: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<SoldierModel> findById(String id) {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setString(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return Optional.of(mapSoldier(result));
                }
                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo buscar el soldado: " + e.getMessage(), e);
        }
    }

    @Override
    public List<SoldierModel> findAll() {
        List<SoldierModel> soldiers = new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_FIND_ALL);
             ResultSet result = statement.executeQuery()) {

            while (result.next()) {
                soldiers.add(mapSoldier(result));
            }

            return soldiers;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudieron listar los soldados: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public SoldierModel update(SoldierModel soldier) {
        validateSoldier(soldier);

        if (findById(soldier.getId()).isEmpty()) {
            throw new IllegalArgumentException(
                    "El soldado no existe.");
        }

        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_UPDATE)) {

            setSoldierFields(statement, soldier, 1);
            statement.setString(15, soldier.getId());

            statement.executeUpdate();
            return soldier;

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo actualizar el soldado: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(String id) {
        try (PreparedStatement statement =
                     connection.prepareStatement(SQL_DELETE)) {

            statement.setString(1, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new IllegalArgumentException(
                        "El soldado no existe.");
            }

        } catch (SQLException e) {
            throw new IllegalStateException(
                    "No se pudo eliminar el soldado: "
                    + e.getMessage(), e);
        }
    }

    private static void setSoldierFields(
            PreparedStatement statement,
            SoldierModel soldier,
            int start) throws SQLException {

        statement.setString(start, soldier.getCode());
        statement.setString(start + 1, soldier.getIdentityDocument());
        statement.setString(start + 2, soldier.getFirstName());
        statement.setString(start + 3, soldier.getSecondName());
        statement.setString(start + 4, soldier.getFirstSurname());
        statement.setString(start + 5, soldier.getSecondSurname());
        statement.setDate(
                start + 6,
                Date.valueOf(soldier.getBirthDate())
        );
        statement.setString(start + 7, soldier.getGender());
        statement.setDouble(start + 8, soldier.getInitialHeight());
        statement.setDouble(start + 9, soldier.getInitialWeight());
        statement.setString(start + 10, soldier.getAddress());
        statement.setString(start + 11, soldier.getPhone());
        statement.setString(start + 12, soldier.getEmail());
        statement.setString(start + 13, soldier.getRank());
    }

    private static SoldierModel mapSoldier(
            ResultSet result) throws SQLException {

        return new SoldierModel(
                result.getString("id"),
                result.getString("code"),
                result.getString("identity_document"),
                result.getString("first_name"),
                result.getString("second_name"),
                result.getString("first_surname"),
                result.getString("second_surname"),
                result.getDate("birth_date").toLocalDate(),
                result.getString("gender"),
                result.getDouble("initial_height"),
                result.getDouble("initial_weight"),
                result.getString("address"),
                result.getString("phone"),
                result.getString("email"),
                result.getString("military_rank")
        );
    }

    private static void validateSoldier(SoldierModel soldier) {
        if (soldier == null
                || soldier.getId() == null
                || soldier.getId().isBlank()) {
            throw new IllegalArgumentException(
                    "El soldado debe tener un ID válido.");
        }
    }
}

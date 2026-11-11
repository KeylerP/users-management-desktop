
package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli;

import com.jcaa.usersmanagement.domain.model.SoldierModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.SoldierController;

import java.time.LocalDate;
import java.util.Scanner;

public final class SoldierManagementCli {

    private final SoldierController controller;
    private final Scanner scanner;

    public SoldierManagementCli(
            SoldierController controller,
            Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    public void start() {
        String option;

        do {
            System.out.println("\n=== GESTION DE SOLDADOS ===");
            System.out.println("1. Crear soldado");
            System.out.println("2. Buscar soldado");
            System.out.println("3. Listar soldados");
            System.out.println("4. Actualizar soldado");
            System.out.println("5. Eliminar soldado");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");

            if (!scanner.hasNextLine()) {
                System.out.println("\nLa entrada de consola se ha cerrado.");
                return;
            }

            option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1" -> create();
                    case "2" -> find();
                    case "3" -> list();
                    case "4" -> update();
                    case "5" -> delete();
                    case "0" -> System.out.println("Volviendo...");
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (!option.equals("0"));
    }

    private void create() {
        SoldierModel soldier = readSoldier(null);
        controller.create(soldier);
        System.out.println("Soldado creado correctamente.");
    }

    private void find() {
        String id = read("ID del soldado: ");

        controller.findById(id).ifPresentOrElse(
                this::printSoldier,
                () -> System.out.println("Soldado no encontrado.")
        );
    }

    private void list() {
        var soldiers = controller.findAll();

        if (soldiers.isEmpty()) {
            System.out.println("No hay soldados registrados.");
            return;
        }

        soldiers.forEach(this::printSoldier);
    }

    private void update() {
        String id = read("ID del soldado: ");

        if (controller.findById(id).isEmpty()) {
            System.out.println("Soldado no encontrado.");
            return;
        }

        System.out.println("Introduce los nuevos datos:");
        SoldierModel soldier = readSoldier(id);

        controller.update(soldier);
        System.out.println("Soldado actualizado correctamente.");
    }

    private void delete() {
        String id = read("ID del soldado: ");
        controller.deleteById(id);
        System.out.println("Soldado eliminado correctamente.");
    }

    private SoldierModel readSoldier(String existingId) {
        String id = existingId != null
                ? existingId
                : read("Identificador: ");

        String code = read("Codigo militar: ");
        String document = read("Documento: ");
        String firstName = read("Primer nombre: ");
        String secondName = read("Segundo nombre: ");
        String firstSurname = read("Primer apellido: ");
        String secondSurname = read("Segundo apellido: ");
        LocalDate birthDate =
                LocalDate.parse(read("Nacimiento (AAAA-MM-DD): "));
        String gender = read("Genero: ");
        double height = Double.parseDouble(read("Estatura: "));
        double weight = Double.parseDouble(read("Peso: "));
        String address = read("Direccion: ");
        String phone = read("Telefono: ");
        String email = read("Correo: ");
        String rank = read("Rango: ");

        return new SoldierModel(
                id,
                code,
                document,
                firstName,
                secondName,
                firstSurname,
                secondSurname,
                birthDate,
                gender,
                height,
                weight,
                address,
                phone,
                email,
                rank
        );
    }

    private String read(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private void printSoldier(SoldierModel soldier) {
        System.out.println("----------------------------");
        System.out.println("ID: " + soldier.getId());
        System.out.println("Codigo: " + soldier.getCode());
        System.out.println("Nombre: "
                + soldier.getFirstName() + " "
                + soldier.getSecondName() + " "
                + soldier.getFirstSurname() + " "
                + soldier.getSecondSurname());
        System.out.println("Documento: " + soldier.getIdentityDocument());
        System.out.println("Nacimiento: " + soldier.getBirthDate());
        System.out.println("Genero: " + soldier.getGender());
        System.out.println("Estatura: " + soldier.getInitialHeight());
        System.out.println("Peso: " + soldier.getInitialWeight());
        System.out.println("Direccion: " + soldier.getAddress());
        System.out.println("Telefono: " + soldier.getPhone());
        System.out.println("Correo: " + soldier.getEmail());
        System.out.println("Rango: " + soldier.getRank());
    }
}

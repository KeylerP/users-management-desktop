
package com.jcaa.usersmanagement;

import com.jcaa.usersmanagement.infrastructure.config.DependencyContainer;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.UserManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.SoldierManagementCli;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {

    private static final Logger log =
            LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {

        log.info("Starting Management System...");

        final DependencyContainer container =
                new DependencyContainer();

        try (final Scanner scanner = new Scanner(System.in)) {

            boolean running = true;

            while (running) {

                System.out.println("\n================================");
                System.out.println(" SISTEMA DE GESTION MILITAR");
                System.out.println("================================");
                System.out.println("1. Gestion de usuarios");
                System.out.println("2. Gestion de soldados");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");

                final String option = scanner.nextLine().trim();

                switch (option) {

                    case "1" -> {
                        new UserManagementCli(
                                container.userController(),
                                new ConsoleIO(scanner, System.out)
                        ).start();
                    }

                    case "2" -> {
                        new SoldierManagementCli(
                                container.soldierController(),
                                scanner
                        ).start();
                    }

                    case "0" -> {
                        System.out.println("Hasta pronto.");
                        running = false;
                    }

                    default ->
                            System.out.println("Opcion no valida.");
                }
            }
        }
    }
}

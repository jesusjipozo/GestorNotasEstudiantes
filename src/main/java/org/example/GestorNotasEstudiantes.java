package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorNotasEstudiantes {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String ARCHIVO = "resources/notas_estudiantes.txt";

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer
            switch (opcion) {
                case 1:
                    añadirEstudiante();
                    break;
                case 2:
                    mostrarEstudiantes();
                    break;
                case 3:
                    buscarEstudiante();
                    break;
                case 4:
                    calcularMedia();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 5);
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Gestor de Notas de Estudiantes ---");
        System.out.println("1. Añadir estudiante");
        System.out.println("2. Mostrar todos los estudiantes");
        System.out.println("3. Buscar estudiante");
        System.out.println("4. Calcular nota media");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void añadirEstudiante() {
        System.out.print("Ingrese el nombre del estudiante: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la nota del estudiante: ");
        double nota = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer

        Estudiante estudiante = new Estudiante(nombre, nota);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            writer.write(estudiante.toString());
            writer.newLine();
            System.out.println("Estudiante añadido correctamente.");
        } catch (IOException e) {
            System.out.println("Error al añadir el estudiante: " + e.getMessage());
        }
    }

    private static void mostrarEstudiantes() {
        List<Estudiante> estudiantes = leerEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes en el archivo.");
        } else {
            for (Estudiante estudiante : estudiantes) {
                System.out.println(estudiante.getNombre() + " - " + estudiante.getNota());
            }
        }
    }

    private static void buscarEstudiante() {
        System.out.print("Ingrese el nombre del estudiante a buscar: ");
        String nombre = scanner.nextLine();
        List<Estudiante> estudiantes = leerEstudiantes();
        boolean encontrado = false;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equalsIgnoreCase(nombre)) {
                System.out.println("Estudiante encontrado: " + estudiante.getNombre() + " - " + estudiante.getNota());
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Estudiante no encontrado.");
        }
    }

    private static void calcularMedia() {
        List<Estudiante> estudiantes = leerEstudiantes();
        if (estudiantes.isEmpty()) {
            System.out.println("No hay estudiantes para calcular la media.");
        } else {
            double suma = 0;
            for (Estudiante estudiante : estudiantes) {
                suma += estudiante.getNota();
            }
            double media = suma / estudiantes.size();
            System.out.println("La nota media de los estudiantes es: " + media);
        }
    }

    private static List<Estudiante> leerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 2) {
                    String nombre = partes[0];
                    double nota = Double.parseDouble(partes[1]);
                    estudiantes.add(new Estudiante(nombre, nota));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error en el formato de la nota: " + e.getMessage());
        }
        return estudiantes;
    }

    // Clase interna para representar un estudiante
    static class Estudiante {
        private String nombre;
        private double nota;

        public Estudiante(String nombre, double nota) {
            this.nombre = nombre;
            this.nota = nota;
        }

        public String getNombre() {
            return nombre;
        }

        public double getNota() {
            return nota;
        }

        @Override
        public String toString() {
            return nombre + "," + nota;
        }
    }
}

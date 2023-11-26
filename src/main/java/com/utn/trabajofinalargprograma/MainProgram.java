/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.utn.trabajofinalargprograma;

import controlador.GestorCliente;
import controlador.GestorTecnico;
import controlador.GestorEspecialidad;
import controlador.GestorOperadorMesaAyuda;
import controlador.GestorServicios;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Scanner;
import modelo.Cliente;
import modelo.Especialidad;
import modelo.Tecnico;
import modelo.OperadorMesaAyuda;
import modelo.Servicio;
import vista.ClienteVista;
import vista.TecnicoVista;
import vista.EspecialidadVista;
import vista.OperadorMesaAyudaVista;
import vista.ServiciosVista;
public class MainProgram {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorEspecialidad gestorEspecialidad = new GestorEspecialidad();
        GestorOperadorMesaAyuda gOperadorMesaAyuda = new GestorOperadorMesaAyuda();
        do {
        try {
            //Identifiquese, ingrese su legajo
            System.out.println("Seleccione la funcionalidad a ejecutar");
            System.out.println("1- Administrar Clientes");
            System.out.println("2- Administrar Tecnico");
            System.out.println("3- Administrar Especialidad");
            System.out.println("4- Administrar Operador");
            System.out.println("5- Administrar Servicios");
            System.out.println("6- Administrar Reporte Incidencia");
            System.out.println("7- Reporte de incidentes por tecnico por dias ");
            System.out.println("8- Reporte de incidentes resueltos por especialidad");
            System.out.println("9- Reporte Estadistico Técnico mas eficiente");

            int opcionMenu = scanner.nextInt();
            scanner.nextLine();

            if (opcionMenu == 1) {
                GestorCliente gCliente = new GestorCliente();

                System.out.println("Ingrese el cuit del cliente");
                Long cuit = new Scanner(System.in).nextLong();

                Cliente cliente = gCliente.getClienteXCUIT(cuit);

                ClienteVista vistaCliente = new ClienteVista();

                if (cliente == null) {
                    System.out.println("El cliente buscado no existe. Proceda a cargar uno nuevo");
                    cliente = vistaCliente.cargarClienteNuevo();
                    gCliente.guardar(cliente);
                } else {
                    System.out.println("El cliente " + cliente.getRazonSocial() + " ya existe. Para modificar ingrese U, si desea eliminar ingrese E");
                    String accion = new Scanner(System.in).nextLine();
                    if (accion.toUpperCase().equals("U")) {
                        cliente = vistaCliente.modificarCliente(cliente);
                        gCliente.guardar(cliente);
                    } else if (accion.toUpperCase().equals("E")) {
                        gCliente.eliminar(cliente);
                    }
                }
            }
            else if (opcionMenu == 2) {
                GestorTecnico gTecnico = new GestorTecnico();

                System.out.println("Ingrese el legajo del tecnico");
                Integer legajo = new Scanner(System.in).nextInt();

                Tecnico tecnico = gTecnico.getTecnicoXLegajo(legajo);

                TecnicoVista vistaTecnico = new TecnicoVista();

                if (tecnico == null) {
                    System.out.println("El tecnico buscado no existe. Proceda a cargar uno nuevo");
                    tecnico = vistaTecnico.cargarTecnicoNuevo(legajo);
                    EspecialidadVista vistaEspecialidad = new EspecialidadVista();
                    vistaEspecialidad.cargarEspecialidadesTecnico(tecnico);
                    gTecnico.guardar(tecnico);
                } else {
                    System.out.println("El tecnico " + tecnico.getApellido() + " " + tecnico.getNombre() + " ya existe. Para modificar ingrese U, si desea eliminar ingrese E");
                    String accion = new Scanner(System.in).nextLine();
                    if (accion.toUpperCase().equals("U")) {
                        tecnico = vistaTecnico.modificarTecnico(tecnico, legajo);
                        gTecnico.guardar(tecnico);
                    } else if (accion.toUpperCase().equals("E")) {
                        gTecnico.eliminar(tecnico);
                    }
                }
            }
            else if (opcionMenu == 3) {
                System.out.println("Lista de Especialidades:");
                List <Especialidad> especialidades = gestorEspecialidad.obtenerTodasEspecialidades();
                for (Especialidad especialidad : especialidades) {
                    System.out.println("ID: " + especialidad.getId() + ", Denominación: " + especialidad.getDenominacion());
                }
                System.out.println("Seleccione la operación a realizar");
                System.out.println("1- Agregar Especialidad");
                System.out.println("2- Eliminar Especialidad");

                int operacionEspecialidad = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea

                if (operacionEspecialidad == 1) { // Agregar Especialidad
                    System.out.println("Ingrese la denominación de la especialidad:");
                    String denominacion = scanner.nextLine();
                    gestorEspecialidad.agregarEspecialidad(denominacion);
                    System.out.println("Especialidad agregada exitosamente.");
                } else if (operacionEspecialidad == 2) { // Eliminar Especialidad
                    System.out.println("Ingrese el ID de la especialidad a eliminar:");
                    Long idEspecialidad = scanner.nextLong();
                    gestorEspecialidad.eliminarEspecialidad(idEspecialidad);
                    System.out.println("Especialidad eliminada exitosamente.");
                } else {
                    System.out.println("Operación no válida.");
                }
            }
            else if (opcionMenu == 4) {
                OperadorMesaAyudaVista vistaOperadorMesaAyuda = new OperadorMesaAyudaVista();

                System.out.println("Ingrese el legajo del operador de mesa de ayuda");
                int legajoOperador = new Scanner(System.in).nextInt();

                OperadorMesaAyuda operadorMesaAyuda = gOperadorMesaAyuda.getOperadorMesaAyudaXLegajo(legajoOperador);

                if (operadorMesaAyuda == null) {
                    System.out.println("El operador de mesa de ayuda buscado no existe. Proceda a cargar uno nuevo");
                    operadorMesaAyuda = vistaOperadorMesaAyuda.cargarOperadorMesaAyudaNuevo(legajoOperador);
                    gOperadorMesaAyuda.guardar(operadorMesaAyuda);
                } else {
                    System.out.println("El operador de mesa de ayuda " + operadorMesaAyuda.getApellido() + " "
                            + operadorMesaAyuda.getNombre() + " ya existe. Para modificar ingrese U, si desea eliminar ingrese E");
                    String accion = new Scanner(System.in).nextLine();
                    if (accion.toUpperCase().equals("U")) {
                        operadorMesaAyuda = vistaOperadorMesaAyuda.modificarOperadorMesaAyuda(operadorMesaAyuda, legajoOperador);
                        gOperadorMesaAyuda.guardar(operadorMesaAyuda);
                    } else if (accion.toUpperCase().equals("E")) {
                        gOperadorMesaAyuda.eliminar(operadorMesaAyuda);
                    }
                }

            }
            else if (opcionMenu == 5) {
                ServiciosVista serviciosVista = new ServiciosVista();
                serviciosVista.mostrarMenuServicios();
            }
            else if (opcionMenu == 6) {
                // ... (código para Administrar Reporte Incidencia)
            }
            else if (opcionMenu == 7) {
                // ... (código para Reporte de incidentes por tecnico por dias)
            }
            else if (opcionMenu == 8) {
                // ... (código para Reporte de incidentes resueltos por especialidad)
            }
            else if (opcionMenu == 9) {
                // ... (código para Reporte Estadistico Técnico mas eficiente)
            }

            System.out.println("Desea realizar otra operación? (S/N)");
            String respuesta = scanner.nextLine();
            if (!respuesta.equalsIgnoreCase("S")) {
                break; // Salir del bucle si la respuesta no es "S"

            }

        } catch (Exception e){
            e.printStackTrace();
        }         }
        while (true) ; // Este bucle seguirá ejecutándose hasta que se rompa explícitamente
        scanner.close();
    }

    public static void obtenerConexion() {
        Connection con = null;
        try {
            //Class.forName("com.mysql.jdbc.Driver");//mysql 5
            Class.forName("com.mysql.cj.jdbc.Driver");//mysql 8
            //jdbc:mysql://localhost:3306/database //mysql 5
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/argentina_programa?useTimezone=true&serverTimezone=UTC", "root", "admin");
            if(con != null){
                System.out.println("CONECTADO");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

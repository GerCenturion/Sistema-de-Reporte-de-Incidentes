package vista;

import controlador.*;
import modelo.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class ReporteIncidenciaVista {

    private Scanner scanner;
    private GestorCliente gestorCliente;

    public ReporteIncidenciaVista(Scanner scanner, GestorCliente gestorCliente) {
        this.scanner = scanner;
        this.gestorCliente = gestorCliente;
    }

    public void gestionarReportes(GestorReporteIncidencia gestorReporteIncidencia,
                                  List<Servicio> listaServicios,
                                  List<Tecnico> listaTecnicos) {

        int opcion;
        do {
            System.out.println("\nGestión de Reportes de Incidencia");
            System.out.println("1- Ver Incidentes por Usuario");
            System.out.println("2- Crear Nuevo Reporte");
            System.out.println("3- Modificar/Cerrar Reporte");
            System.out.println("0- Volver al Menú Principal");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    verIncidentesPorUsuario(); // Método para ver incidentes por usuario
                    break;
                case 2:
                    crearReporte(gestorReporteIncidencia, listaServicios, listaTecnicos);
                    break;
                case 3:
                    cerrarModificarReporte(gestorReporteIncidencia);
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 0);
    }

    private void verIncidentesPorUsuario() {
        // Lógica para ver incidentes por usuario
        // Puedes implementar aquí la funcionalidad para mostrar incidentes por usuario
        System.out.println("Funcionalidad no implementada aún.");
    }


    private void cerrarModificarReporte(GestorReporteIncidencia gestorReporteIncidencia) {
        // Lógica para cerrar o modificar un reporte existente
        // Puedes implementar aquí la funcionalidad para cerrar o modificar un reporte existente
        System.out.println("Funcionalidad no implementada aún.");
    }

    public void crearReporte(GestorReporteIncidencia gestorReporteIncidencia, List<Servicio> listaServicios, List<Tecnico> listaTecnicos) {
        try {
            System.out.println("Ingrese el CUIT del cliente:");
            long cuit = scanner.nextLong();
            Cliente cliente = obtenerClientePorCuit(cuit);

            if (cliente != null) {
                // Mostrar nombre del cliente
                System.out.println("Cliente: " + cliente.getRazonSocial());

                if (!cliente.getServicios().isEmpty()) {
                    System.out.println("Ingrese la descripción del problema:");
                    scanner.nextLine(); // Consumir el salto de línea
                    String descripcionProblema = scanner.nextLine();

                    System.out.println("Seleccione un técnico:");
                    Tecnico tecnicoSeleccionado = seleccionarTecnico(listaTecnicos);

                    System.out.println("Ingrese el tiempo estimado de resolución (en minutos):");
                    int tiempoEstimadoResolucion = scanner.nextInt();

                    System.out.println("Ingrese la fecha de posible resolución (formato: dd/MM/yyyy):");
                    scanner.nextLine(); // Consumir el salto de línea
                    String fechaPosibleResolucionStr = scanner.nextLine();
                    Date fechaPosibleResolucion = parseFecha(fechaPosibleResolucionStr);

                    // La fecha de alta será la fecha actual
                    Date fechaAlta = new Date();

                    // Estado inicial: Pendiente
                    String estado = "Pendiente";

                    // Crear el reporte
                    gestorReporteIncidencia.crearReporte(cliente, descripcionProblema, tecnicoSeleccionado,
                            tiempoEstimadoResolucion, fechaPosibleResolucion, fechaAlta, estado);

                    // Mostrar mensaje de éxito
                    System.out.println("Reporte creado exitosamente.");
                } else {
                    System.out.println("El cliente no tiene servicios contratados. No se puede crear un reporte.");
                }
            } else {
                System.out.println("No se encontró ningún cliente con el CUIT proporcionado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Cliente obtenerClientePorCuit(Long cuit) {
        try {
            // Utiliza el gestorCliente para obtener el cliente por CUIT
            return gestorCliente.getClienteXCUIT(cuit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private Tecnico seleccionarTecnico(List<Tecnico> listaTecnicos) {
        // Lógica para seleccionar un técnico
        // Puedes implementar esta lógica según tus necesidades.
        // Aquí, simplemente seleccionamos el primer técnico de la lista como ejemplo.
        if (!listaTecnicos.isEmpty()) {
            return listaTecnicos.get(0);
        } else {
            System.out.println("No hay técnicos disponibles. No se puede asignar un técnico al reporte.");
            return null;
        }
    }

    private Date parseFecha(String fechaStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(fechaStr);
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha. Verifique el formato (dd/MM/yyyy).");
            return null;
        }
    }
}



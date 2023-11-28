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
            return gestorCliente.getClienteXCUIT(cuit);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Tecnico seleccionarTecnico(List<Tecnico> tecnicos) {
        try {
            GestorTecnico gestorTecnico = new GestorTecnico();
            List<Tecnico> tecnicosConEspecialidades = gestorTecnico.obtenerTodosTecnicosConEspecialidades();

            // Aquí puedes implementar la lógica para mostrar la lista de técnicos con especialidades
            // y permitir al usuario seleccionar uno. Puedes utilizar un bucle, mostrar índices, etc.
            // Aquí te dejo un ejemplo simple:

            for (int i = 0; i < tecnicosConEspecialidades.size(); i++) {
                Tecnico tecnico = tecnicosConEspecialidades.get(i);
                System.out.println((i + 1) + ". Legajo: " + tecnico.getLegajo() +
                        ", Nombre: " + tecnico.getNombreCompleto() +
                        ", Especialidades: " + obtenerEspecialidades(tecnico));
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Seleccione un técnico por su número de legajo: ");
            int legajoSeleccionado = scanner.nextInt();

            // Obtener el técnico seleccionado por su legajo
            Tecnico tecnicoSeleccionado = gestorTecnico.getTecnicoXLegajo(legajoSeleccionado);

            if (tecnicoSeleccionado != null) {
                return tecnicoSeleccionado;
            } else {
                System.out.println("No se encontró ningún técnico con el legajo proporcionado.");
                return seleccionarTecnico(tecnicos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String obtenerEspecialidades(Tecnico tecnico) {
        StringBuilder especialidadesStr = new StringBuilder();
        List<TecnicoEspecialidad> especialidades = tecnico.getTecnicoEspecialidades();

        for (TecnicoEspecialidad especialidad : especialidades) {
            especialidadesStr.append(obtenerDenominacionEspecialidad(especialidad)).append(", ");
        }

        // Elimina la coma final y espacio si hay al menos una especialidad
        if (especialidadesStr.length() > 0) {
            especialidadesStr.delete(especialidadesStr.length() - 2, especialidadesStr.length());
        }

        return especialidadesStr.toString();
    }

    private String obtenerDenominacionEspecialidad(TecnicoEspecialidad especialidad) {
        // Ajusta esto según la estructura de tu clase Especialidad
        return especialidad.getEspecialidad().getDenominacion();
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




package vista;

import controlador.GestorCliente;
import controlador.GestorReporteIncidencia;
import controlador.GestorTecnico;
import modelo.Cliente;
import modelo.ReporteIncidencia;
import modelo.Servicio;
import modelo.Tecnico;
import modelo.TecnicoEspecialidad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ReporteIncidenciaVista {

    private Scanner scanner;
    private GestorCliente gestorCliente;
    private controlador.GestorReporteIncidencia GestorReporteIncidencia;

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
            System.out.println("3- Cerrar Reporte");
            System.out.println("0- Volver al Menú Principal");
            System.out.print("Ingrese su opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();  // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    verIncidentesPorCliente(gestorReporteIncidencia);break;
                case 2:
                    crearReporte(gestorReporteIncidencia, listaServicios, listaTecnicos);
                    break;
                case 3:
                    cerrarOmodificarReporte(gestorReporteIncidencia);
                    break;
                case 0:
                    System.out.println("Volviendo al Menú Principal");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 0);
    }

    private void cerrarOmodificarReporte(GestorReporteIncidencia gestorReporteIncidencia) {
        verIncidentesPorCliente(gestorReporteIncidencia);

        // Permitir al usuario seleccionar un reporte para cerrar o modificar
        System.out.print("Ingrese el ID del reporte que desea cerrar o modificar: ");
        int idReporteSeleccionado = scanner.nextInt();

        ReporteIncidencia reporteSeleccionado = gestorReporteIncidencia.getReportePorId(idReporteSeleccionado);

        if (reporteSeleccionado != null) {
            // Mostrar detalles del reporte seleccionado (puedes personalizar esto según tus necesidades)
            System.out.println("Detalles del Reporte Seleccionado:");
            System.out.println("ID: " + reporteSeleccionado.getId());
            System.out.println("Descripción: " + reporteSeleccionado.getDescripcionProblema());

            // Preguntar al usuario si desea cerrar o modificar el reporte
            System.out.println("Seleccione la operación a realizar:");
            System.out.println("1- Cerrar Reporte");
            int opcionOperacion = scanner.nextInt();

            switch (opcionOperacion) {
                case 1:
                    cerrarReporte(gestorReporteIncidencia, reporteSeleccionado);
                    break;
                default:
                    System.out.println("Operación no válida.");
            }
        } else {
            System.out.println("No se encontró ningún reporte con el ID proporcionado.");
        }
    }

    private void cerrarReporte(GestorReporteIncidencia gestorReporteIncidencia, ReporteIncidencia reporte) {
        // Mostrar detalles del reporte seleccionado (puedes personalizar esto según tus necesidades)
        System.out.println("Detalles del Reporte Seleccionado:");
        System.out.println("ID: " + reporte.getId());
        System.out.println("Descripción: " + reporte.getDescripcionProblema());


        // Solicitar al usuario que complete el tiempo de resolución y agregue observaciones
        System.out.println("Ingrese el tiempo de resolución (en minutos):");
        int tiempoResolucion = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea

        System.out.println("Ingrese observaciones del técnico:");
        String observacionesTecnico = scanner.nextLine();

        reporte.setTiempoEstimadoResolucion(tiempoResolucion);
        reporte.setObservacionesTecnico(observacionesTecnico);
        reporte.setEstado("Resuelto");

        // Actualizar el reporte en el gestor
        gestorReporteIncidencia.actualizarReporte(reporte);

    }


    private void verIncidentesPorCliente(GestorReporteIncidencia gestorReporteIncidencia) {
        try {
            System.out.println("Ingrese el CUIT del cliente:");
            long cuit = scanner.nextLong();
            Cliente cliente = obtenerClientePorCuit(cuit);

            if (cliente != null) {
                List<ReporteIncidencia> reportesCliente = gestorReporteIncidencia.obtenerReportesPorCliente(cliente);

                if (!reportesCliente.isEmpty()) {
                    System.out.println("Reportes de incidentes para el cliente " + cliente.getRazonSocial() + ":");
                    for (ReporteIncidencia reporte : reportesCliente) {
                        System.out.println("ID: " + reporte.getId() +
                                ", Descripción: " + reporte.getDescripcionProblema() +
                                ", Tipo de Problema: " + reporte.getTipoProblema() +
                                ", Estado: " + reporte.getEstado());
                    }
                } else {
                    System.out.println("El cliente no tiene reportes de incidentes registrados.");
                }
            } else {
                System.out.println("No se encontró ningún cliente con el CUIT proporcionado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void crearReporte(GestorReporteIncidencia gestorReporteIncidencia,
                             List<Servicio> listaServicios,
                             List<Tecnico> listaTecnicos) {
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

                    System.out.println("Seleccione el tipo de problema:");
                    System.out.println("1- Sin Urgencia");
                    System.out.println("2- Moderado");
                    System.out.println("3- Urgente");
                    System.out.print("Ingrese su opción: ");
                    int opcionTipoProblema = scanner.nextInt();

                    // Mapea la opción a un valor de cadena correspondiente
                    String tipoProblema;
                    switch (opcionTipoProblema) {
                        case 1:
                            tipoProblema = "Sin Urgencia";
                            break;
                        case 2:
                            tipoProblema = "Moderado";
                            break;
                        case 3:
                            tipoProblema = "Urgente";
                            break;
                        default:
                            System.out.println("Opción no válida. Se establecerá el tipo de problema como 'Sin Urgencia'.");
                            tipoProblema = "Sin Urgencia";
                    }

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
                            tiempoEstimadoResolucion, fechaPosibleResolucion, fechaAlta, estado, tipoProblema);

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

        if (especialidadesStr.length() > 0) {
            especialidadesStr.delete(especialidadesStr.length() - 2, especialidadesStr.length());
        }

        return especialidadesStr.toString();
    }

    private String obtenerDenominacionEspecialidad(TecnicoEspecialidad especialidad) {
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

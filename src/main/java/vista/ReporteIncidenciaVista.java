package vista;

import controlador.GestorReporteIncidencia;
import controlador.GestorCliente;
import modelo.Cliente;
import modelo.OperadorMesaAyuda;
import modelo.ReporteIncidencia;
import modelo.Servicio;
import modelo.Tecnico;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ReporteIncidenciaVista {
    private Scanner scanner;
    private ClienteVista clienteVista;
    private GestorCliente gestorCliente;

    public ReporteIncidenciaVista() {
        this.scanner = new Scanner(System.in);
        this.clienteVista = new ClienteVista();
        this.gestorCliente = new GestorCliente();
    }
    public ReporteIncidencia crearReporte(GestorReporteIncidencia gestorReporteIncidencia,
                                          List<Servicio> servicios, List<OperadorMesaAyuda> operadores,
                                          List<Tecnico> tecnicos) {
        List<Cliente> clientes = gestorCliente.obtenerTodosClientes();
        Cliente cliente = seleccionarCliente(clientes);
        if (cliente == null) {
            System.out.println("No se pudo crear el reporte. Cliente no seleccionado.");
            return null;
        }

        mostrarServiciosContratados(cliente, servicios);

        Servicio servicio = seleccionarServicio(servicios);
        if (servicio == null) {
            System.out.println("No se pudo crear el reporte. Servicio no seleccionado.");
            return null;
        }

        OperadorMesaAyuda operador = seleccionarOperador(operadores);
        Tecnico tecnico = seleccionarTecnico(tecnicos);

        Date fechaAlta = Calendar.getInstance().getTime();

        System.out.println("Ingrese la descripción del problema:");
        String descripcionProblema = scanner.nextLine();

        System.out.println("Ingrese el tipo de problema:");
        System.out.println("1- Software");
        System.out.println("2- Hardware");
        String tipoProblema = scanner.nextLine();

        System.out.println("Ingrese el tiempo estimado de resolución (en horas):");
        int tiempoEstimadoResolucion = scanner.nextInt();

        Date fechaPosibleResolucion = ingresarFecha();

        String estado = "Pendiente";

        scanner.nextLine();
        System.out.println("Ingrese observaciones del técnico:");
        String observacionesTecnico = scanner.nextLine();

        ReporteIncidencia nuevoReporte = new ReporteIncidencia(fechaAlta, descripcionProblema, tipoProblema,
                servicio, operador, cliente, tecnico, tiempoEstimadoResolucion,
                fechaPosibleResolucion, estado, observacionesTecnico);

        gestorReporteIncidencia.agregarReporteIncidencia(nuevoReporte);

        System.out.println("Reporte de incidencia creado correctamente.");
        return nuevoReporte;
    }

    private void mostrarServiciosContratados(Cliente cliente, List<Servicio> servicios) {
        System.out.println("Servicios contratados por el cliente:");
        for (Servicio servicio : servicios) {
            if (cliente.tieneServicioContratado(servicio)) {
                System.out.println("- " + servicio.getDenominacion());
            }
        }
    }


    private Cliente seleccionarCliente(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes disponibles. Por favor, agregue clientes primero.");
            return null;
        }

        System.out.println("Clientes disponibles:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getRazonSocial());
        }

        System.out.println("Seleccione el número correspondiente al cliente:");
        int opcionCliente = scanner.nextInt();

        if (opcionCliente < 1 || opcionCliente > clientes.size()) {
            System.out.println("Opción no válida. Seleccionando el primer cliente por defecto.");
            return clientes.get(0);
        }

        return clientes.get(opcionCliente - 1);
    }


    private Servicio seleccionarServicio(List<Servicio> servicios) {
        if (servicios.isEmpty()) {
            System.out.println("No hay servicios disponibles. Por favor, agregue servicios primero.");
            return null;
        }
        return servicios.get(0);
    }

    private OperadorMesaAyuda seleccionarOperador(List<OperadorMesaAyuda> operadores) {
        if (operadores.isEmpty()) {
            System.out.println("No hay operadores disponibles. Por favor, agregue operadores primero.");
            return null;
        }
        return operadores.get(0);
    }

    private Tecnico seleccionarTecnico(List<Tecnico> tecnicos) {
        if (tecnicos.isEmpty()) {
            System.out.println("No hay técnicos disponibles. Por favor, agregue técnicos primero.");
            return null;
        }
        return tecnicos.get(0);
    }

    private Date ingresarFecha() {
        String fechaStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.parse(fechaStr);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al convertir la fecha", e);
        }
    }
}

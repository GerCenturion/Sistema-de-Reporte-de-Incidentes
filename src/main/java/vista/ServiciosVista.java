package vista;

import java.util.List;
import java.util.Scanner;
import controlador.GestorServicios;
import controlador.GestorCliente;
import modelo.Cliente;
import modelo.Servicio;
import modelo.ClienteServicio;

public class ServiciosVista {

    private final GestorServicios gestorServicios;

    public ServiciosVista() {
        this.gestorServicios = new GestorServicios();
    }

    public void mostrarMenuServicios() {
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Seleccione la operación a realizar con servicios:");
            System.out.println("1- Mostrar todos los servicios");
            System.out.println("2- Agregar un nuevo servicio");
            System.out.println("3- Eliminar un servicio");
            System.out.println("4- Asignar servicio a cliente");
            System.out.println("5- Eliminar servicio de cliente");
            System.out.println("0- Volver al menú principal");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    mostrarTodosServicios();
                    break;
                case 2:
                    agregarNuevoServicio();
                    break;
                case 3:
                    eliminarServicio();
                    break;
                case 4:
                    asignarServicioACliente();
                    break;
                case 5:
                    eliminarServicioDeCliente();
                    break;
                case 0:
                    return; // Salir al menú principal
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (true);
    }

    private void mostrarTodosServicios() {
        List<Servicio> servicios = gestorServicios.obtenerTodosServicios();

        if (servicios.isEmpty()) {
            System.out.println("No hay servicios disponibles.");
        } else {
            System.out.println("Listado de servicios:");
            for (Servicio servicio : servicios) {
                System.out.println("ID: " + servicio.getId() + ", Denominación: " + servicio.getDenominacion());
            }
        }
    }

    private void agregarNuevoServicio() {
        System.out.println("Ingrese la denominación del nuevo servicio:");
        String denominacion = new Scanner(System.in).nextLine();
        gestorServicios.agregarServicio(denominacion);
        System.out.println("Servicio agregado exitosamente.");
    }

    private void eliminarServicio() {
        System.out.println("Ingrese el ID del servicio a eliminar:");
        long idServicio = new Scanner(System.in).nextLong();
        gestorServicios.eliminarServicio(idServicio);
        System.out.println("Servicio eliminado exitosamente.");
    }

    private void asignarServicioACliente() {
        System.out.println("Ingrese el CUIT del cliente:");
        long cuitCliente = new Scanner(System.in).nextLong();
        Cliente cliente = obtenerClientePorCuit(cuitCliente);

        if (cliente != null) {
            mostrarTodosServicios();
            System.out.println("Ingrese el ID del servicio a asignar:");
            long idServicio = new Scanner(System.in).nextLong();
            Servicio servicio = gestorServicios.getServicioById(idServicio);

            if (servicio != null) {
                gestorServicios.agregarServicioACliente(cliente, servicio);
                System.out.println("Servicio asignado exitosamente al cliente.");
            } else {
                System.out.println("Servicio no encontrado.");
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void eliminarServicioDeCliente() {
        System.out.println("Ingrese el CUIT del cliente:");
        long cuitCliente = new Scanner(System.in).nextLong();
        Cliente cliente = obtenerClientePorCuit(cuitCliente);

        if (cliente != null) {
            List<ClienteServicio> serviciosCliente = gestorServicios.obtenerServiciosCliente(cliente);

            if (!serviciosCliente.isEmpty()) {
                System.out.println("Servicios asignados al cliente:");
                for (ClienteServicio clienteServicio : serviciosCliente) {
                    System.out.println("ID: " + clienteServicio.getServicio().getId() +
                            ", Denominación: " + clienteServicio.getServicio().getDenominacion());
                }

                System.out.println("Ingrese el ID del servicio a eliminar del cliente:");
                long idServicio = new Scanner(System.in).nextLong();

                ClienteServicio clienteServicio = serviciosCliente.stream()
                        .filter(cs -> cs.getServicio().getId() == idServicio)
                        .findFirst()
                        .orElse(null);

                if (clienteServicio != null) {
                    gestorServicios.eliminarServicioDeCliente(clienteServicio);
                    System.out.println("Servicio eliminado exitosamente del cliente.");
                } else {
                    System.out.println("Servicio no asignado al cliente.");
                }
            } else {
                System.out.println("El cliente no tiene servicios asignados.");
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private Cliente obtenerClientePorCuit(long cuit) {
        GestorCliente gestorCliente = new GestorCliente();
        return gestorCliente.getClienteXCUIT(cuit);
    }
}

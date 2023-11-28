package controlador;

import modelo.Cliente;
import modelo.Tecnico;
import modelo.ReporteIncidencia;

import java.util.Date;

import org.hibernate.Transaction;
import persistencia.ConfigHibernate;

import java.util.Collections;
import java.util.List;

public class GestorReporteIncidencia extends Gestor {

    public void crearReporte(Cliente cliente, String descripcionProblema, Tecnico tecnico,
                             int tiempoEstimadoResolucion, Date fechaPosibleResolucion,
                             Date fechaAlta, String estado, String tipoProblema) {
        if (sesion == null || !sesion.isOpen()) {
            sesion = ConfigHibernate.openSession();
        }

        try {
            Transaction tx = null;
            try {
                ReporteIncidencia reporte = new ReporteIncidencia();
                reporte.setCliente(cliente);
                reporte.setDescripcionProblema(descripcionProblema);
                reporte.setTecnico(tecnico);
                reporte.setTiempoEstimadoResolucion(tiempoEstimadoResolucion);
                reporte.setFechaPosibleResolucion(fechaPosibleResolucion);
                reporte.setFechaAlta(fechaAlta);
                reporte.setEstado(estado);
                reporte.setTipoProblema(tipoProblema);

                // Llama al método guardar de la clase Gestor para persistir el reporte
                guardar(reporte);

                // Puedes imprimir un mensaje adicional si lo deseas
                System.out.println("Reporte persistido exitosamente.");

            } catch (Exception e) {
                // Maneja la excepción si ocurre algún problema al persistir el reporte
                e.printStackTrace();
                System.out.println("Error al persistir el reporte: " + e.getMessage());
            }
        } finally {
            // Cierra la sesión al finalizar
            sesion.close();
        }
    }
}


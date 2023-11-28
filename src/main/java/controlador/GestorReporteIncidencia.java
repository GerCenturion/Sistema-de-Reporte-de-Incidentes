package controlador;

import modelo.Cliente;
import modelo.Tecnico;
import modelo.ReporteIncidencia;

import java.util.Date;  // Make sure to import the correct Date class

import org.hibernate.Query;
import org.hibernate.Transaction;
import persistencia.ConfigHibernate;

import java.util.Collections;
import java.util.List;

public class GestorReporteIncidencia extends Gestor {

    public void crearReporte(Cliente cliente, String descripcionProblema, Tecnico tecnico,
                             int tiempoEstimadoResolucion, Date fechaPosibleResolucion, Date fechaAlta, String estado) {
        if (sesion == null || !sesion.isOpen()) {
            sesion = ConfigHibernate.openSession();
        }
    }

    public List<ReporteIncidencia> obtenerTodosReportesIncidencia() {
        try {
            Query consulta = sesion.createQuery("FROM ReporteIncidencia");
            return consulta.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void agregarReporteIncidencia(ReporteIncidencia nuevoReporte) {
        Transaction transaction = null;

        try {
            transaction = sesion.beginTransaction();

            sesion.persist(nuevoReporte);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al agregar reporte de incidencia: " + e.getMessage(), e);
        }
    }
}


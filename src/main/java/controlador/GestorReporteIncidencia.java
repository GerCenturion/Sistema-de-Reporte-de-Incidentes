package controlador;

import modelo.Cliente;
import modelo.OperadorMesaAyuda;
import modelo.ReporteIncidencia;
import modelo.Servicio;
import modelo.Tecnico;
import org.hibernate.Query;
import org.hibernate.Transaction;
import persistencia.ConfigHibernate;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GestorReporteIncidencia extends Gestor {

    public GestorReporteIncidencia() {
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

    // Otras operaciones necesarias para el manejo de reportes de incidencia
}

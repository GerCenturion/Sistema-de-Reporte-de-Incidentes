package controlador;

import modelo.Cliente;
import modelo.Tecnico;
import modelo.ReporteIncidencia;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import persistencia.ConfigHibernate;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import org.hibernate.Transaction;


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
    public ReporteIncidencia getReportePorId(long id) {
        Session session = null;
        try {
            session = ConfigHibernate.openSession();

            // Utilizando HQL para obtener el reporte por ID
            String hql = "FROM ReporteIncidencia r WHERE r.id = :id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);

            return (ReporteIncidencia) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<ReporteIncidencia> obtenerReportesPorCliente(Cliente cliente) {
        Session session = null;
        try {
            session = ConfigHibernate.openSession();
            String hql = "FROM ReporteIncidencia r WHERE r.cliente = :cliente";
            Query query = session.createQuery(hql);
            query.setParameter("cliente", cliente);
            return query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public void actualizarReporte(ReporteIncidencia reporte) {
        if (sesion == null || !sesion.isOpen()) {
            sesion = ConfigHibernate.openSession();
        }

        Transaction tx = null;

        try {
            tx = sesion.beginTransaction();

            // Actualizar el reporte en la sesión
            sesion.update(reporte);

            // Commit de la transacción
            tx.commit();

            // Puedes imprimir un mensaje adicional si lo deseas
            System.out.println("Reporte actualizado exitosamente.");
        } catch (HibernateException e) {
            // Maneja la excepción si ocurre algún problema al actualizar el reporte
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            System.out.println("Error al actualizar el reporte: " + e.getMessage());
        } finally {
            // Cierra la sesión al finalizar
            sesion.close();
        }
    }



}


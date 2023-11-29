package controlador;

import modelo.Cliente;
import modelo.Tecnico;
import modelo.ReporteIncidencia;
import modelo.Servicio;
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

    public Tecnico obtenerTecnicoConMasReportesPorDia() {
        Session session = null;
        try {
            session = ConfigHibernate.openSession();

            String hql = "SELECT r.tecnico, COUNT(r), DATE(r.fechaAlta) " +
                    "FROM ReporteIncidencia r " +
                    "GROUP BY r.tecnico, DATE(r.fechaAlta) " +
                    "ORDER BY COUNT(r) DESC";

            Query query = session.createQuery(hql);
            query.setMaxResults(1); // Obtén solo el primer resultado (el técnico con más reportes por día)

            Object[] result = (Object[]) query.uniqueResult();

            if (result != null && result.length == 3) {
                Tecnico tecnico = (Tecnico) result[0];
                Long cantidadReportes = (Long) result[1];
                Date fecha = (Date) result[2];

                System.out.println("Fecha: " + fecha);
                System.out.println("Técnico con más reportes por dia: " + tecnico.getNombreCompleto());
                System.out.println("Cantidad de reportes: " + cantidadReportes);

                return tecnico;
            }

        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    public List<Object[]> obtenerReporteIncidentesResueltosPorServicio() {
        Session session = null;
        try {
            session = ConfigHibernate.openSession();

            // Utilizando HQL para obtener el reporte por servicio y cantidad de incidentes resueltos
            String hql = "SELECT r.servicio.denominacion, COUNT(r) " +
                    "FROM ReporteIncidencia r " +
                    "WHERE r.estado = 'Resuelto' " +
                    "GROUP BY r.servicio.denominacion";
            Query query = session.createQuery(hql);

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

    public Tecnico obtenerTecnicoMasEficiente() {
        Session session = null;
        try {
            session = ConfigHibernate.openSession();
            String hql = "SELECT r.tecnico, COUNT(r), MIN(r.tiempoEstimadoResolucion) " +
                    "FROM ReporteIncidencia r " +
                    "WHERE r.estado = 'Resuelto' " +
                    "GROUP BY r.tecnico " +
                    "ORDER BY COUNT(r) DESC, MIN(r.tiempoEstimadoResolucion)";
            Query query = session.createQuery(hql);
            query.setMaxResults(1);
            Object[] result = (Object[]) query.uniqueResult();
            return result != null ? (Tecnico) result[0] : null;
        } catch (HibernateException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
    public long contarReportesResueltosPorTecnico(Tecnico tecnico) {
        Session session = null;
        try {
            session = ConfigHibernate.openSession();
            String hql = "SELECT COUNT(r) FROM ReporteIncidencia r WHERE r.tecnico = :tecnico AND r.estado = 'Resuelto'";
            Query query = session.createQuery(hql);
            query.setParameter("tecnico", tecnico);
            return (long) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public int obtenerTiempoEstimadoTotalPorTecnico(Tecnico tecnico) {
        Session session = null;
        try {
            session = ConfigHibernate.openSession();
            String hql = "SELECT COALESCE(SUM(r.tiempoEstimadoResolucion), 0) FROM ReporteIncidencia r WHERE r.tecnico = :tecnico AND r.estado = 'Resuelto'";
            Query query = session.createQuery(hql);
            query.setParameter("tecnico", tecnico);
            Long tiempoEstimadoTotal = (Long) query.uniqueResult();
            return tiempoEstimadoTotal.intValue();
        } catch (HibernateException e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}


package controlador;

import modelo.Cliente;
import modelo.ReporteIncidencia;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import persistencia.ConfigHibernate;
import java.util.Collections;
import java.util.List;


public class GestorReporteIncidencia extends Gestor {

    public GestorReporteIncidencia() {
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
        // Implementa la lógica para actualizar un reporte en la base de datos o donde sea necesario
        // Puedes utilizar Hibernate u otro mecanismo de persistencia aquí
    }


}
package controlador;

import modelo.DatosContacto;
import modelo.OperadorMesaAyuda;
import org.hibernate.Query;
import org.hibernate.Transaction;
import persistencia.ConfigHibernate;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

public class GestorOperadorMesaAyuda extends Gestor {

    public GestorOperadorMesaAyuda() {
        if (sesion == null || !sesion.isOpen()) {
            sesion = ConfigHibernate.openSession();
        }
    }

    public List<OperadorMesaAyuda> obtenerTodosOperadoresMesaAyuda() {
        try {
            Query consulta = sesion.createQuery("FROM OperadorMesaAyuda");
            return consulta.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void agregarOperadorMesaAyuda(String apellido, String nombre, int legajo, DatosContacto datosContacto) {
        Transaction transaction = null;

        try {
            transaction = sesion.beginTransaction();

            OperadorMesaAyuda operadorMesaAyuda = new OperadorMesaAyuda(apellido, nombre, legajo, datosContacto);
            sesion.persist(operadorMesaAyuda);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error adding help desk operator: " + e.getMessage(), e);
        }
    }

    public void eliminarOperadorMesaAyuda(long legajo) {
        Transaction tx = null;
        try {
            tx = sesion.beginTransaction();
            OperadorMesaAyuda operadorMesaAyuda = getOperadorMesaAyudaXLegajo(legajo);
            if (operadorMesaAyuda != null) {
                sesion.delete(operadorMesaAyuda);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public OperadorMesaAyuda getOperadorMesaAyudaXLegajo(long legajo) {
        try {
            Query consulta = sesion.createQuery("FROM OperadorMesaAyuda WHERE legajo = :legajo");
            consulta.setParameter("legajo", legajo);

            return (OperadorMesaAyuda) consulta.uniqueResult();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }


}

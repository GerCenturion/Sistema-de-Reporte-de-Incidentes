package controlador;

import modelo.Cliente;
import modelo.ClienteServicio;
import modelo.Servicio;
import org.hibernate.Query;
import persistencia.ConfigHibernate;
import org.hibernate.Transaction;


import java.util.Collections;
import java.util.List;

public class GestorServicios extends Gestor {

    public GestorServicios() {
        if (sesion == null || !sesion.isOpen())
            sesion = ConfigHibernate.openSession();
    }

    public List<Servicio> obtenerTodosServicios() {
        try {
            Query consulta = sesion.createQuery("FROM Servicio");
            return consulta.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void agregarServicio(String denominacion) {
        Transaction transaction = null;

        try {
            transaction = sesion.beginTransaction();

            Servicio servicio = new Servicio();
            servicio.setDenominacion(denominacion);
            sesion.persist(servicio);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error adding service: " + e.getMessage(), e);
        }
    }

    public void eliminarServicio(long idServicio) {
        Transaction tx = null;
        try {
            tx = sesion.beginTransaction();
            Servicio servicio = getServicioById(idServicio);
            if (servicio != null) {
                sesion.delete(servicio);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public Servicio getServicioById(long idServicio) {
        try {
            return (Servicio) sesion.get(Servicio.class, idServicio);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }


    public List<ClienteServicio> obtenerServiciosCliente(Cliente cliente) {
        try {
            Query consulta = sesion.createQuery("FROM ClienteServicio WHERE cliente = :cliente");
            consulta.setParameter("cliente", cliente);

            return consulta.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void agregarServicioACliente(Cliente cliente, Servicio servicio) {
        Transaction transaction = null;

        try {
            transaction = sesion.beginTransaction();

            ClienteServicio clienteServicio = new ClienteServicio(cliente, servicio);
            sesion.persist(clienteServicio);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Error al agregar servicio al Cliente: " + e.getMessage(), e);
        }
    }


    public void eliminarServicioDeCliente(ClienteServicio clienteServicio) {
        Transaction tx = null;
        try {
            tx = sesion.beginTransaction();
            sesion.delete(clienteServicio);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}

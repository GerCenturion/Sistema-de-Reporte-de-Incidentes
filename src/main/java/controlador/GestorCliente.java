/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import java.util.List;
import java.util.Collections;
import modelo.Cliente;
import org.hibernate.Query;
import org.hibernate.Transaction;
import persistencia.ConfigHibernate;

public class GestorCliente extends Gestor {

    public GestorCliente() {
        if(sesion == null || !sesion.isOpen())
            sesion = ConfigHibernate.openSession();
    }
    
    public Cliente getClienteXCUIT(Long cuit){
        try {
        
            Query consulta = sesion.createQuery("SELECT cliente FROM Cliente cliente WHERE cliente.cuit = :cuit");
            consulta.setParameter("cuit", cuit);

            Cliente cliente = (Cliente) consulta.uniqueResult();
            return cliente;
            
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> obtenerTodosClientes() {
        try {
            Query consulta = sesion.createQuery("FROM Cliente");
            return consulta.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void agregarCliente(Cliente cliente) {
        Transaction transaction = null;
        try {
            transaction = sesion.beginTransaction();
            sesion.persist(cliente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Error al agregar cliente: " + e.getMessage(), e);
        }
    }


    public void eliminarCliente(long cuit) {
        // Lógica para eliminar un cliente de tu base de datos
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controlador;

import java.util.List;
import modelo.Cliente;
import org.hibernate.Query;
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
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Cliente;
import modelo.Especialidad;
import org.hibernate.Query;
import org.hibernate.Session;
import persistencia.ConfigHibernate;

public class GestorEspecialidad extends Gestor {

    public GestorEspecialidad() {
        if(sesion == null || !sesion.isOpen())
            sesion = ConfigHibernate.openSession();
    }
    
    /*
    public GestorEspecialidad(Session sesionParam) {
        sesion = sesionParam;
    }*/
    
    public Especialidad getEspecialidadXId(Long idEspecialidad){
        try {
        
            Query consulta = sesion.createQuery("FROM Especialidad WHERE id = :idEspecialidad");
            consulta.setParameter("idEspecialidad", idEspecialidad);

            Especialidad especialidad = (Especialidad) consulta.uniqueResult();
            return especialidad;
            
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}

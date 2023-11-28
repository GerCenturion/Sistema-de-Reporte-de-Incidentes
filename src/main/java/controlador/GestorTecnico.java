/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Cliente;
import modelo.Tecnico;
import org.hibernate.Query;
import persistencia.ConfigHibernate;
import java.util.List;

public class GestorTecnico extends Gestor {

    public GestorTecnico() {
        if(sesion == null || !sesion.isOpen())
            sesion = ConfigHibernate.openSession();
    }
    
    public Tecnico getTecnicoXLegajo(int legajo){
        try {
        
            Query consulta = sesion.createQuery("SELECT tecnico FROM Tecnico tecnico WHERE tecnico.legajo = :legajo");
            consulta.setParameter("legajo", legajo);

            Tecnico tecnico = (Tecnico) consulta.uniqueResult();
            return tecnico;
            
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tecnico> obtenerTodosTecnicosConEspecialidades() {
        try {
            Query consulta = sesion.createQuery("SELECT DISTINCT tecnico FROM Tecnico tecnico LEFT JOIN FETCH tecnico.tecnicoEspecialidades tEspecialidad LEFT JOIN FETCH tEspecialidad.especialidad");
            List<Tecnico> tecnicos = consulta.list();
            return tecnicos;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}

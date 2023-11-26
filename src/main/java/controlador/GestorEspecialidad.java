/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Especialidad;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import persistencia.ConfigHibernate;
import java.util.List;
import java.util.Collections;



public class GestorEspecialidad extends Gestor {

    public GestorEspecialidad() {
        if (sesion == null || !sesion.isOpen())
            sesion = ConfigHibernate.openSession();
    }

    public Especialidad getEspecialidadXId(Long idEspecialidad) {
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

    public void agregarEspecialidad(String denominacion) {
        Transaction tx = null;
        try {
            tx = sesion.beginTransaction();
            Especialidad especialidad = new Especialidad(denominacion);
            sesion.save(especialidad);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void eliminarEspecialidad(Long idEspecialidad) {
        Transaction tx = null;
        try {
            tx = sesion.beginTransaction();
            Especialidad especialidad = getEspecialidadXId(idEspecialidad);
            if (especialidad != null) {
                sesion.delete(especialidad);
                tx.commit();
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<Especialidad> obtenerTodasEspecialidades() {
        try {
            Query consulta = sesion.createQuery("FROM Especialidad");
            return consulta.list();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.GestorEspecialidad;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import modelo.Especialidad;
import modelo.Tecnico;
import modelo.TecnicoEspecialidad;
import org.hibernate.Session;

public class EspecialidadVista {

    public void cargarEspecialidadesTecnico(Tecnico tecnico) throws Exception {
        GestorEspecialidad gEspecialidad = new GestorEspecialidad();
        System.out.println("Lista de Especialidades");
        List<Especialidad> especialidades = gEspecialidad.listar(Especialidad.class);
        for(Especialidad esp : especialidades){
            System.out.println(esp.getId() + " " + esp.getDenominacion());
        }
        
        List<Long> idEspecialidades = new ArrayList<Long>();

        while(true){
            System.out.println("Seleccione la especialidad a asignar al tecnico");
            long idTecnico = new Scanner(System.in).nextLong();
            if(idEspecialidades.contains(idTecnico)){
                System.out.println("La especialidad seleccionada ya fue asignada, elija otra");
                continue;
            }
            idEspecialidades.add(idTecnico);
            
            Especialidad especialidad = (Especialidad) gEspecialidad.buscarPorId(Especialidad.class, idTecnico);
            if(especialidad != null){
                TecnicoEspecialidad te = new TecnicoEspecialidad();
                te.setEspecialidad(especialidad);
                te.setTecnico(tecnico);
                tecnico.addEspecialidad(te);
                System.out.println("La especialidad " + especialidad.getDenominacion() + " fue agregada exitosamente");
            }else{
                System.out.println("El id de la especialidad ingresada no existe");
            }
            System.out.println("Desea agregar otra especialidad?? S/N");
            String continuar = new Scanner(System.in).nextLine();
            if(!continuar.toUpperCase().equals("S")){
                break;
            }
        }
        
    }
    
}

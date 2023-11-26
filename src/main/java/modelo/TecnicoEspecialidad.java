/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Comparator;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arg_prog_tecnico_especialidad")
@Getter @Setter
public class TecnicoEspecialidad extends EntidadId implements Comparable<TecnicoEspecialidad>{

    public TecnicoEspecialidad(){}
    
    public TecnicoEspecialidad(Tecnico tecnico, Especialidad especialidad) {
        this.tecnico = tecnico;
        this.especialidad = especialidad;
    }
    
    
    @ManyToOne
    @JoinColumn(name = "idtecnico")
    private Tecnico tecnico;
    @ManyToOne
    @JoinColumn(name = "idespecialidad")
    private Especialidad especialidad;

    @Override
    public int compareTo(TecnicoEspecialidad o) {
        return new Integer(this.tecnico.getLegajo()).compareTo(o.getTecnico().getLegajo());
    }
    
   
}

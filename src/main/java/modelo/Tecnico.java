/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arg_prog_tecnico")
@Getter @Setter
public class Tecnico extends Empleado{

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReporteIncidencia> reportesIncidencia;//1 a N
    
    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TecnicoEspecialidad> tecnicoEspecialidades;// 1 a N
    
    
    public Tecnico(){}

    public Tecnico(String apellido, String nombre, int legajo, DatosContacto datosContacto) {
        super(apellido, nombre, legajo, datosContacto);
    }
    
    public void addEspecialidad(TecnicoEspecialidad tecnicoEspecialidad){
        if(this.tecnicoEspecialidades == null)
            this.tecnicoEspecialidades = new ArrayList<TecnicoEspecialidad>();
        this.tecnicoEspecialidades.add(tecnicoEspecialidad);
    }
    
  
    
}

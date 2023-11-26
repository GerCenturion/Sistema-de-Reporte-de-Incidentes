/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arg_prog_especialidad")
@Getter @Setter
public class Especialidad  extends EntidadId{

    @Column(length = 175, nullable = false)
    private String denominacion;
    
    @OneToMany(mappedBy = "especialidad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TecnicoEspecialidad> tecnicosEspecialidad;//1 a N
    
    public Especialidad(){}
    
    
    public Especialidad(String denominacion) {
        this.denominacion = denominacion;
    }
}

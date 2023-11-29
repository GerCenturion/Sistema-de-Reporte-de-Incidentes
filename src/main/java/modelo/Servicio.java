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
@Table(name = "arg_prog_servicio")
@Getter @Setter
public class Servicio extends EntidadId {

    @Column(nullable = false)
    private String denominacion;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClienteServicio> cliente;

    @OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReporteIncidencia> reportesIncidencia;


    
}

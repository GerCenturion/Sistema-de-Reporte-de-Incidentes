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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arg_prog_cliente")
@Getter @Setter
public class Cliente extends EntidadId{
    
    @Column(length = 150)
    private String razonSocial;
    
    @Column(nullable = false, unique = true)
    private long cuit;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ClienteServicio> servicios;
    
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReporteIncidencia> reportesIncidencia;//1 a N
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "iddatoscontacto")
    private DatosContacto datosContacto;//1 a 1

    public boolean tieneServicioContratado(Servicio servicio) {
        for (ClienteServicio clienteServicio : this.servicios) {
            if (clienteServicio.getServicio().equals(servicio)) {
                return true;
            }
        }
        return false;
    }
    public String getRazonSocial() {
        return this.razonSocial;
    }

}

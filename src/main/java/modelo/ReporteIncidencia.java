/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arg_prog_reporte_incidencia")
@Getter @Setter
public class ReporteIncidencia  extends EntidadId {
     
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;
    @Column(nullable = false)
    private String descripcionProblema;
    @Column(nullable = false)
    private String tipoProblema;//basico, intermedio, complejo
    @ManyToOne
    @JoinColumn(name = "idservicio")
    private Servicio servicio;//N a 1
    @ManyToOne
    @JoinColumn(name = "idoperador")
    private OperadorMesaAyuda operador;//N a 1
    @ManyToOne
    @JoinColumn(name = "idcliente", nullable = false)
    private Cliente cliente;//N a 1
    @ManyToOne
    @JoinColumn(name = "idtecnico", nullable = false)
    private Tecnico tecnico;//N a 1
    private int tiempoEstimadoResolucion;//horas o minutos
    @Temporal(TemporalType.DATE)
    private Date fechaPosibleResolucion;
    private String estado;//pendiente, en proceso, resuelto, anulado
    private String observacionesTecnico;
    
    
    
    
    
    
    
}

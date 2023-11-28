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
public class ReporteIncidencia extends EntidadId {

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaAlta;

    @Column(nullable = false)
    private String descripcionProblema;

    @Column(nullable = false)
    private String tipoProblema;

    @ManyToOne
    @JoinColumn(name = "idservicio")
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "idcliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idtecnico", nullable = false)
    private Tecnico tecnico;

    private int tiempoEstimadoResolucion;

    @Temporal(TemporalType.DATE)
    private Date fechaPosibleResolucion;

    private String estado;

    private String observacionesTecnico;

    public ReporteIncidencia() {
        // Constructor sin argumentos requerido por JPA
    }

    public ReporteIncidencia(Date fechaAlta, String descripcionProblema, String tipoProblema,
                             Servicio servicio, OperadorMesaAyuda operador, Cliente cliente,
                             Tecnico tecnico, int tiempoEstimadoResolucion, Date fechaPosibleResolucion,
                             String estado, String observacionesTecnico) {
        this.fechaAlta = fechaAlta;
        this.descripcionProblema = descripcionProblema;
        this.tipoProblema = tipoProblema;
        this.servicio = servicio;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.tiempoEstimadoResolucion = tiempoEstimadoResolucion;
        this.fechaPosibleResolucion = fechaPosibleResolucion;
        this.estado = estado;
        this.observacionesTecnico = observacionesTecnico;
    }

}

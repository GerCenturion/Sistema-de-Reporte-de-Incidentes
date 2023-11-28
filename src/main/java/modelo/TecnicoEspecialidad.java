package modelo;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arg_prog_tecnico_especialidad")
@Getter @Setter
public class TecnicoEspecialidad extends EntidadId {

    @ManyToOne
    @JoinColumn(name = "idtecnico")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "idespecialidad")
    private Especialidad especialidad;
}


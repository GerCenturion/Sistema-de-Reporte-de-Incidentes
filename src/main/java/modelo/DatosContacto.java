/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "arg_prog_datos_contacto")
@Getter @Setter
public class DatosContacto  extends EntidadId{

    private long telefono;
    private long celular;
    @Column(length = 75)
    private String email;
    
    public DatosContacto(){}
    
    public DatosContacto(long telefono, long celular, String email) {
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
    }
}

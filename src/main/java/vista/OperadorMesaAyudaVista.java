package vista;

import java.util.Scanner;
import modelo.DatosContacto;
import modelo.OperadorMesaAyuda;

public class OperadorMesaAyudaVista {

    public OperadorMesaAyuda cargarOperadorMesaAyudaNuevo(int legajo) {
        OperadorMesaAyuda operadorMesaAyuda = new OperadorMesaAyuda("", "", 0, new DatosContacto());
        System.out.println("Ingrese el apellido del Operador de Mesa de Ayuda");
        operadorMesaAyuda.setApellido(new Scanner(System.in).nextLine());
        System.out.println("Ingrese el nombre del Operador de Mesa de Ayuda");
        operadorMesaAyuda.setNombre(new Scanner(System.in).nextLine());
        operadorMesaAyuda.setLegajo(legajo);

        DatosContacto datosContacto = new DatosContacto();
        System.out.println("Ingrese el celular del Operador de Mesa de Ayuda");
        datosContacto.setCelular(new Scanner(System.in).nextLong());
        System.out.println("Ingrese el Email del Operador de Mesa de Ayuda");
        datosContacto.setEmail(new Scanner(System.in).nextLine());
        System.out.println("Ingrese el telefono del Operador de Mesa de Ayuda");
        datosContacto.setTelefono(new Scanner(System.in).nextLong());

        operadorMesaAyuda.setDatosContacto(datosContacto);

        return operadorMesaAyuda;
    }

    public OperadorMesaAyuda modificarOperadorMesaAyuda(OperadorMesaAyuda operadorMesaAyuda, int legajo) {
        System.out.println("Ingrese el apellido del Operador de Mesa de Ayuda");
        operadorMesaAyuda.setApellido(new Scanner(System.in).nextLine());
        System.out.println("Ingrese el nombre del Operador de Mesa de Ayuda");
        operadorMesaAyuda.setNombre(new Scanner(System.in).nextLine());
        operadorMesaAyuda.setLegajo(legajo);

        DatosContacto datosContacto = new DatosContacto();
        System.out.println("Ingrese el celular del Operador de Mesa de Ayuda");
        datosContacto.setCelular(new Scanner(System.in).nextLong());
        System.out.println("Ingrese el Email del Operador de Mesa de Ayuda");
        datosContacto.setEmail(new Scanner(System.in).nextLine());
        System.out.println("Ingrese el telefono del Operador de Mesa de Ayuda");
        datosContacto.setTelefono(new Scanner(System.in).nextLong());

        operadorMesaAyuda.setDatosContacto(datosContacto);

        return operadorMesaAyuda;
    }
}

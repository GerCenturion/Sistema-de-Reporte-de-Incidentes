/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.util.Scanner;
import java.util.List;
import modelo.Cliente;
import modelo.DatosContacto;

public class ClienteVista {
    
    
    public Cliente cargarClienteNuevo(){
        
        Cliente cliente = new Cliente();
        
        System.out.println("Ingrese el CUIT del Cliente");
        cliente.setCuit(new Scanner(System.in).nextLong());
        System.out.println("Ingrese la razon social del Cliente");
        cliente.setRazonSocial(new Scanner(System.in).nextLine());
        
        DatosContacto datosContacto = new DatosContacto();
        System.out.println("Ingrese el celular del Cliente");
        datosContacto.setCelular(new Scanner(System.in).nextLong());
        System.out.println("Ingrese el Email del Cliente");
        datosContacto.setEmail(new Scanner(System.in).nextLine());
        System.out.println("Ingrese el telefono del Cliente");
        datosContacto.setTelefono(new Scanner(System.in).nextLong());
        
        cliente.setDatosContacto(datosContacto);
        
        return cliente;
    
    }
    
    
    public Cliente modificarCliente(Cliente cliente){
        
        System.out.println("Ingrese el CUIT del Cliente");
        cliente.setCuit(new Scanner(System.in).nextLong());
        System.out.println("Ingrese la razon social del Cliente");
        cliente.setRazonSocial(new Scanner(System.in).nextLine());
        
        DatosContacto datosContacto = cliente.getDatosContacto();
        System.out.println("Ingrese el celular del Cliente");
        datosContacto.setCelular(new Scanner(System.in).nextLong());
        System.out.println("Ingrese el Email del Cliente");
        datosContacto.setEmail(new Scanner(System.in).nextLine());
        System.out.println("Ingrese el telefono del Cliente");
        datosContacto.setTelefono(new Scanner(System.in).nextLong());
        
        cliente.setDatosContacto(datosContacto);
        
        return cliente;
    
    }

    public void verClientes(List<Cliente> clientes) {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes para mostrar.");
        } else {
            System.out.println("Clientes disponibles:");
            for (Cliente cliente : clientes) {
                mostrarInformacionCliente(cliente);
                System.out.println("------------");
            }
        }
    }

    private void mostrarInformacionCliente(Cliente cliente) {
        System.out.println("CUIT: " + cliente.getCuit());
        System.out.println("Razón Social: " + cliente.getRazonSocial());

        DatosContacto datosContacto = cliente.getDatosContacto();
        System.out.println("Celular: " + datosContacto.getCelular());
        System.out.println("Email: " + datosContacto.getEmail());
        System.out.println("Teléfono: " + datosContacto.getTelefono());
    }
    
    
}

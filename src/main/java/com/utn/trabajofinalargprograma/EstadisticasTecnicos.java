package com.utn.trabajofinalargprograma;

import controlador.GestorReporteIncidencia;
import modelo.Tecnico;
import modelo.Servicio;
import java.util.List;

public class EstadisticasTecnicos {
    private final GestorReporteIncidencia gestorReporteIncidencia;

    public EstadisticasTecnicos(GestorReporteIncidencia gestorReporteIncidencia) {
        this.gestorReporteIncidencia = gestorReporteIncidencia;
    }

    public Tecnico obtenerTecnicoMasEficiente() {
        return gestorReporteIncidencia.obtenerTecnicoConMasReportesPorDia();
    }

    public void mostrarEstadisticasTecnicos() {
        List<Object[]> estadisticas = gestorReporteIncidencia.obtenerReporteIncidentesResueltosPorServicio();

        if (!estadisticas.isEmpty()) {
            System.out.println("Estadísticas de incidentes resueltos por servicio:");
            for (Object[] estadistica : estadisticas) {
                String servicio = (String) estadistica[0];
                long cantidadIncidentes = (long) estadistica[1];
                System.out.println("Servicio: " + servicio + ", Incidentes Resueltos: " + cantidadIncidentes);
            }
        } else {
            System.out.println("No hay estadísticas disponibles.");
        }
    }
        public String obtenerInfoTecnicoMasEficiente() {
            Tecnico tecnicoMasEficiente = gestorReporteIncidencia.obtenerTecnicoMasEficiente();

            if (tecnicoMasEficiente != null) {
                long tareasCompletadas = gestorReporteIncidencia.contarReportesResueltosPorTecnico(tecnicoMasEficiente);
                int tiempoEstimado = gestorReporteIncidencia.obtenerTiempoEstimadoTotalPorTecnico(tecnicoMasEficiente);

                return "Técnico más eficiente:\n" +
                        "Nombre: " + tecnicoMasEficiente.getNombre() + "\n" +
                        "Apellido: " + tecnicoMasEficiente.getApellido() + "\n" +
                        "Tareas Completadas: " + tareasCompletadas + "\n" +
                        "Tiempo Estimado Total: " + tiempoEstimado + " horas";
            } else {
                return "No hay técnicos eficientes disponibles.";
        }
    }


}




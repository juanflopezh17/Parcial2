package co.edu.uniquindio.poo.model;

import java.util.ArrayList;

public class DetalleServicio {

    private final String codigo;
    private final EquipoTecnologico equipo;
    private final Servicio servicio;
    private String descripcionFalla;
    private Tecnico tecnico;
    private double horasTrabajo;
    private String observacionesTecnicas;
    private EstadoIntervencion estado;
    private ArrayList<RepuestoUtilizado> listaRepuestos;

    public DetalleServicio(String codigo, EquipoTecnologico equipo, Servicio servicio, String descripcionFalla) {
        this.codigo = codigo;
        this.equipo = equipo;
        this.servicio = servicio;
        this.descripcionFalla = descripcionFalla;
        this.tecnico = null;
        this.horasTrabajo = 0;
        this.observacionesTecnicas = "";
        this.estado = EstadoIntervencion.PENDIENTE;
        this.listaRepuestos = new ArrayList<>();
    }

    private boolean estaCerrado() {
        return estado == EstadoIntervencion.FINALIZADA || estado == EstadoIntervencion.CANCELADA;
    }
    public String asignarTecnico(Tecnico tecnico) {
        if (estaCerrado()) {
            return "No se puede asignar tecnico a una intervencion " + estado;
        }
        if (!tecnico.estaActivo()) {
            return "El tecnico " + tecnico.getNombre() + " esta inactivo";
        }
        if (!tecnico.puedeAtender(equipo)) {
            return "El tecnico " + tecnico.getNombre() + " (" + tecnico.getEspecialidad()
                    + ") no esta autorizado para un equipo " + equipo.getCategoria();
        }
        this.tecnico = tecnico;
        return null;
    }
    public String agregarRepuesto(Repuesto repuesto, int cantidad) {
        if (!servicio.esReparacion()) {
            return "Solo las reparaciones correctivas usan repuestos";
        }
        if (estaCerrado()) {
            return "No se pueden agregar repuestos a una intervencion " + estado;
        }
        if (!repuesto.esCompatibleCon(equipo)) {
            return "El repuesto " + repuesto.getNombre() + " no es compatible con un equipo " + equipo.getCategoria();
        }
        if (!repuesto.hayDisponibilidad(cantidad)) {
            return "No hay existencias suficientes de " + repuesto.getNombre()
                    + " (disponible: " + repuesto.getCantidadDisponible() + ", solicitado: " + cantidad + ")";
        }
        repuesto.descontar(cantidad);
        listaRepuestos.add(new RepuestoUtilizado(repuesto, cantidad, repuesto.getPrecioUnitario()));
        return null;
    }
    public String registrarHoras(double horas) {
        if (horas < 0) {
            return "Las horas de trabajo no pueden ser negativas";
        }
        if (estaCerrado()) {
            return "No se pueden cambiar las horas de una intervencion " + estado;
        }
        this.horasTrabajo = horas;
        return null;
    }
    public String actualizarEstado(EstadoIntervencion nuevoEstado) {
        if (nuevoEstado == null) {
            return "Debe seleccionar un estado";
        }
        if (estaCerrado()) {
            return "La intervencion " + codigo + " ya esta " + estado;
        }
        if ((nuevoEstado == EstadoIntervencion.EN_PROCESO || nuevoEstado == EstadoIntervencion.FINALIZADA)
                && tecnico == null) {
            return "Debe asignar un tecnico antes de avanzar la intervencion";
        }
        if (nuevoEstado == EstadoIntervencion.FINALIZADA && servicio.esReparacion() && horasTrabajo <= 0) {
            return "Registre las horas de trabajo antes de finalizar la reparacion";
        }
        this.estado = nuevoEstado;
        return null;
    }

    public double calcularCostoRepuestos() {
        double total = 0;
        for (RepuestoUtilizado ru : listaRepuestos) {
            total += ru.calcularSubtotal();
        }
        return total;
    }
    public double calcularCosto() {
        return servicio.calcularCosto(this);
    }

    public String getCodigo() { return codigo; }

    public EquipoTecnologico getEquipo() { return equipo; }

    public Servicio getServicio() { return servicio; }

    public String getDescripcionFalla() { return descripcionFalla; }

    public void setDescripcionFalla(String descripcionFalla) { this.descripcionFalla = descripcionFalla; }

    public Tecnico getTecnico() { return tecnico; }

    public double getHorasTrabajo() { return horasTrabajo; }

    public void setHorasTrabajo(double horasTrabajo) { this.horasTrabajo = horasTrabajo; }

    public String getObservacionesTecnicas() { return observacionesTecnicas; }

    public void setObservacionesTecnicas(String observacionesTecnicas) {
        this.observacionesTecnicas = observacionesTecnicas;
    }

    public EstadoIntervencion getEstado() { return estado; }

    public ArrayList<RepuestoUtilizado> getListaRepuestos() { return listaRepuestos; }

    public void setListaRepuestos(ArrayList<RepuestoUtilizado> listaRepuestos) {
        this.listaRepuestos = listaRepuestos;
    }

    @Override
    public String toString() {
        String nombreTecnico = "SIN ASIGNAR";
        if (tecnico != null) {
            nombreTecnico = tecnico.getNombre();
        }
        return "DetalleServicio [codigo=" + codigo + ", equipo=" + equipo.getCodigoRegistro() + ", servicio="
                + servicio.getNombre() + ", falla=" + descripcionFalla + ", tecnico=" + nombreTecnico
                + ", horasTrabajo=" + horasTrabajo + ", estado=" + estado + ", costo=" + calcularCosto() + "]";
    }
}

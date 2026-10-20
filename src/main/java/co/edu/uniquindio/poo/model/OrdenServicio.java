package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrdenServicio {

    private final String codigo;
    private final LocalDate fechaCreacion;
    private final Cliente cliente;
    private String observaciones;
    private EstadoOrden estado;
    private ArrayList<DetalleServicio> listaDetalles;

    public OrdenServicio(String codigo, LocalDate fechaCreacion, Cliente cliente, String observaciones) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.cliente = cliente;
        this.observaciones = observaciones;
        this.estado = EstadoOrden.ABIERTA;
        this.listaDetalles = new ArrayList<>();
    }

    private boolean admiteCambios() {
        return estado == EstadoOrden.ABIERTA || estado == EstadoOrden.EN_PROCESO;
    }
    public String agregarDetalle(EquipoTecnologico equipo, Servicio servicio, String descripcionFalla) {
        if (!admiteCambios()) {
            return "No se pueden agregar detalles a una orden " + estado;
        }
        if (cliente.buscarEquipo(equipo.getCodigoRegistro()) == null) {
            return "El equipo no pertenece al cliente de la orden";
        }
        if (descripcionFalla == null || descripcionFalla.trim().isEmpty()) {
            return "La falla o necesidad reportada es obligatoria";
        }
        String codigoDetalle = "D-" + (listaDetalles.size() + 1);
        if (listaDetalles.size() + 1 < 10) {
            codigoDetalle = "D-0" + (listaDetalles.size() + 1);
        }
        listaDetalles.add(new DetalleServicio(codigoDetalle, equipo, servicio, descripcionFalla));
        return null;
    }

    public DetalleServicio buscarDetalle(String codigoDetalle) {
        for (DetalleServicio detalle : listaDetalles) {
            if (detalle.getCodigo().equalsIgnoreCase(codigoDetalle)) {
                return detalle;
            }
        }
        return null;
    }
    public String actualizarEstadoDetalle(String codigoDetalle, EstadoIntervencion nuevoEstado) {
        if (!admiteCambios()) {
            return "La orden esta " + estado + " y no admite cambios";
        }
        DetalleServicio detalle = buscarDetalle(codigoDetalle);
        if (detalle == null) {
            return "No existe el detalle " + codigoDetalle + " en la orden";
        }
        String error = detalle.actualizarEstado(nuevoEstado);
        if (error != null) {
            return error;
        }
        if (estado == EstadoOrden.ABIERTA && nuevoEstado == EstadoIntervencion.EN_PROCESO) {
            estado = EstadoOrden.EN_PROCESO;
        }
        return null;
    }
    public String finalizar() {
        if (!admiteCambios()) {
            return "La orden esta " + estado + " y no se puede finalizar";
        }
        if (listaDetalles.isEmpty()) {
            return "La orden no tiene detalles registrados";
        }
        for (DetalleServicio detalle : listaDetalles) {
            if (detalle.getEstado() != EstadoIntervencion.FINALIZADA) {
                return "Aun hay intervenciones sin finalizar en la orden " + codigo
                        + " (" + detalle.getCodigo() + " esta " + detalle.getEstado() + ")";
            }
        }
        estado = EstadoOrden.FINALIZADA;
        return null;
    }

    public ArrayList<EquipoTecnologico> obtenerEquipos() {
        ArrayList<EquipoTecnologico> equipos = new ArrayList<>();
        for (DetalleServicio detalle : listaDetalles) {
            if (!equipos.contains(detalle.getEquipo())) {
                equipos.add(detalle.getEquipo());
            }
        }
        return equipos;
    }

    public double calcularTotal() {
        double total = 0;
        for (DetalleServicio detalle : listaDetalles) {
            total += detalle.calcularCosto();
        }
        return total;
    }
    public String generarResumen() {
        String texto = "Orden " + codigo + " | " + fechaCreacion + " | " + estado + "\n";
        for (DetalleServicio d : listaDetalles) {
            texto += "   " + d.getCodigo() + " - " + d.getEquipo().getCategoria() + " "
                    + d.getEquipo().getCodigoRegistro() + " (" + d.getEquipo().getMarca() + " "
                    + d.getEquipo().getModelo() + ") | " + d.getServicio().getNombre() + " | "
                    + d.getEstado() + " | $" + String.format("%,.0f", d.calcularCosto()) + "\n";
        }
        texto += "   TOTAL: $" + String.format("%,.0f", calcularTotal()) + "\n";
        return texto;
    }

    public String getCodigo() { return codigo; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }

    public Cliente getCliente() { return cliente; }

    public String getObservaciones() { return observaciones; }

    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public EstadoOrden getEstado() { return estado; }

    public void setEstado(EstadoOrden estado) { this.estado = estado; }

    public ArrayList<DetalleServicio> getListaDetalles() { return listaDetalles; }

    public void setListaDetalles(ArrayList<DetalleServicio> listaDetalles) { this.listaDetalles = listaDetalles; }

    @Override
    public String toString() {
        return "OrdenServicio [codigo=" + codigo + ", fechaCreacion=" + fechaCreacion + ", cliente="
                + cliente.getNombreCompleto() + ", observaciones=" + observaciones + ", estado=" + estado
                + ", detalles=" + listaDetalles.size() + ", total=" + calcularTotal() + "]";
    }
}

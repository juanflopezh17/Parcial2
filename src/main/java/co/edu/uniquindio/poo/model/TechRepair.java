package co.edu.uniquindio.poo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TechRepair {

    public static final int LIMITE_INVENTARIO_CRITICO = 5;

    private String nombre;
    private String ciudad;
    private ArrayList<Cliente> listaClientes;
    private ArrayList<Tecnico> listaTecnicos;
    private ArrayList<Servicio> listaServicios;
    private Map<String, Repuesto> mapaRepuestos;
    private LinkedList<OrdenServicio> listaOrdenes;

    public TechRepair(String nombre, String ciudad) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.listaClientes = new ArrayList<>();
        this.listaTecnicos = new ArrayList<>();
        this.listaServicios = new ArrayList<>();
        this.mapaRepuestos = new HashMap<>();
        this.listaOrdenes = new LinkedList<>();
    }

    public String registrarCliente(String documento, String nombreCompleto, String telefono,
                                   String correo, String direccion) {
        Cliente cliente = new Cliente(documento, nombreCompleto, telefono, correo, direccion);
        String error = cliente.validarDatos();
        if (error != null) {
            return "Error: " + error;
        }
        if (buscarCliente(documento) != null) {
            return "Error: ya existe un cliente con el documento " + documento;
        }
        listaClientes.add(cliente);
        return "Cliente registrado correctamente:\n" + cliente;
    }

    public Cliente buscarCliente(String documento) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }
    public String registrarEquipo(String documentoCliente, EquipoTecnologico equipo) {
        Cliente cliente = buscarCliente(documentoCliente);
        if (cliente == null) {
            return "Error: no existe un cliente con el documento " + documentoCliente;
        }
        String error = equipo.validarDatos(); // valida segun la categoria del equipo
        if (error != null) {
            return "Error: " + error;
        }
        if (buscarEquipo(equipo.getCodigoRegistro()) != null) {
            return "Error: ya existe un equipo con el codigo " + equipo.getCodigoRegistro();
        }
        for (Cliente c : listaClientes) {
            for (EquipoTecnologico e : c.getListaEquipos()) {
                if (e.getNumeroSerie().equalsIgnoreCase(equipo.getNumeroSerie())) {
                    return "Error: ya existe un equipo con el numero de serie " + equipo.getNumeroSerie();
                }
            }
        }
        cliente.registrarEquipo(equipo);
        return "Equipo registrado correctamente:\n" + equipo;
    }

    public EquipoTecnologico buscarEquipo(String codigoRegistro) {
        for (Cliente cliente : listaClientes) {
            EquipoTecnologico equipo = cliente.buscarEquipo(codigoRegistro);
            if (equipo != null) {
                return equipo;
            }
        }
        return null;
    }
    public String registrarTecnico(String codigo, String nombreTecnico, String documento,
                                   Especialidad especialidad, EstadoLaboral estadoLaboral) {
        Tecnico tecnico = new Tecnico(codigo, nombreTecnico, documento, especialidad, estadoLaboral);
        String error = tecnico.validarDatos();
        if (error != null) {
            return "Error: " + error;
        }
        if (buscarTecnico(codigo) != null) {
            return "Error: ya existe un tecnico con el codigo " + codigo;
        }
        for (Tecnico t : listaTecnicos) {
            if (t.getDocumento().equals(documento)) {
                return "Error: ya existe un tecnico con el documento " + documento;
            }
        }
        listaTecnicos.add(tecnico);
        return "Tecnico registrado correctamente:\n" + tecnico;
    }

    public Tecnico buscarTecnico(String codigo) {
        for (Tecnico tecnico : listaTecnicos) {
            if (tecnico.getCodigo().equalsIgnoreCase(codigo)) {
                return tecnico;
            }
        }
        return null;
    }
    public void cargarCatalogoServicios() {
        registrarServicio(new Diagnostico("S-01", "Diagnostico", "Identifica las fallas de un equipo"));
        registrarServicio(new MantenimientoPreventivo("S-02", "Mantenimiento preventivo", "Limpieza, revision y optimizacion"));
        registrarServicio(new ReparacionCorrectiva("S-03", "Reparacion correctiva", "Solucion de fallas identificadas"));
    }

    public boolean registrarServicio(Servicio servicio) {
        if (buscarServicio(servicio.getCodigo()) != null) {
            return false;
        }
        listaServicios.add(servicio);
        return true;
    }

    public Servicio buscarServicio(String codigo) {
        for (Servicio servicio : listaServicios) {
            if (servicio.getCodigo().equalsIgnoreCase(codigo)) {
                return servicio;
            }
        }
        return null;
    }
    public String registrarRepuesto(String codigo, String nombreRepuesto, String descripcion,
                                    double precioUnitario, int cantidadInicial,
                                    CategoriaCompatibilidad categoria) {
        Repuesto repuesto = new Repuesto(codigo, nombreRepuesto, descripcion, precioUnitario, cantidadInicial, categoria);
        String error = repuesto.validarDatos();
        if (error != null) {
            return "Error: " + error;
        }
        if (buscarRepuesto(codigo) != null) {
            return "Error: ya existe un repuesto con el codigo " + codigo;
        }
        mapaRepuestos.put(codigo, repuesto);
        return "Repuesto registrado correctamente:\n" + repuesto;
    }

    public Repuesto buscarRepuesto(String codigo) {
        return mapaRepuestos.get(codigo);
    }
    public String crearOrden(String codigo, String documentoCliente, String observaciones) {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "Error: el codigo de la orden es obligatorio";
        }
        Cliente cliente = buscarCliente(documentoCliente);
        if (cliente == null) {
            return "Error: no existe un cliente con el documento " + documentoCliente;
        }
        if (buscarOrden(codigo) != null) {
            return "Error: ya existe la orden " + codigo;
        }
        OrdenServicio orden = new OrdenServicio(codigo, LocalDate.now(), cliente, observaciones);
        listaOrdenes.add(orden);
        return "Orden creada correctamente:\n" + orden;
    }

    public OrdenServicio buscarOrden(String codigo) {
        for (OrdenServicio orden : listaOrdenes) {
            if (orden.getCodigo().equalsIgnoreCase(codigo)) {
                return orden;
            }
        }
        return null;
    }
    public String agregarDetalle(String codigoOrden, String codigoEquipo, String codigoServicio,
                                 String descripcionFalla) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) {
            return "Error: no existe la orden " + codigoOrden;
        }
        EquipoTecnologico equipo = orden.getCliente().buscarEquipo(codigoEquipo);
        if (equipo == null) {
            return "Error: el cliente de la orden no tiene el equipo " + codigoEquipo;
        }
        Servicio servicio = buscarServicio(codigoServicio);
        if (servicio == null) {
            return "Error: no existe el servicio " + codigoServicio;
        }
        String error = orden.agregarDetalle(equipo, servicio, descripcionFalla);
        if (error != null) {
            return "Error: " + error;
        }
        DetalleServicio nuevo = orden.getListaDetalles().get(orden.getListaDetalles().size() - 1);
        return "Detalle agregado correctamente:\n" + nuevo;
    }
    public String asignarTecnico(String codigoOrden, String codigoDetalle, String codigoTecnico) {
        DetalleServicio detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle == null) {
            return "Error: no existe el detalle " + codigoDetalle + " en la orden " + codigoOrden;
        }
        Tecnico tecnico = buscarTecnico(codigoTecnico);
        if (tecnico == null) {
            return "Error: no existe el tecnico " + codigoTecnico;
        }
        String error = detalle.asignarTecnico(tecnico);
        if (error != null) {
            return "Error: " + error;
        }
        return "Tecnico " + tecnico.getNombre() + " asignado al detalle " + codigoDetalle;
    }
    public String registrarRepuestoUtilizado(String codigoOrden, String codigoDetalle, String codigoRepuesto,
                                             int cantidad) {
        DetalleServicio detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle == null) {
            return "Error: no existe el detalle " + codigoDetalle + " en la orden " + codigoOrden;
        }
        Repuesto repuesto = buscarRepuesto(codigoRepuesto);
        if (repuesto == null) {
            return "Error: no existe el repuesto " + codigoRepuesto;
        }
        String error = detalle.agregarRepuesto(repuesto, cantidad);
        if (error != null) {
            return "Error: " + error;
        }
        return "Repuesto registrado en la reparacion. Existencias restantes de " + repuesto.getNombre()
                + ": " + repuesto.getCantidadDisponible();
    }
    public String calcularValorDetalle(String codigoOrden, String codigoDetalle) {
        DetalleServicio detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle == null) {
            return "Error: no existe el detalle " + codigoDetalle + " en la orden " + codigoOrden;
        }
        return "Valor del detalle " + codigoDetalle + ": $" + String.format("%,.0f", detalle.calcularCosto());
    }

    public String calcularValorOrden(String codigoOrden) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) {
            return "Error: no existe la orden " + codigoOrden;
        }
        return "Valor total de la orden " + codigoOrden + ": $" + String.format("%,.0f", orden.calcularTotal());
    }
    public String registrarHorasDetalle(String codigoOrden, String codigoDetalle, double horas) {
        DetalleServicio detalle = buscarDetalle(codigoOrden, codigoDetalle);
        if (detalle == null) {
            return "Error: no existe el detalle " + codigoDetalle + " en la orden " + codigoOrden;
        }
        String error = detalle.registrarHoras(horas);
        if (error != null) {
            return "Error: " + error;
        }
        return "Horas registradas: " + horas;
    }

    public String actualizarEstadoDetalle(String codigoOrden, String codigoDetalle, EstadoIntervencion nuevoEstado) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) {
            return "Error: no existe la orden " + codigoOrden;
        }
        String error = orden.actualizarEstadoDetalle(codigoDetalle, nuevoEstado);
        if (error != null) {
            return "Error: " + error;
        }
        return "Detalle " + codigoDetalle + " actualizado a " + nuevoEstado;
    }
    public String finalizarOrden(String codigoOrden) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) {
            return "Error: no existe la orden " + codigoOrden;
        }
        String error = orden.finalizar();
        if (error != null) {
            return "Error: " + error;
        }
        return "Orden " + codigoOrden + " finalizada correctamente";
    }

    public String consultarOrdenesDeCliente(String documentoCliente) {
        if (buscarCliente(documentoCliente) == null) {
            return "Error: no existe un cliente con el documento " + documentoCliente;
        }
        String texto = "";
        for (OrdenServicio orden : listaOrdenes) {
            if (orden.getCliente().getDocumento().equals(documentoCliente)) {
                texto += orden.generarResumen() + "\n";
            }
        }
        if (texto.isEmpty()) {
            return "El cliente no tiene ordenes registradas";
        }
        return texto;
    }

    public String consultarTrabajosDeTecnico(String codigoTecnico) {
        Tecnico tecnico = buscarTecnico(codigoTecnico);
        if (tecnico == null) {
            return "Error: no existe el tecnico " + codigoTecnico;
        }
        ArrayList<DetalleServicio> todos = obtenerTodosLosDetalles();
        String texto = "";
        for (EstadoIntervencion estado : EstadoIntervencion.values()) {
            String grupo = "";
            int cantidad = 0;
            for (DetalleServicio d : todos) {
                if (d.getTecnico() == tecnico && d.getEstado() == estado) {
                    grupo += "   " + d + "\n";
                    cantidad++;
                }
            }
            if (cantidad > 0) {
                texto += estado + " (" + cantidad + "):\n" + grupo;
            }
        }
        if (texto.isEmpty()) {
            return "El tecnico " + tecnico.getNombre() + " no tiene trabajos asignados";
        }
        return "TRABAJOS DE " + tecnico.getNombre() + "\n" + texto;
    }
    public String consultarEquiposPendientes() {
        ArrayList<EquipoTecnologico> pendientes = new ArrayList<>();
        for (DetalleServicio d : obtenerTodosLosDetalles()) {
            boolean sinFinalizar = d.getEstado() == EstadoIntervencion.PENDIENTE
                    || d.getEstado() == EstadoIntervencion.EN_PROCESO;
            if (sinFinalizar && !pendientes.contains(d.getEquipo())) {
                pendientes.add(d.getEquipo());
            }
        }
        if (pendientes.isEmpty()) {
            return "No hay equipos con intervenciones pendientes";
        }
        String texto = "EQUIPOS PENDIENTES\n";
        for (EquipoTecnologico e : pendientes) {
            texto += "  - " + e.getCategoria() + " " + e.getCodigoRegistro() + " (" + e.getMarca() + " "
                    + e.getModelo() + ")\n";
        }
        return texto;
    }
    public String generarReporteServicios() {
        ArrayList<DetalleServicio> todos = obtenerTodosLosDetalles();
        String texto = "REPORTE DE SERVICIOS (intervenciones finalizadas)\n\n";

        texto += "Cantidad y total facturado por tipo de servicio:\n";
        for (TipoServicio tipo : TipoServicio.values()) {
            int cantidad = 0;
            double total = 0;
            for (DetalleServicio d : todos) {
                if (d.getEstado() == EstadoIntervencion.FINALIZADA && d.getServicio().getTipo() == tipo) {
                    cantidad++;
                    total += d.calcularCosto();
                }
            }
            texto += "  - " + tipo + ": " + cantidad + " servicio(s) | $" + String.format("%,.0f", total) + "\n";
        }

        texto += "\nTotal facturado por categoria de equipo:\n";
        for (CategoriaEquipo categoria : CategoriaEquipo.values()) {
            double total = 0;
            for (DetalleServicio d : todos) {
                if (d.getEstado() == EstadoIntervencion.FINALIZADA && d.getEquipo().getCategoria() == categoria) {
                    total += d.calcularCosto();
                }
            }
            texto += "  - " + categoria + ": $" + String.format("%,.0f", total) + "\n";
        }
        return texto;
    }
    public String generarReporteTecnicos() {
        ArrayList<DetalleServicio> todos = obtenerTodosLosDetalles();
        String texto = "REPORTE DE TECNICOS\n\n";
        for (Tecnico tecnico : listaTecnicos) {
            int finalizadas = 0;
            double valorAtendido = 0;
            for (DetalleServicio d : todos) {
                if (d.getTecnico() == tecnico && d.getEstado() == EstadoIntervencion.FINALIZADA) {
                    finalizadas++;
                    valorAtendido += d.calcularCosto();
                }
            }
            texto += "  - " + tecnico.getNombre() + " (" + tecnico.getEspecialidad() + "): " + finalizadas
                    + " intervencion(es) finalizada(s) | $" + String.format("%,.0f", valorAtendido) + "\n";
        }
        return texto;
    }
    public String consultarInventarioCritico() {
        String texto = "";
        for (Repuesto r : mapaRepuestos.values()) {
            if (r.getCantidadDisponible() < LIMITE_INVENTARIO_CRITICO) {
                texto += "  - " + r.getCodigo() + " " + r.getNombre() + ": " + r.getCantidadDisponible()
                        + " unidad(es)\n";
            }
        }
        if (texto.isEmpty()) {
            return "No hay repuestos con inventario critico";
        }
        return "INVENTARIO CRITICO (menos de " + LIMITE_INVENTARIO_CRITICO + " unidades)\n" + texto;
    }


    private DetalleServicio buscarDetalle(String codigoOrden, String codigoDetalle) {
        OrdenServicio orden = buscarOrden(codigoOrden);
        if (orden == null) {
            return null;
        }
        return orden.buscarDetalle(codigoDetalle);
    }

    private ArrayList<DetalleServicio> obtenerTodosLosDetalles() {
        ArrayList<DetalleServicio> todos = new ArrayList<>();
        for (OrdenServicio orden : listaOrdenes) {
            todos.addAll(orden.getListaDetalles());
        }
        return todos;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCiudad() { return ciudad; }

    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public ArrayList<Cliente> getListaClientes() { return listaClientes; }

    public ArrayList<Tecnico> getListaTecnicos() { return listaTecnicos; }

    public ArrayList<Servicio> getListaServicios() { return listaServicios; }

    public Map<String, Repuesto> getMapaRepuestos() { return mapaRepuestos; }

    public LinkedList<OrdenServicio> getListaOrdenes() { return listaOrdenes; }

    @Override
    public String toString() {
        return "TechRepair [nombre=" + nombre + ", ciudad=" + ciudad + ", clientes=" + listaClientes.size()
                + ", tecnicos=" + listaTecnicos.size() + ", servicios=" + listaServicios.size()
                + ", repuestos=" + mapaRepuestos.size() + ", ordenes=" + listaOrdenes.size() + "]";
    }
}

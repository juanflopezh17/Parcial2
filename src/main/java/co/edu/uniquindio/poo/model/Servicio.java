package co.edu.uniquindio.poo.model;

public abstract class Servicio {

    private final String codigo;
    private String nombre;
    private String descripcion;
    private double tarifaBase;

    public Servicio(String codigo, String nombre, String descripcion, double tarifaBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tarifaBase = tarifaBase;
    }
    public abstract double calcularCosto(DetalleServicio detalle);

    public abstract TipoServicio getTipo();

    public boolean esReparacion() {
        return false;
    }

    public String getCodigo() { return codigo; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getTarifaBase() { return tarifaBase; }

    public void setTarifaBase(double tarifaBase) { this.tarifaBase = tarifaBase; }

    @Override
    public String toString() {
        return "codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", tarifaBase=" + tarifaBase;
    }
}

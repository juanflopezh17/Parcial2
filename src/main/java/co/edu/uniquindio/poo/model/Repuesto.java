package co.edu.uniquindio.poo.model;

public class Repuesto {

    private final String codigo;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private int cantidadDisponible;
    private CategoriaCompatibilidad categoriaCompatibilidad;

    public Repuesto(String codigo, String nombre, String descripcion, double precioUnitario,
                    int cantidadDisponible, CategoriaCompatibilidad categoriaCompatibilidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.cantidadDisponible = cantidadDisponible;
        this.categoriaCompatibilidad = categoriaCompatibilidad;
    }
    public String validarDatos() {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "El codigo del repuesto es obligatorio";
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre del repuesto es obligatorio";
        }
        if (precioUnitario <= 0) {
            return "El precio unitario debe ser mayor que cero";
        }
        if (cantidadDisponible < 0) {
            return "La cantidad disponible no puede ser negativa";
        }
        if (categoriaCompatibilidad == null) {
            return "Debe seleccionar una categoria de compatibilidad";
        }
        return null;
    }

    public boolean esCompatibleCon(EquipoTecnologico equipo) {
        if (categoriaCompatibilidad == CategoriaCompatibilidad.UNIVERSAL) {
            return true;
        }
        if (categoriaCompatibilidad == CategoriaCompatibilidad.PORTATIL) {
            return equipo.getCategoria() == CategoriaEquipo.PORTATIL;
        }
        if (categoriaCompatibilidad == CategoriaCompatibilidad.ESCRITORIO) {
            return equipo.getCategoria() == CategoriaEquipo.ESCRITORIO;
        }
        return equipo.getCategoria() == CategoriaEquipo.SMARTPHONE;
    }

    public boolean hayDisponibilidad(int cantidad) {
        return cantidad > 0 && cantidad <= cantidadDisponible;
    }
    public boolean descontar(int cantidad) {
        if (!hayDisponibilidad(cantidad)) {
            return false;
        }
        cantidadDisponible -= cantidad;
        return true;
    }

    public String getCodigo() { return codigo; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecioUnitario() { return precioUnitario; }

    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public int getCantidadDisponible() { return cantidadDisponible; }

    public void setCantidadDisponible(int cantidadDisponible) { this.cantidadDisponible = cantidadDisponible; }

    public CategoriaCompatibilidad getCategoriaCompatibilidad() { return categoriaCompatibilidad; }

    public void setCategoriaCompatibilidad(CategoriaCompatibilidad categoriaCompatibilidad) {
        this.categoriaCompatibilidad = categoriaCompatibilidad;
    }

    @Override
    public String toString() {
        return "Repuesto [codigo=" + codigo + ", nombre=" + nombre + ", descripcion=" + descripcion
                + ", precioUnitario=" + precioUnitario + ", cantidadDisponible=" + cantidadDisponible
                + ", categoriaCompatibilidad=" + categoriaCompatibilidad + "]";
    }
}

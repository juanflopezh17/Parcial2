package co.edu.uniquindio.poo.model;

public class RepuestoUtilizado {

    private final Repuesto repuesto;
    private final int cantidad;
    private final double precioUnitarioAplicado;

    public RepuestoUtilizado(Repuesto repuesto, int cantidad, double precioUnitarioAplicado) {
        this.repuesto = repuesto;
        this.cantidad = cantidad;
        this.precioUnitarioAplicado = precioUnitarioAplicado;
    }

    public double calcularSubtotal() {
        return cantidad * precioUnitarioAplicado;
    }

    public Repuesto getRepuesto() { return repuesto; }

    public int getCantidad() { return cantidad; }

    public double getPrecioUnitarioAplicado() { return precioUnitarioAplicado; }

    @Override
    public String toString() {
        return "RepuestoUtilizado [repuesto=" + repuesto.getNombre() + ", cantidad=" + cantidad
                + ", precioUnitarioAplicado=" + precioUnitarioAplicado + ", subtotal=" + calcularSubtotal() + "]";
    }
}
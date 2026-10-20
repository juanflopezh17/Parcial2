package co.edu.uniquindio.poo.model;

public class MantenimientoPreventivo extends Servicio {

    public static final double TARIFA_PORTATIL = 60000;
    public static final double TARIFA_ESCRITORIO = 70000;
    public static final double TARIFA_SMARTPHONE = 40000;


    public MantenimientoPreventivo(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, TARIFA_SMARTPHONE);
    }

    @Override
    public TipoServicio getTipo() { return TipoServicio.MANTENIMIENTO_PREVENTIVO; }

    @Override
    public double calcularCosto(DetalleServicio detalle) {
        switch (detalle.getEquipo().getCategoria()) {
            case PORTATIL:
                return TARIFA_PORTATIL;
            case ESCRITORIO:
                return TARIFA_ESCRITORIO;
            default:
                return TARIFA_SMARTPHONE;
        }
    }

    @Override
    public String toString() {
        return "MantenimientoPreventivo [" + super.toString() + "]";
    }
}

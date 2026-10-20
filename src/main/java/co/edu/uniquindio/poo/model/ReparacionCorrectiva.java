package co.edu.uniquindio.poo.model;

public class ReparacionCorrectiva extends Servicio {

    public static final double COSTO_HORA_MANO_OBRA = 45000;

    public ReparacionCorrectiva(String codigo, String nombre, String descripcion) {
        super(codigo, nombre, descripcion, COSTO_HORA_MANO_OBRA);
    }

    public double calcularManoDeObra(DetalleServicio detalle) {
        return getTarifaBase() * detalle.getHorasTrabajo();
    }

    @Override
    public boolean esReparacion() {
        return true;
    }

    @Override
    public TipoServicio getTipo() { return TipoServicio.REPARACION_CORRECTIVA; }

    @Override
    public double calcularCosto(DetalleServicio detalle) {
        return calcularManoDeObra(detalle) + detalle.calcularCostoRepuestos();
    }

    @Override
    public String toString() {
        return "ReparacionCorrectiva [" + super.toString() + "]";
    }
}

package co.edu.uniquindio.poo.model;

public class Escritorio extends EquipoTecnologico {

    private String tipoProcesador;
    private int memoriaRam;         // GB
    private int almacenamiento;     // GB
    private boolean tarjetaGraficaDedicada;

    public Escritorio(String codigoRegistro, String numeroSerie, String marca, String modelo,
                      int anioFabricacion, String estadoFisico, String tipoProcesador,
                      int memoriaRam, int almacenamiento, boolean tarjetaGraficaDedicada) {
        super(codigoRegistro, numeroSerie, marca, modelo, anioFabricacion, estadoFisico);
        this.tipoProcesador = tipoProcesador;
        this.memoriaRam = memoriaRam;
        this.almacenamiento = almacenamiento;
        this.tarjetaGraficaDedicada = tarjetaGraficaDedicada;
    }

    @Override
    public CategoriaEquipo getCategoria() { return CategoriaEquipo.ESCRITORIO; }

    @Override
    public String validarDatos() {
        String error = super.validarDatos();
        if (error != null) {
            return error;
        }
        if (estaVacio(tipoProcesador)) {
            return "El tipo de procesador es obligatorio";
        }
        if (memoriaRam <= 0) {
            return "La memoria RAM debe ser mayor que cero";
        }
        if (almacenamiento <= 0) {
            return "El almacenamiento debe ser mayor que cero";
        }
        return null;
    }

    public String getTipoProcesador() { return tipoProcesador; }

    public void setTipoProcesador(String tipoProcesador) { this.tipoProcesador = tipoProcesador; }

    public int getMemoriaRam() { return memoriaRam; }

    public void setMemoriaRam(int memoriaRam) { this.memoriaRam = memoriaRam; }

    public int getAlmacenamiento() { return almacenamiento; }

    public void setAlmacenamiento(int almacenamiento) { this.almacenamiento = almacenamiento; }

    public boolean isTarjetaGraficaDedicada() { return tarjetaGraficaDedicada; }

    public void setTarjetaGraficaDedicada(boolean tarjetaGraficaDedicada) {
        this.tarjetaGraficaDedicada = tarjetaGraficaDedicada;
    }

    @Override
    public String toString() {
        return "Escritorio [" + super.toString() + ", tipoProcesador=" + tipoProcesador + ", memoriaRam="
                + memoriaRam + "GB, almacenamiento=" + almacenamiento + "GB, tarjetaGraficaDedicada="
                + tarjetaGraficaDedicada + "]";
    }
}
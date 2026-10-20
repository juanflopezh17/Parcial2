package co.edu.uniquindio.poo.model;

public class Smartphone extends EquipoTecnologico {

    private String sistemaOperativo;
    private int almacenamiento;      // GB
    private int capacidadBateriaMah;
    private boolean compatible5G;

    public Smartphone(String codigoRegistro, String numeroSerie, String marca, String modelo,
                      int anioFabricacion, String estadoFisico, String sistemaOperativo,
                      int almacenamiento, int capacidadBateriaMah, boolean compatible5G) {
        super(codigoRegistro, numeroSerie, marca, modelo, anioFabricacion, estadoFisico);
        this.sistemaOperativo = sistemaOperativo;
        this.almacenamiento = almacenamiento;
        this.capacidadBateriaMah = capacidadBateriaMah;
        this.compatible5G = compatible5G;
    }

    @Override
    public CategoriaEquipo getCategoria() { return CategoriaEquipo.SMARTPHONE; }

    @Override
    public String validarDatos() {
        String error = super.validarDatos();
        if (error != null) {
            return error;
        }
        if (estaVacio(sistemaOperativo)) {
            return "El sistema operativo es obligatorio";
        }
        if (almacenamiento <= 0) {
            return "El almacenamiento debe ser mayor que cero";
        }
        if (capacidadBateriaMah <= 0) {
            return "La capacidad de bateria debe ser mayor que cero";
        }
        return null;
    }

    public String getSistemaOperativo() { return sistemaOperativo; }

    public void setSistemaOperativo(String sistemaOperativo) { this.sistemaOperativo = sistemaOperativo; }

    public int getAlmacenamiento() { return almacenamiento; }

    public void setAlmacenamiento(int almacenamiento) { this.almacenamiento = almacenamiento; }

    public int getCapacidadBateriaMah() { return capacidadBateriaMah; }

    public void setCapacidadBateriaMah(int capacidadBateriaMah) { this.capacidadBateriaMah = capacidadBateriaMah; }

    public boolean isCompatible5G() { return compatible5G; }

    public void setCompatible5G(boolean compatible5G) { this.compatible5G = compatible5G; }

    @Override
    public String toString() {
        return "Smartphone [" + super.toString() + ", sistemaOperativo=" + sistemaOperativo
                + ", almacenamiento=" + almacenamiento + "GB, capacidadBateriaMah=" + capacidadBateriaMah
                + ", compatible5G=" + compatible5G + "]";
    }
}
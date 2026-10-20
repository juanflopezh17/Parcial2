package co.edu.uniquindio.poo.model;

public class Portatil extends EquipoTecnologico {

    private double tamanioPantalla; // pulgadas
    private int memoriaRam;         // GB
    private int almacenamiento;     // GB
    private boolean bateriaExtraible;

    public Portatil(String codigoRegistro, String numeroSerie, String marca, String modelo,
                    int anioFabricacion, String estadoFisico, double tamanioPantalla,
                    int memoriaRam, int almacenamiento, boolean bateriaExtraible) {
        super(codigoRegistro, numeroSerie, marca, modelo, anioFabricacion, estadoFisico);
        this.tamanioPantalla = tamanioPantalla;
        this.memoriaRam = memoriaRam;
        this.almacenamiento = almacenamiento;
        this.bateriaExtraible = bateriaExtraible;
    }

    @Override
    public CategoriaEquipo getCategoria() { return CategoriaEquipo.PORTATIL; }

    @Override
    public String validarDatos() {
        String error = super.validarDatos();
        if (error != null) {
            return error;
        }
        if (tamanioPantalla <= 0) {
            return "El tamanio de pantalla debe ser mayor que cero";
        }
        if (memoriaRam <= 0) {
            return "La memoria RAM debe ser mayor que cero";
        }
        if (almacenamiento <= 0) {
            return "El almacenamiento debe ser mayor que cero";
        }
        return null;
    }

    public double getTamanioPantalla() { return tamanioPantalla; }

    public void setTamanioPantalla(double tamanioPantalla) { this.tamanioPantalla = tamanioPantalla; }

    public int getMemoriaRam() { return memoriaRam; }

    public void setMemoriaRam(int memoriaRam) { this.memoriaRam = memoriaRam; }

    public int getAlmacenamiento() { return almacenamiento; }

    public void setAlmacenamiento(int almacenamiento) { this.almacenamiento = almacenamiento; }

    public boolean isBateriaExtraible() { return bateriaExtraible; }

    public void setBateriaExtraible(boolean bateriaExtraible) { this.bateriaExtraible = bateriaExtraible; }

    @Override
    public String toString() {
        return "Portatil [" + super.toString() + ", tamanioPantalla=" + tamanioPantalla + ", memoriaRam="
                + memoriaRam + "GB, almacenamiento=" + almacenamiento + "GB, bateriaExtraible="
                + bateriaExtraible + "]";
    }
}

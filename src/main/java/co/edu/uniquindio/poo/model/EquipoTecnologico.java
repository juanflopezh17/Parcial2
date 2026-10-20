package co.edu.uniquindio.poo.model;

import java.time.LocalDate;

public abstract class EquipoTecnologico {

    private final String codigoRegistro;
    private final String numeroSerie;
    private String marca;
    private String modelo;
    private int anioFabricacion;
    private String estadoFisico;

    public EquipoTecnologico(String codigoRegistro, String numeroSerie, String marca, String modelo,
                             int anioFabricacion, String estadoFisico) {
        this.codigoRegistro = codigoRegistro;
        this.numeroSerie = numeroSerie;
        this.marca = marca;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.estadoFisico = estadoFisico;
    }

    public abstract CategoriaEquipo getCategoria();

    protected boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
    public String validarDatos() {
        if (estaVacio(codigoRegistro)) {
            return "El codigo de registro es obligatorio";
        }
        if (estaVacio(numeroSerie)) {
            return "El numero de serie es obligatorio";
        }
        if (estaVacio(marca)) {
            return "La marca es obligatoria";
        }
        if (estaVacio(modelo)) {
            return "El modelo es obligatorio";
        }
        if (estaVacio(estadoFisico)) {
            return "El estado fisico es obligatorio";
        }
        if (anioFabricacion < 1980 || anioFabricacion > LocalDate.now().getYear()) {
            return "El anio de fabricacion no es valido";
        }
        return null;
    }

    public String getCodigoRegistro() { return codigoRegistro; }

    public String getNumeroSerie() { return numeroSerie; }

    public String getMarca() { return marca; }

    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }

    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnioFabricacion() { return anioFabricacion; }

    public void setAnioFabricacion(int anioFabricacion) { this.anioFabricacion = anioFabricacion; }

    public String getEstadoFisico() { return estadoFisico; }

    public void setEstadoFisico(String estadoFisico) { this.estadoFisico = estadoFisico; }

    @Override
    public String toString() {
        return "codigoRegistro=" + codigoRegistro + ", numeroSerie=" + numeroSerie + ", marca=" + marca
                + ", modelo=" + modelo + ", anioFabricacion=" + anioFabricacion
                + ", estadoFisico=" + estadoFisico;
    }
}
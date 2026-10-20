package co.edu.uniquindio.poo.model;

import java.util.ArrayList;

public class Cliente {

    private final String documento;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String direccion;
    private ArrayList<EquipoTecnologico> listaEquipos;

    public Cliente(String documento, String nombreCompleto, String telefono, String correo, String direccion) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.listaEquipos = new ArrayList<>();
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private boolean soloDigitos(String texto) {
        if (estaVacio(texto)) {
            return false;
        }
        for (int i = 0; i < texto.length(); i++) {
            if (!Character.isDigit(texto.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    // Devuelve null si los datos son validos
    public String validarDatos() {
        if (!soloDigitos(documento) || documento.length() < 5 || documento.length() > 15) {
            return "El documento debe tener entre 5 y 15 digitos";
        }
        if (estaVacio(nombreCompleto)) {
            return "El nombre completo es obligatorio";
        }
        if (!soloDigitos(telefono) || telefono.length() < 7 || telefono.length() > 10) {
            return "El telefono debe tener entre 7 y 10 digitos";
        }
        if (estaVacio(correo) || !correo.contains("@") || !correo.contains(".")
                || correo.indexOf("@") > correo.lastIndexOf(".") || correo.contains(" ")) {
            return "El correo electronico no es valido";
        }
        if (estaVacio(direccion)) {
            return "La direccion es obligatoria";
        }
        return null;
    }
    public boolean registrarEquipo(EquipoTecnologico equipo) {
        if (buscarEquipo(equipo.getCodigoRegistro()) != null) {
            return false;
        }
        listaEquipos.add(equipo);
        return true;
    }

    public EquipoTecnologico buscarEquipo(String codigoRegistro) {
        for (EquipoTecnologico equipo : listaEquipos) {
            if (equipo.getCodigoRegistro().equals(codigoRegistro)) {
                return equipo;
            }
        }
        return null;
    }

    public String getDocumento() { return documento; }

    public String getNombreCompleto() { return nombreCompleto; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getTelefono() { return telefono; }

    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }

    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    public ArrayList<EquipoTecnologico> getListaEquipos() { return listaEquipos; }

    public void setListaEquipos(ArrayList<EquipoTecnologico> listaEquipos) { this.listaEquipos = listaEquipos; }

    @Override
    public String toString() {
        return "Cliente [documento=" + documento + ", nombreCompleto=" + nombreCompleto + ", telefono="
                + telefono + ", correo=" + correo + ", direccion=" + direccion + ", equipos="
                + listaEquipos.size() + "]";
    }
}

package co.edu.uniquindio.poo.model;

public class Tecnico {

    private final String codigo;
    private String nombre;
    private final String documento;
    private Especialidad especialidad;
    private EstadoLaboral estadoLaboral;

    public Tecnico(String codigo, String nombre, String documento, Especialidad especialidad,
                   EstadoLaboral estadoLaboral) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.documento = documento;
        this.especialidad = especialidad;
        this.estadoLaboral = estadoLaboral;
    }

  public String validarDatos() {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "El codigo del tecnico es obligatorio";
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre del tecnico es obligatorio";
        }
        if (documento == null || documento.trim().isEmpty()) {
            return "El documento del tecnico es obligatorio";
        }
        if (especialidad == null) {
            return "Debe seleccionar una especialidad";
        }
        if (estadoLaboral == null) {
            return "Debe seleccionar un estado laboral";
        }
        return null;
    }

    public boolean estaActivo() {
        return estadoLaboral == EstadoLaboral.ACTIVO;
    }

    public boolean puedeAtender(EquipoTecnologico equipo) {
        if (especialidad == Especialidad.COMPUTADORES) {
            return equipo.getCategoria() == CategoriaEquipo.PORTATIL
                    || equipo.getCategoria() == CategoriaEquipo.ESCRITORIO;
        }
        if (especialidad == Especialidad.DISPOSITIVOS_MOVILES) {
            return equipo.getCategoria() == CategoriaEquipo.SMARTPHONE;
        }
        return true;
    }

    public String getCodigo() { return codigo; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDocumento() { return documento; }

    public Especialidad getEspecialidad() { return especialidad; }

    public void setEspecialidad(Especialidad especialidad) { this.especialidad = especialidad; }

    public EstadoLaboral getEstadoLaboral() { return estadoLaboral; }

    public void setEstadoLaboral(EstadoLaboral estadoLaboral) { this.estadoLaboral = estadoLaboral; }

    @Override
    public String toString() {
        return "Tecnico [codigo=" + codigo + ", nombre=" + nombre + ", documento=" + documento
                + ", especialidad=" + especialidad + ", estadoLaboral=" + estadoLaboral + "]";
    }
}
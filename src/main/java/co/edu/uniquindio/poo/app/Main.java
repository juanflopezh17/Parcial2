package co.edu.uniquindio.poo.app;

import co.edu.uniquindio.poo.model.*;

import javax.swing.JOptionPane;

/** Menu principal: solo pide datos y delega toda la logica en las clases del modelo. */
public class Main {

    private static final TechRepair techRepair = new TechRepair("TechRepair", "Armenia, Quindio");

    public static void main(String[] args) {
        techRepair.cargarCatalogoServicios();
        int opcion;
        do {
            opcion = leerEntero(menu());
            ejecutar(opcion);
        } while (opcion != 0);
    }

    private static String menu() {
        return "TECHREPAIR - MENU PRINCIPAL\n\n"
                + " 1. Registrar cliente\n 2. Registrar equipo\n 3. Registrar tecnico\n 4. Registrar repuesto\n"
                + " 5. Crear orden de servicio\n 6. Agregar detalle a una orden\n 7. Asignar tecnico\n"
                + " 8. Registrar repuestos utilizados\n 9. Calcular valor de un detalle\n"
                + "10. Calcular valor total de la orden\n11. Actualizar estado de un detalle\n"
                + "12. Finalizar una orden\n13. Consultar ordenes de un cliente\n"
                + "14. Consultar trabajos de un tecnico\n15. Consultar equipos pendientes\n"
                + "16. Reporte de servicios\n17. Reporte de tecnicos\n18. Inventario critico\n"
                + "0. Salir";
    }

    private static void ejecutar(int opcion) {
        switch (opcion) {
            case 1:
                mostrar(techRepair.registrarCliente(leerTexto("Documento:"), leerTexto("Nombre completo:"),
                        leerTexto("Telefono:"), leerTexto("Correo:"), leerTexto("Direccion:")));
                break;
            case 2:
                registrarEquipo();
                break;
            case 3:
                mostrar(techRepair.registrarTecnico(leerTexto("Codigo:"), leerTexto("Nombre:"),
                        leerTexto("Documento:"), (Especialidad) elegir("Especialidad:", Especialidad.values()),
                        (EstadoLaboral) elegir("Estado laboral:", EstadoLaboral.values())));
                break;
            case 4:
                mostrar(techRepair.registrarRepuesto(leerTexto("Codigo:"), leerTexto("Nombre:"),
                        leerTexto("Descripcion:"), leerDouble("Precio unitario:"), leerEntero("Existencias iniciales:"),
                        (CategoriaCompatibilidad) elegir("Categoria de compatibilidad:", CategoriaCompatibilidad.values())));
                break;
            case 5:
                mostrar(techRepair.crearOrden(leerTexto("Codigo de la orden:"), leerTexto("Documento del cliente:"),
                        leerTexto("Observaciones generales:")));
                break;
            case 6:
                mostrar(techRepair.agregarDetalle(leerTexto("Codigo de la orden:"), leerTexto("Codigo del equipo:"),
                        leerTexto("Codigo del servicio (S-01, S-02, S-03):"), leerTexto("Falla o necesidad reportada:")));
                break;
            case 7:
                mostrar(techRepair.asignarTecnico(leerTexto("Codigo de la orden:"), leerTexto("Codigo del detalle:"),
                        leerTexto("Codigo del tecnico:")));
                break;
            case 8:
                mostrar(techRepair.registrarRepuestoUtilizado(leerTexto("Codigo de la orden:"),
                        leerTexto("Codigo del detalle:"), leerTexto("Codigo del repuesto:"), leerEntero("Cantidad:")));
                break;
            case 9:
                mostrar(techRepair.calcularValorDetalle(leerTexto("Codigo de la orden:"), leerTexto("Codigo del detalle:")));
                break;
            case 10:
                mostrar(techRepair.calcularValorOrden(leerTexto("Codigo de la orden:")));
                break;
            case 11:
                actualizarEstadoDetalle();
                break;
            case 12:
                mostrar(techRepair.finalizarOrden(leerTexto("Codigo de la orden:")));
                break;
            case 13:
                mostrar(techRepair.consultarOrdenesDeCliente(leerTexto("Documento del cliente:")));
                break;
            case 14:
                mostrar(techRepair.consultarTrabajosDeTecnico(leerTexto("Codigo del tecnico:")));
                break;
            case 15:
                mostrar(techRepair.consultarEquiposPendientes());
                break;
            case 16:
                mostrar(techRepair.generarReporteServicios());
                break;
            case 17:
                mostrar(techRepair.generarReporteTecnicos());
                break;
            case 18:
                mostrar(techRepair.consultarInventarioCritico());
                break;
            case 0:
                mostrar("Hasta pronto");
                break;
            default:
                mostrar("Opcion no valida");
        }
    }

    // ---------- Opciones que requieren varios datos ----------
    private static void registrarEquipo() {
        String documento = leerTexto("Documento del cliente:");
        CategoriaEquipo categoria = (CategoriaEquipo) elegir("Categoria del equipo:", CategoriaEquipo.values());
        if (categoria == null) {
            mostrar("Operacion cancelada");
            return;
        }
        String codigo = leerTexto("Codigo de registro:");
        String serie = leerTexto("Numero de serie:");
        String marca = leerTexto("Marca:");
        String modelo = leerTexto("Modelo:");
        int anio = leerEntero("Anio de fabricacion:");
        String estadoFisico = leerTexto("Estado fisico al ingresar:");
        EquipoTecnologico equipo;
        if (categoria == CategoriaEquipo.PORTATIL) {
            equipo = new Portatil(codigo, serie, marca, modelo, anio, estadoFisico,
                    leerDouble("Tamanio de pantalla (pulgadas):"), leerEntero("Memoria RAM (GB):"),
                    leerEntero("Almacenamiento (GB):"), confirmar("Tiene bateria extraible?"));
        } else if (categoria == CategoriaEquipo.ESCRITORIO) {
            equipo = new Escritorio(codigo, serie, marca, modelo, anio, estadoFisico,
                    leerTexto("Tipo de procesador:"), leerEntero("Memoria RAM (GB):"),
                    leerEntero("Almacenamiento (GB):"), confirmar("Tiene tarjeta grafica dedicada?"));
        } else {
            equipo = new Smartphone(codigo, serie, marca, modelo, anio, estadoFisico,
                    leerTexto("Sistema operativo:"), leerEntero("Almacenamiento (GB):"),
                    leerEntero("Capacidad de bateria (mAh):"), confirmar("Es compatible con 5G?"));
        }
        mostrar(techRepair.registrarEquipo(documento, equipo));
    }

    private static void actualizarEstadoDetalle() {
        String codigoOrden = leerTexto("Codigo de la orden:");
        String codigoDetalle = leerTexto("Codigo del detalle:");
        EstadoIntervencion estado = (EstadoIntervencion) elegir("Nuevo estado:", EstadoIntervencion.values());
        if (estado == EstadoIntervencion.FINALIZADA) {
            // las horas se piden al cerrar la intervencion
            double horas = leerDouble("Horas de trabajo (0 si el servicio no las usa):");
            String resultadoHoras = techRepair.registrarHorasDetalle(codigoOrden, codigoDetalle, horas);
            if (resultadoHoras.startsWith("Error")) {
                mostrar(resultadoHoras);
                return;
            }
        }
        mostrar(techRepair.actualizarEstadoDetalle(codigoOrden, codigoDetalle, estado));
    }
    private static String leerTexto(String mensaje) {
        String texto = JOptionPane.showInputDialog(null, mensaje);
        if (texto == null) {
            return "";
        }
        return texto.trim();
    }
    private static boolean esNumero(String texto, boolean permiteDecimal) {
        int puntos = 0;
        int digitos = 0;
        if (texto.isEmpty() || texto.length() > 9) {
            return false;
        }
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (Character.isDigit(c)) {
                digitos++;
            } else if (c == '.' && permiteDecimal) {
                puntos++;
            } else {
                return false;
            }
        }
        return digitos > 0 && puntos <= 1;
    }

    private static int leerEntero(String mensaje) {
        String texto = leerTexto(mensaje);
        if (!esNumero(texto, false)) {
            return -1;
        }
        return Integer.parseInt(texto);
    }

    private static double leerDouble(String mensaje) {
        String texto = leerTexto(mensaje).replace(',', '.');
        if (!esNumero(texto, true)) {
            return -1;
        }
        return Double.parseDouble(texto);
    }

    private static boolean confirmar(String mensaje) {
        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, "TechRepair", JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }


    private static Object elegir(String mensaje, Object[] opciones) {
        return JOptionPane.showInputDialog(null, mensaje, "TechRepair", JOptionPane.QUESTION_MESSAGE,
                null, opciones, opciones[0]);
    }

    private static void mostrar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "TechRepair", JOptionPane.INFORMATION_MESSAGE);
    }
}
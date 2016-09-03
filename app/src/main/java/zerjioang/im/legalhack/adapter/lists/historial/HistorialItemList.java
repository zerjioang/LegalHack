package zerjioang.im.legalhack.adapter.lists.historial;

/**
 * Clase que contiene SOLO los datos relacionados con una sesion de chat
 */
public class HistorialItemList {

    private final String nombre;
    private final String userName;

    public HistorialItemList(String nombre, String userName) {
        this.nombre = nombre;
        this.userName = userName;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUserName() {
        return userName;
    }
}

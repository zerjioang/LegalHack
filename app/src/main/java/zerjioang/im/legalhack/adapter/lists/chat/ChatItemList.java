package zerjioang.im.legalhack.adapter.lists.chat;

import android.graphics.drawable.Drawable;

/**
 * Clase que contiene SOLO los datos relacionados con una sesion de chat
 */
public class ChatItemList {

    private final Drawable o;
    private final String nombre;
    private final String ultimoMensaje;
    private final String fechaStr;
    private String chatUserId;

    public ChatItemList(Drawable o, String nombre, String ultimoMensaje, String fechaStr, String chatUserId) {
        this.o = o;
        this.nombre = nombre;
        this.ultimoMensaje = ultimoMensaje;
        this.fechaStr = fechaStr;
        this.chatUserId = chatUserId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public String getFechaStr() {
        return fechaStr;
    }

    public String getChatUserId() {
        return chatUserId;
    }

    public void setChatUserId(String chatUserId) {
        this.chatUserId = chatUserId;
    }
}

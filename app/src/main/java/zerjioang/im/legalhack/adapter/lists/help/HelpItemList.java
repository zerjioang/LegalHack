package zerjioang.im.legalhack.adapter.lists.help;

import java.io.UnsupportedEncodingException;

/**
 * Clase que contiene SOLO los datos relacionados con una pregunta de faqs
 */
public class HelpItemList {

    private String titulo;
    private String respuesta;

    public HelpItemList(String titulo, String respuesta) {
        try {
            this.respuesta = new String(respuesta.getBytes("ISO-8859-1"), "UTF-8");
            this.titulo = new String(titulo.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            this.titulo = titulo;
            this.respuesta = respuesta;
        }
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}

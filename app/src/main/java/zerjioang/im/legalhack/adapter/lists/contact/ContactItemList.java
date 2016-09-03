package zerjioang.im.legalhack.adapter.lists.contact;

import android.net.Uri;

/**
 * Clase que contiene SOLO los datos relacionados con una pregunta de faqs
 */
public class ContactItemList {


    private final String id;
    private final String name;
    private final String phone;
    private Uri image;

    public ContactItemList(String id, String name, String phone, Uri image) {

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getFixPhone() {
        String numero = getPhone().replace(" ", "");
        if (numero.startsWith("+"))
            numero = numero.substring(3);
        return numero;
    }
}

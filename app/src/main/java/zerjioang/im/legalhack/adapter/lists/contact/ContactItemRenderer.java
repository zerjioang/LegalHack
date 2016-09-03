package zerjioang.im.legalhack.adapter.lists.contact;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.ui.RoundedAvatarDrawable;

/**
 * Clase que contiene SOLO los elementos graficos necesarios para pintar en
 * pantalla los datos de una pregunta de ayuda. Obviamente usara como fuente de datos el objeto HelpItemList
 */
public class ContactItemRenderer extends LinearLayout {

    private final Context context;
    private ContactItemList contact;

    private TextView textViewNombreContacto, textViewEstado;
    private ImageView imageViewContactImage;

    public ContactItemRenderer(Context context, ContactItemList contact) {
        super(context);
        this.context = context;
        this.contact = contact;
        init();
    }

    public ContactItemList getContact() {
        return contact;
    }

    /**
     * Inicializa y setea los datos de la vista
     */
    private void init() {
        inflate();
        findElements();
        rellenarCampos();
    }

    /**
     * hace un inflate de los elementos
     */
    private void inflate() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.list_contact_item, this, true);
    }

    /**
     * carga en los elementos de la interfaz los datos correspondientes
     */
    public void rellenarCampos() {

        //rellenar el nombre del elemento de forma dinamica a partir de los datos
        textViewNombreContacto.setText(getContact().getName());
        textViewEstado.setText(String.valueOf(getContact().getPhone()));

        //settear la imagen del usuario
        Uri u = getContact().getImage();
        if (u != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), u);
                if (bitmap != null) {
                    //poner imagen redondeada
                    //poner la imagen redondeada
                    RoundedAvatarDrawable rounded = new RoundedAvatarDrawable(bitmap);
                    imageViewContactImage.setImageDrawable(rounded);
                } else
                    setDefaultRoundedImage();
            } catch (Exception e) {
                setDefaultRoundedImage();
            }
        } else {
            setDefaultRoundedImage();
        }
    }

    private void setDefaultRoundedImage() {
        //poner la imagen redondeada
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
        RoundedAvatarDrawable rounded = new RoundedAvatarDrawable(bm);
        imageViewContactImage.setImageDrawable(rounded);
    }

    /**
     * busca y asocia los elementos de la activity a los objetos en java
     */
    private void findElements() {
        //se asocian los elementos de la interfaz a los objetos de la clase
        textViewNombreContacto = (TextView) findViewById(R.id.textViewNombreContacto);
        textViewEstado = (TextView) findViewById(R.id.textViewEstado);
        imageViewContactImage = (ImageView) findViewById(R.id.imageViewContactImage);
    }
}

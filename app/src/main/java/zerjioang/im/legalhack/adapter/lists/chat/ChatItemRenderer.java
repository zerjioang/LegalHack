package zerjioang.im.legalhack.adapter.lists.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.ui.RoundedAvatarDrawable;

/**
 * Clase que contiene SOLO los elementos graficos necesarios para pintar en
 * pantalla los datos del chat. Obviamente usara como fuente de datos el objeto ChatItemList
 */
public class ChatItemRenderer extends LinearLayout {

    private final Context context;
    private ChatItemList chat;

    private TextView textViewNombre, textViewHora, textViewUltimoComentario;
    private ImageView imageViewAvatar;

    public ChatItemRenderer(Context context, ChatItemList chat) {
        super(context);
        this.context = context;
        this.chat = chat;
        init();
    }

    public ChatItemList getChat() {
        return chat;
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
        li.inflate(R.layout.list_chat_item, this, true);
    }

    /**
     * carga en los elementos de la interfaz los datos correspondientes
     */
    public void rellenarCampos() {

        //rellenar el nombre del elemento de forma dinamica a partir de los datos
        textViewNombre.setText(getChat().getNombre());
        textViewHora.setText(getChat().getFechaStr());
        textViewUltimoComentario.setText(getChat().getUltimoMensaje());

        //poner la imagen redondeada
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.default_avatar);
        RoundedAvatarDrawable rounded = new RoundedAvatarDrawable(bm);
        imageViewAvatar.setImageDrawable(rounded);
    }

    /**
     * busca y asocia los elementos de la activity a los objetos en java
     */
    private void findElements() {
        //se asocian los elementos de la interfaz a los objetos de la clase
        textViewNombre = (TextView) findViewById(R.id.textViewNombreContacto);
        textViewHora = (TextView) findViewById(R.id.textViewHora);
        textViewUltimoComentario = (TextView) findViewById(R.id.textViewUltimoComentario);
        imageViewAvatar = (ImageView) findViewById(R.id.imageViewAvatar);
    }
}

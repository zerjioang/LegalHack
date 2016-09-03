package zerjioang.im.legalhack.adapter.lists.historial;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import zerjioang.im.legalhack.R;

/**
 * Clase que contiene SOLO los elementos graficos necesarios para pintar en
 * pantalla los datos del historial. Obviamente usara como fuente de datos el objeto ChatItemList
 */
public class HistorialItemRenderer extends LinearLayout {

    private final Context context;
    private HistorialItemList historial;

    private TextView textViewNombre, textViewUser, textViewUserName;
    private ImageView imageViewAvatar;

    public HistorialItemRenderer(Context context, HistorialItemList chat) {
        super(context);
        this.context = context;
        this.historial = chat;
        init();
    }

    public HistorialItemList getHistorial() {
        return historial;
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
        li.inflate(R.layout.list_historial_item, this, true);
    }

    /**
     * carga en los elementos de la interfaz los datos correspondientes
     */
    public void rellenarCampos() {

        //rellenar el nombre del elemento de forma dinamica a partir de los datos
        textViewNombre.setText(historial.getNombre());
        textViewUser.setText("User: ");
        textViewUserName.setText(historial.getUserName());
    }

    /**
     * busca y asocia los elementos de la activity a los objetos en java
     */
    private void findElements() {
        //se asocian los elementos de la interfaz a los objetos de la clase
        textViewNombre = (TextView) findViewById(R.id.textViewNombreContacto);
        textViewUser = (TextView) findViewById(R.id.textViewUser);
        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
    }
}

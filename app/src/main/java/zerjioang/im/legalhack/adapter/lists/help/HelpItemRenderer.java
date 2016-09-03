package zerjioang.im.legalhack.adapter.lists.help;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import zerjioang.im.legalhack.R;

/**
 * Clase que contiene SOLO los elementos graficos necesarios para pintar en
 * pantalla los datos de una pregunta de ayuda. Obviamente usara como fuente de datos el objeto HelpItemList
 */
public class HelpItemRenderer extends LinearLayout {

    private final Context context;
    private HelpItemList help;

    private TextView textViewTitulo, textViewRespuesta;

    public HelpItemRenderer(Context context, HelpItemList help) {
        super(context);
        this.context = context;
        this.help = help;
        init();
    }

    public HelpItemList getHelp() {
        return help;
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
        li.inflate(R.layout.list_help_item, this, true);
    }

    /**
     * carga en los elementos de la interfaz los datos correspondientes
     */
    public void rellenarCampos() {

        //rellenar el nombre del elemento de forma dinamica a partir de los datos
        textViewTitulo.setText(getHelp().getTitulo());
        textViewRespuesta.setText(getHelp().getRespuesta());
    }

    /**
     * busca y asocia los elementos de la activity a los objetos en java
     */
    private void findElements() {
        //se asocian los elementos de la interfaz a los objetos de la clase
        textViewTitulo = (TextView) findViewById(R.id.textViewTitulo);
        textViewRespuesta = (TextView) findViewById(R.id.textViewRespuesta);
    }
}

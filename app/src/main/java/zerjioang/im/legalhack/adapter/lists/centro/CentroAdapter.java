package zerjioang.im.legalhack.adapter.lists.centro;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergio on 12/5/15.
 */
public class CentroAdapter extends ArrayAdapter<CentroItemList> {

    //region Atributos

    private Context context;
    private List<CentroItemList> listaCentros = new ArrayList();

    public CentroAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    //endregion

    //region Metodos overideados

    @Override
    public int getCount() {
        return listaCentros.size();
    }

    @Override
    public CentroItemList getItem(int position) {
        return listaCentros.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //obtener el espacio de ese elemento de la lista
        CentroItemList centro = listaCentros.get(position);

        //lo devolvemos para que lo renderize en la activity
        CentroItemRenderer render = new CentroItemRenderer(context, centro);

        return render;
    }

    @Override
    public void add(CentroItemList msg) {
        listaCentros.add(msg);
        notifyDataSetChanged();
    }
}
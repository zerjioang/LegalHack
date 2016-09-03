package zerjioang.im.legalhack.adapter.lists.historial;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import zerjioang.im.legalhack.dao.Historial;

/**
 * Created by sergio on 12/5/15.
 */
public class HistorialAdapter extends BaseAdapter {

    //region Atributos

    private Activity activity;
    private List<HistorialItemList> listaChats;

    //endregion

    //region constructor

    public HistorialAdapter(Activity activity, List<Historial> listaChats) {
        this.activity = activity;
        this.listaChats = new ArrayList<>();
        for (Historial h : listaChats) {
            HistorialItemList hIL = new HistorialItemList(h.getVISIBLENAME(), h.getUSER());
            this.listaChats.add(hIL);
        }
    }

    //endregion

    //region Metodos overideados

    @Override
    public int getCount() {
        return listaChats.size();
    }

    @Override
    public Object getItem(int position) {
        return listaChats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //obtener el espacio de ese elemento de la lista
        HistorialItemList chat = listaChats.get(position);

        //lo devolvemos para que lo renderize en la activity
        HistorialItemRenderer render = new HistorialItemRenderer(activity, chat);
        return render;
    }
}
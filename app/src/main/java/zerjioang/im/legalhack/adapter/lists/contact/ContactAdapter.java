package zerjioang.im.legalhack.adapter.lists.contact;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by sergio on 12/5/15.
 */
public class ContactAdapter extends BaseAdapter {

    //region Atributos

    private Activity activity;
    private List<ContactItemList> listaContactos;

    //endregion

    //region constructor

    public ContactAdapter(Activity activity, List<ContactItemList> listaContactos) {
        this.activity = activity;
        this.listaContactos = listaContactos;
    }

    //endregion

    //region Metodos overideados

    @Override
    public int getCount() {
        return listaContactos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaContactos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //obtener el espacio de ese elemento de la lista
        ContactItemList contact = listaContactos.get(position);

        //lo devolvemos para que lo renderize en la activity
        ContactItemRenderer render = new ContactItemRenderer(activity, contact);
        return render;
    }
}
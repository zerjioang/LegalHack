package zerjioang.im.legalhack.adapter.lists.help;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by sergio on 12/5/15.
 */
public class HelpAdapter extends BaseAdapter {

    //region Atributos

    private Activity activity;
    private List<HelpItemList> listaHelp;

    //endregion

    //region constructor

    public HelpAdapter(Activity activity, List<HelpItemList> listaHelp) {
        this.activity = activity;
        this.listaHelp = listaHelp;
    }

    //endregion

    //region Metodos overideados

    @Override
    public int getCount() {
        return listaHelp.size();
    }

    @Override
    public Object getItem(int position) {
        return listaHelp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //obtener el espacio de ese elemento de la lista
        HelpItemList chat = listaHelp.get(position);

        //lo devolvemos para que lo renderize en la activity
        HelpItemRenderer render = new HelpItemRenderer(activity, chat);
        return render;
    }
}
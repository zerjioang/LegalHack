package zerjioang.im.legalhack.adapter.lists.chat;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by sergio on 12/5/15.
 */
public class ChatAdapter extends BaseAdapter {

    //region Atributos

    private Activity activity;
    private List<ChatItemList> listaChats;

    //endregion

    //region constructor

    public ChatAdapter(Activity activity, List<ChatItemList> listaChats) {
        this.activity = activity;
        this.listaChats = listaChats;
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
        ChatItemList chat = listaChats.get(position);

        //lo devolvemos para que lo renderize en la activity
        ChatItemRenderer render = new ChatItemRenderer(activity, chat);
        return render;
    }
}
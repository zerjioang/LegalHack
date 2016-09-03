package zerjioang.im.legalhack.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.chat.ChatAdapter;
import zerjioang.im.legalhack.adapter.lists.chat.ChatItemList;
import zerjioang.im.legalhack.dao.CHATS_INICIADOS;
import zerjioang.im.legalhack.dao.DataManager;
import zerjioang.im.legalhack.ui.ConversationActivity;
import zerjioang.im.legalhack.util.Cache;

public class ListaChatsFragment extends Fragment {

    //datos de la ventana inicial
    private ListView listViewChats;
    private TextView textViewSinChats;
    private View view;

    public ListaChatsFragment() {
        // Required empty public constructor
    }

    public static ListaChatsFragment newInstance() {
        ListaChatsFragment fragment = new ListaChatsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inicio, container, false);

        listViewChats = (ListView) view.findViewById(R.id.listViewChats);
        textViewSinChats = (TextView) view.findViewById(R.id.textViewSinChats);

        listViewChats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //open chat details
                Intent t = new Intent(getActivity(), ConversationActivity.class);
                ChatItemList item = (ChatItemList) listViewChats.getAdapter().getItem(position);
                //pasarle a la activity el nombre del chat
                t.putExtra("nombreChat", item.getNombre());
                String user = item.getChatUserId();
                t.putExtra("destinationUser", user);
                getActivity().startActivity(t);
            }
        });

        initLista();
        return view;
    }

    private void initLista() {
        List<ChatItemList> lista = obtenerListadoChatsIniciados();
        if (!lista.isEmpty()) {
            //lista.add(new ChatItemList(null, "Chat de prueba", "exacto", "19:44", "ruben") );
            ChatAdapter ca = new ChatAdapter(getActivity(), lista);
            listViewChats.setAdapter(ca);
            textViewSinChats.setVisibility(View.GONE);
            listViewChats.setVisibility(View.VISIBLE);
        } else {
            textViewSinChats.setVisibility(View.VISIBLE);
            listViewChats.setVisibility(View.GONE);
        }
    }

    private List<ChatItemList> obtenerListadoChatsIniciados() {
        ArrayList<ChatItemList> listaFinal = Cache.listaChats;
        if (listaFinal == null || listaFinal.isEmpty()) {
            ArrayList<CHATS_INICIADOS> chats = (ArrayList<CHATS_INICIADOS>) DataManager.getInstance().sacarChats();
            listaFinal = new ArrayList<>();
            for (CHATS_INICIADOS chat : chats) {
                listaFinal.add(new ChatItemList(null, chat.getTITULO(), chat.getULTIMOSMS(), chat.getULTIMAHORA(), chat.getUSUARIODESTINO()));
            }
            Cache.listaChats = listaFinal;
        }
        return listaFinal;
    }

}

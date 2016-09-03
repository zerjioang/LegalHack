package zerjioang.im.legalhack.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.contact.ContactAdapter;
import zerjioang.im.legalhack.adapter.lists.contact.ContactItemList;
import zerjioang.im.legalhack.util.Utilities;

public class ListaContactosFragment extends Fragment {

    //datos de la ventana inicial
    private ListView listViewContactos;
    private View view;


    public ListaContactosFragment() {
        // Required empty public constructor
    }

    public static ListaContactosFragment newInstance() {
        ListaContactosFragment fragment = new ListaContactosFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contactos, container, false);

        listViewContactos = (ListView) view.findViewById(R.id.listViewContactos);

        initList();

        return view;
    }

    private void initList() {
        List<ContactItemList> lista = Utilities.obtenerContactos(getActivity());
        listViewContactos.setAdapter(new ContactAdapter(getActivity(), lista));
    }
}

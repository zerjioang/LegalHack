package zerjioang.im.legalhack.ui.fragment.legal.sub_fragmentos;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.help.HelpAdapter;
import zerjioang.im.legalhack.adapter.lists.help.HelpItemList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColaboradoresFragment extends Fragment {


    private View view;
    private ListView listViewHelp;

    public ColaboradoresFragment() {
        // Required empty public constructor
    }

    public static ColaboradoresFragment newInstance() {
        ColaboradoresFragment fragment = new ColaboradoresFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);

        listViewHelp = (ListView) view.findViewById(R.id.listViewHelp);

        initLista();
        return view;
    }

    private void initLista() {
        List<HelpItemList> lista = new LinkedList<>();
        lista.add(new HelpItemList(
                "¿Que es Secrets?",
                "Secrets es una app de mensajeria instantanea que permite poner en contacto de manera segura y rapida a personas.\nHa sido especialmente diseñada para relaciones abogado-cliente."));
        lista.add(new HelpItemList(
                "¿Puedo usarla si no soy abogado?",
                "Obviamente sí, pero esta creada especificamente para poner en contacto a clientes con abogados. Además tienes que tener en cuenta que Secrets no es sólo una aplicacion para movíl si no que tiene una infraestructura de red detrás que la soporta"));
        lista.add(new HelpItemList(
                "¿Es totalmente gratuita?",
                "Sí, pero tienes estar autorizado a usarla"));
        HelpAdapter ca = new HelpAdapter(getActivity(), lista);
        listViewHelp.setAdapter(ca);
    }


}

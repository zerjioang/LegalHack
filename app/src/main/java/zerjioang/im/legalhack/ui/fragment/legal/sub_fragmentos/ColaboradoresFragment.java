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
                "�Que es Secrets?",
                "Secrets es una app de mensajeria instantanea que permite poner en contacto de manera segura y rapida a personas.\nHa sido especialmente dise�ada para relaciones abogado-cliente."));
        lista.add(new HelpItemList(
                "�Puedo usarla si no soy abogado?",
                "Obviamente s�, pero esta creada especificamente para poner en contacto a clientes con abogados. Adem�s tienes que tener en cuenta que Secrets no es s�lo una aplicacion para mov�l si no que tiene una infraestructura de red detr�s que la soporta"));
        lista.add(new HelpItemList(
                "�Es totalmente gratuita?",
                "S�, pero tienes estar autorizado a usarla"));
        HelpAdapter ca = new HelpAdapter(getActivity(), lista);
        listViewHelp.setAdapter(ca);
    }


}

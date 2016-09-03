package zerjioang.im.legalhack.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.help.HelpAdapter;
import zerjioang.im.legalhack.adapter.lists.help.HelpItemList;

public class HelpFragment extends Fragment {

    private View view;
    private ListView listViewHelp;

    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance() {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_help, container, false);

        listViewHelp = (ListView) view.findViewById(R.id.listViewHelp);

        //TODO lista chats test
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
        lista.add(new HelpItemList(
                "¿Cómo funciona?",
                "Su funcionamiento es muy similar al de aplicaciones como Whatsapp o Telegram. Solo tienes que añadir el contacto con el que deseas comunicarte y listo!"));
        lista.add(new HelpItemList(
                "¿Puedo enviar contenido confidencial a traves de Secrets?",
                "Secrets utiliza un medio de comunicacion cifrado de forma que sólo el emisor y el receptor sean capaces de interpretar los datos"));
        lista.add(new HelpItemList(
                "¿Cómo surgió?",
                "Se creó como propuesta para resolver uno de los retos propuestos en el LegalHackaton 2015 llevado a cabo en Bilbao."));
        lista.add(new HelpItemList(
                "¿Quien la ha desarrollado?",
                "El equipo detrás de Secrets es:\n\nSergio Anguita\nSergio Ausín\nSofia Barrena\nRubén García"));
        HelpAdapter ca = new HelpAdapter(getActivity(), lista);
        listViewHelp.setAdapter(ca);
    }

}

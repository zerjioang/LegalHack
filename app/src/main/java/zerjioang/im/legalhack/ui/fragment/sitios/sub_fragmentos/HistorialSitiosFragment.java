package zerjioang.im.legalhack.ui.fragment.sitios.sub_fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.historial.HistorialAdapter;
import zerjioang.im.legalhack.dao.DataManager;
import zerjioang.im.legalhack.dao.Historial;
import zerjioang.im.legalhack.util.Cache;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistorialSitiosFragment extends Fragment {

    private ListView listiviewHistorial;
    private TextView textViewNoHistorial;
    private LinearLayout layout;
    private View view;


    public HistorialSitiosFragment() {
        // Required empty public constructor
    }

    public static HistorialSitiosFragment newInstance() {
        HistorialSitiosFragment fragment = new HistorialSitiosFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sitios_historial, container, false);

        listiviewHistorial = (ListView) view.findViewById(R.id.listViewHistorial);
        textViewNoHistorial = (TextView) view.findViewById(R.id.textViewNoHistorial);
        layout = (LinearLayout) view.findViewById(R.id.linearLayoutHistorial);

        List<Historial> lista = obtenerLista();
        if (lista != null && !lista.isEmpty()) {
            HistorialAdapter adapter = new HistorialAdapter(getActivity(), lista);
            listiviewHistorial.setAdapter(adapter);
            layout.setVisibility(View.VISIBLE);
            listiviewHistorial.setVisibility(View.VISIBLE);
            textViewNoHistorial.setVisibility(View.GONE);
        } else {
            layout.setVisibility(View.GONE);
            textViewNoHistorial.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private List<Historial> obtenerLista() {
        List<Historial> lista = Cache.listaHistorial;
        if (lista == null || lista.isEmpty()) {
            //obtener la lista
            lista = DataManager.getInstance().sacarHistorial();
            Cache.listaHistorial = lista;
        }
        return lista;
    }


}

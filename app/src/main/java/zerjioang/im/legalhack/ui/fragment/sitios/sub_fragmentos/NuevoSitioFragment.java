package zerjioang.im.legalhack.ui.fragment.sitios.sub_fragmentos;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.centro.CentroAdapter;
import zerjioang.im.legalhack.adapter.lists.centro.CentroItemList;
import zerjioang.im.legalhack.dao.DataManager;
import zerjioang.im.legalhack.dao.Historial;
import zerjioang.im.legalhack.exceptions.UserIsNotConnectedException;
import zerjioang.im.legalhack.modules.SettingsManagerModule;
import zerjioang.im.legalhack.net.DefaultInternetRequestCallback;
import zerjioang.im.legalhack.net.server.ServerManager;
import zerjioang.im.legalhack.ui.navbar.NavDrawerActivity;
import zerjioang.im.legalhack.util.Utilities;

/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoSitioFragment extends Fragment {


    private View view;
    private ListView listViewCentros;
    private CentroAdapter adapter;
    private LinearLayout layoutAccess;
    private TextView textViewMensajeEstado;

    private EditText editTextUsername, editTextPassword;
    private Button buttonAcceder;
    private ListView listViewCentroDatos;
    private CentroItemList centro;

    public NuevoSitioFragment() {
        // Required empty public constructor
    }

    public static NuevoSitioFragment newInstance() {
        NuevoSitioFragment fragment = new NuevoSitioFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sitios_nuevo, container, false);
        layoutAccess = (LinearLayout) view.findViewById(R.id.layoutAccess);
        textViewMensajeEstado = (TextView) view.findViewById(R.id.textViewMensajeEstado);

        editTextUsername = (EditText) view.findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) view.findViewById(R.id.editTextPassword);
        buttonAcceder = (Button) view.findViewById(R.id.buttonAcceder);
        listViewCentroDatos = (ListView) view.findViewById(R.id.listViewCentroDatos);

        findElements();
        obtenerListado();
        layoutAccess.setVisibility(View.GONE);
        textViewMensajeEstado.setVisibility(View.VISIBLE);
        textViewMensajeEstado.setText("Descargando datos...");
        eventos();
        return view;
    }

    private void eventos() {
        listViewCentros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                centro = adapter.getItem(position);
                if (centro.getLoginEnabled()) {
                    //mostrar layout del login
                    layoutAccess.setVisibility(View.VISIBLE);
                }
            }
        });

        buttonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ejecutar login
                final String user = editTextUsername.getText().toString();
                final String pass = editTextPassword.getText().toString();
                if (centro != null) {
                    final String serverIp = centro.getIp();
                    Utilities.isServerAlive(getActivity(), new DefaultInternetRequestCallback() {

                        private String error;

                        @Override
                        public void execute(boolean alive) {
                            try {
                                try {
                                    ServerManager.getInstance().connect(serverIp);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                ServerManager.getInstance().login(getActivity(), user, pass);
                                guardar(user, pass, serverIp, centro.getServername());
                                //mostrar la pesta�a de chats
                                Activity nav = getActivity();
                                if (nav instanceof NavDrawerActivity) {
                                    NavDrawerActivity navact = (NavDrawerActivity) nav;
                                    navact.cambiarFragmento(NavDrawerActivity.CABECERA);
                                    DataManager.getInstance().guardarHistorial(new Historial(null, serverIp, centro.getPort(), centro.getServername(), centro.getVisibleName(), centro.getLoginEnabled(), user, pass));
                                }
                            } catch (XMPPException | UserIsNotConnectedException e) {
                                e.printStackTrace();
                                error = e.getLocalizedMessage();
                            }
                        }
                    }, DefaultInternetRequestCallback.NETWORK_TASK);
                }
            }
        });

    }

    private void guardar(String user, String pass, String serverIp, String servername) {
        SettingsManagerModule settings = SettingsManagerModule.getInstance(getActivity());
        settings.setUsuario(user);
        settings.setPass(pass);
        settings.setIp(serverIp);
        settings.setServerName(servername);
    }

    private void obtenerListado() {
        ParseQuery<CentroItemList> query = ParseQuery.getQuery("Centro");
        query.findInBackground(new FindCallback<CentroItemList>() {
            public void done(List<CentroItemList> listaCentros, ParseException e) {
                if (e == null) {
                    Log.d("listaCentros", "Retrieved " + listaCentros.size() + " centro");
                } else {
                    Log.d("listaCentros", "Error: " + e.getMessage());
                }
                if (listaCentros != null && !listaCentros.isEmpty()) {
                    for (CentroItemList centro : listaCentros) {
                        adapter.add(centro);
                    }
                    textViewMensajeEstado.setVisibility(View.GONE);
                } else {
                    textViewMensajeEstado.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "No hay centros disponibles", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void findElements() {
        listViewCentros = (ListView) view.findViewById(R.id.listViewCentroDatos);
        adapter = new CentroAdapter(getActivity(), R.layout.list_centro_item);
        listViewCentros.setAdapter(adapter);
    }

}

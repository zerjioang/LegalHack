package zerjioang.im.legalhack.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.parse.Parse;
import com.parse.ParseObject;

import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.centro.CentroItemList;
import zerjioang.im.legalhack.adapter.lists.contact.ContactAdapter;
import zerjioang.im.legalhack.adapter.lists.contact.ContactItemList;
import zerjioang.im.legalhack.dao.CHATS_INICIADOS;
import zerjioang.im.legalhack.dao.DataManager;
import zerjioang.im.legalhack.modules.SettingsManagerModule;
import zerjioang.im.legalhack.net.server.ServerManager;
import zerjioang.im.legalhack.ui.navbar.NavDrawerActivity;
import zerjioang.im.legalhack.ui.settings.SettingsActivity;
import zerjioang.im.legalhack.util.Utilities;

/**
 * Created by Sergio Anguita on 02/03/2015. Clase padre de la mayoria de activities de la app
 */
public abstract class BaseActivity extends AppCompatActivity {

    //Constantes
    protected static final String TAG = "BaseActivity";
    private static final String ACTIONBAR_COLOR = "#FF190C2C";
    private static final boolean IS_DEBUGGING = true;
    protected static SettingsManagerModule settings;
    protected static ServerManager server = ServerManager.getInstance();
    private static boolean invoked;
    //protected static GPSLocationManager gpsManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //comprobar si se está ejecutando en un emulador
        boolean isEmulated = SettingsManagerModule.getInstance(this).isEmulatorDetected();
        //TODO: eliminar IS_DEBUGGING en produccion
        if (isEmulated && !IS_DEBUGGING) {
            //cerrar la activity
            finish();
            return;
        }

        if (!invoked) {
            //registrar las clases
            ParseObject.registerSubclass(CentroItemList.class);
            //inicializar parse
            // Enable Local Datastore.
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "FgsWbVrxCLLEaaOhbtsIQrqG3MzUPoiJgTTo5cX7", "EEySuv4wcSpXXRh1aTlr19Uvwu6gA5rh1Gw9LCsI");
            //inicializar los valores una sola vez
            settings = SettingsManagerModule.getInstance(this);
            //crear el handler que manejara la informacion del gps
            //gpsManager = new GPSLocationManager(this);
            //gpsManager.init(this);
            DataManager.setActivityCaller(this);
            invoked = true;
        }

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(ACTIONBAR_COLOR)));
            bar.setHomeButtonEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        //comrpobar si la app esta verificada
        super.onResume();
        /*if (!SettingsManagerModule.getInstance(this).isVerified()) {
            //verificar
            Intent t = new Intent(this, RegisterDeviceActivity.class);
            this.startActivity(t);
            //cerrar esta actividad
            this.finish();
        } else {
            gpsManager.onResume();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //mostrar ventana de ajustes
            Intent t = new Intent(getActivity(), SettingsActivity.class);
            this.startActivity(t);
            return true;
        } else if (id == R.id.action_new_chat) {
            //mostrar ventana de nuevo chat
            nuevoChat();
            return true;
        } else if (id == R.id.action_logout) {
            //boton de cerrar sesion
            String title = "Cerrar sesión";
            String msg = "¿Desea realmente cerrar la sesión actual? Por seguridad sus datos permanecerán aislados en el dispositivo";
            MaterialDialog.ButtonCallback callback = new MaterialDialog.ButtonCallback() {
                @Override
                public void onNegative(MaterialDialog dialog) {
                    super.onNegative(dialog);
                    //cerrar pop up sin mas
                    dialog.dismiss();
                }

                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    if (settings.getUsuario() != null) {
                        server.disconnect();
                        settings.setUsuario(null);
                        settings.setPass(null);
                        getActivity().finish();
                    }
                }
            };
            Utilities.showMessage(getActivity(), title, msg, callback, Utilities.DIALOG_OK_CANCEL);
        } else if (id == android.R.id.home) {
            if (!(getActivity() instanceof NavDrawerActivity)) {
                //acction de atras de la barra superior
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    protected void nuevoChat() {
        server = ServerManager.getInstance();
        if (server.isConnected()) {
            if (server.isLogged()) {
                //crear nuevo chat con el usuario
                mostrarMensajeSeleecionUsuario();
            } else {
                MaterialDialog.ButtonCallback callback = new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        dialog.dismiss();
                        BaseActivity activity = getActivity();
                        if (activity instanceof NavDrawerActivity) {
                            //cargar fragmento de sitios
                            NavDrawerActivity nav = (NavDrawerActivity) activity;
                            nav.cambiarFragmento(NavDrawerActivity.MIS_SITIOS);
                        }
                    }
                };
                Utilities.showMessage(this, "Conectese", "Por favor, inicie sesión", callback, Utilities.DIALOG_OK);
            }
        } else {
            MaterialDialog.ButtonCallback callback = new MaterialDialog.ButtonCallback() {
                @Override
                public void onPositive(MaterialDialog dialog) {
                    super.onPositive(dialog);
                    dialog.dismiss();
                    BaseActivity activity = getActivity();
                    if (activity instanceof NavDrawerActivity) {
                        //cargar fragmento de sitios
                        NavDrawerActivity nav = (NavDrawerActivity) activity;
                        nav.cambiarFragmento(NavDrawerActivity.MIS_SITIOS);
                    }
                }
            };
            Utilities.showMessage(this, "Conectese", "Por favor, conectese a su sitio de confianza antes de empezar una conversación", callback, Utilities.DIALOG_OK);
        }
    }

    protected void mostrarMensajeSeleecionUsuario() {
        boolean wrapInScrollView = false;
        MaterialDialog.Builder msg = new MaterialDialog.Builder(this)
                .title(R.string.select_contacto)
                .customView(R.layout.fragment_contactos, wrapInScrollView)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        //iniciar chat con ese usuario

                    }

                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.dismiss();
                    }
                });

        final MaterialDialog dialog = msg.build();
        View view = dialog.getCustomView();
        if (view != null) {
            final ListView listaContactos = (ListView) view.findViewById(R.id.listViewContactos);
            List<ContactItemList> lista = Utilities.obtenerContactos(getActivity());
            listaContactos.setAdapter(new ContactAdapter(getActivity(), lista));
            //añadir listener
            listaContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ContactItemList item = (ContactItemList) listaContactos.getAdapter().getItem(position);
                    //crear el chat
                    Intent t = new Intent(getActivity(), ConversationActivity.class);
                    t.putExtra("nombreChat", item.getName());
                    t.putExtra("destinationUser", item.getFixPhone());
                    getActivity().startActivity(t);
                    dialog.dismiss();
                    zerjioang.im.legalhack.dao.DataManager.getInstance().guardarChat(new CHATS_INICIADOS(null, "", item.getName(), "", "", item.getFixPhone()));
                }
            });
            dialog.show();
        }
    }

    public void resetActivityView() {
        Intent t = new Intent(getActivity(), NavDrawerActivity.class);
        startActivity(t);
        //cerrar esta
        this.finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //gpsManager.onPause();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        //definir la animacion al abrir una nueva activity
        this.overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
    }

    @Override
    public void finish() {
        super.finish();
        //definir la animacion al cerrar una nueva activity
        this.overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public BaseActivity getActivity() {
        return this;
    }

    public abstract void findElements();

    public abstract void eventos();
}

package zerjioang.im.legalhack.ui.navbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;

import org.jivesoftware.smack.XMPPException;

import java.io.IOException;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.exceptions.UserIsNotConnectedException;
import zerjioang.im.legalhack.net.DefaultInternetRequestCallback;
import zerjioang.im.legalhack.ui.BaseActivity;
import zerjioang.im.legalhack.ui.fragment.HelpFragment;
import zerjioang.im.legalhack.ui.fragment.ListaChatsFragment;
import zerjioang.im.legalhack.ui.fragment.ListaContactosFragment;
import zerjioang.im.legalhack.ui.fragment.legal.LegalFragment;
import zerjioang.im.legalhack.ui.fragment.sitios.ServidoresFragment;
import zerjioang.im.legalhack.ui.settings.SettingsActivity;
import zerjioang.im.legalhack.util.Utilities;

public class NavDrawerActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final int CABECERA = 0;
    public static final int CHAT_PRIVADO = 1;
    public static final int LEGAL = 2;
    public static final int CONTACTOS = 3;
    public static final int MIS_SITIOS = 4;
    public static final int AJUSTES = 5;
    public static final int AYUDA = 6;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private int fragmentoActual;
    private String barLastTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);
        findElements();

        if (fragmentoActual >= 0) {
            cargarFragmento(fragmentoActual);
        } else {
            cargarFragmento(0);
        }
        //settear el titulo de la ventana en funcion de sis esta conectado a intenet o no
        barLastTitle = "Secrets";
        //tarea asincrona
        /*Utilities.isServerAlive(this, new DefaultInternetRequestCallback() {
            @Override
            public void execute(boolean alive) {
                ActionBar bar = getSupportActionBar();
                if (bar != null) {
                    if (alive)
                        barLastTitle = "Secrets";
                    else
                        barLastTitle = "Esperando red...";
                    bar.setTitle(barLastTitle);
                }
            }
        }, DefaultInternetRequestCallback.GUI_TASK);*/

        Utilities.isServerAlive(this, new DefaultInternetRequestCallback() {
            @Override
            public void execute(boolean alive) {
                String lastServer = settings.getIp();
                String lastUser = settings.getUsuario();
                String lastPass = settings.getPass();
                if (lastServer != null && lastPass != null && lastUser != null) {
                    //iniciar sesion automaticamente en el ultimo servidor usado
                    try {
                        server.connect(lastServer);
                        server.login(getActivity(), lastUser, lastPass);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (XMPPException e) {
                        e.printStackTrace();
                    } catch (UserIsNotConnectedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, DefaultInternetRequestCallback.NETWORK_TASK);
    }

    private void cargarFragmento(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        Fragment fragment = null;

        switch (position) {
            default:
                fragment = null;
                break;
            case CABECERA:
                //imagen de la cabecera
                fragment = ListaChatsFragment.newInstance();
                break;
            case CHAT_PRIVADO:
                //mostrar opciones de nuevo chat
                nuevoChat();
                break;
            case LEGAL:
                fragment = LegalFragment.newInstance();
                break;
            case CONTACTOS:
                fragment = ListaContactosFragment.newInstance();
                break;
            case MIS_SITIOS:
                fragment = ServidoresFragment.newInstance();
                break;
            case AJUSTES:
                Intent t = new Intent(getActivity(), SettingsActivity.class);
                getActivity().startActivity(t);
                break;
            case AYUDA:
                fragment = HelpFragment.newInstance();
                break;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    public void findElements() {
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void eventos() {

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        //actualizar el fragmento actual
        cargarFragmento(position);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(barLastTitle);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mNavigationDrawerFragment != null) {
            if (!mNavigationDrawerFragment.isDrawerOpen()) {
                // Only show items in the action bar relevant to this screen
                // if the drawer is not showing. Otherwise, let the drawer
                // decide what to show in the action bar.
                getMenuInflater().inflate(R.menu.nav_drawer, menu);
                restoreActionBar();
                return true;
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void cambiarFragmento(int posFragmento) {
        this.cargarFragmento(posFragmento);
    }
}

package zerjioang.im.legalhack.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import zerjioang.im.legalhack.util.Utilities;

/**
 * Created by sergio on 02/03/2015.
 */
public class SettingsManagerModule {

    //claves del fichero shared-preferences
    private static final String IS_EMULATOR_RUNNING = "emu";
    private static final String VERIFICADA = "ver";

    private static final String USER = "user";
    private static final String PASS = "pass";
    private static final String IP = "ip";
    private static final String PORT = "port";
    private static final String SERVERNAME = "servername";
    private static final String VISIBLENAME = "visiblename";


    private static SettingsManagerModule smm;
    private Context context;
    private SharedPreferences prefs;

    //for speeding up values
    private Boolean emulatorDetected;
    private boolean modoSoloLectura;

    private SettingsManagerModule(Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SettingsManagerModule getInstance(Context context) {
        if (smm == null) {
            smm = new SettingsManagerModule(context);
        }
        return smm;
    }

    //metodos de la clase;

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public boolean isEmulatorDetected() {
        if (emulatorDetected == null) {
            emulatorDetected = Utilities.detectEmulatorPresence(context);
            setEmulatorDetected(emulatorDetected);
        }
        return emulatorDetected;
    }

    public void setEmulatorDetected(boolean emulatorDetected) {
        this.emulatorDetected = emulatorDetected;
        prefs.edit().putBoolean(IS_EMULATOR_RUNNING, emulatorDetected).apply();
    }

    public boolean isModoSoloLectura() {
        return modoSoloLectura;
    }

    public void setModoSoloLectura(boolean modoSoloLectura) {
        this.modoSoloLectura = modoSoloLectura;
    }

    public boolean isVerified() {
        return prefs.getBoolean(VERIFICADA, false);
    }

    public void setVerified(boolean verified) {
        prefs.edit().putBoolean(VERIFICADA, verified).apply();
    }

    public String getUsuario() {
        return prefs.getString(USER, null);
    }

    public void setUsuario(String usuario) {
        prefs.edit().putString(USER, usuario).apply();
    }

    public String getPass() {
        return prefs.getString(PASS, null);
    }

    public void setPass(String pass) {
        prefs.edit().putString(PASS, pass).apply();
    }

    public String getIp() {
        return prefs.getString(IP, null);
    }

    public void setIp(String ip) {
        prefs.edit().putString(IP, ip).apply();
    }

    public String getPuerto() {
        return prefs.getString(PORT, null);
    }

    public void setPuerto(String puerto) {
        prefs.edit().putString(PORT, puerto).apply();
    }

    public String getServerName() {
        return prefs.getString(SERVERNAME, null);
    }

    public void setServerName(String serverName) {
        prefs.edit().putString(SERVERNAME, serverName).apply();
    }

    public String getVisibleName() {
        return prefs.getString(VISIBLENAME, null);
    }

    public void setVisibleName(String visibleName) {
        prefs.edit().putString(VISIBLENAME, visibleName).apply();
    }
}

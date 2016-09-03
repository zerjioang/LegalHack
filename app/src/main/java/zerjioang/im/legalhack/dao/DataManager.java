package zerjioang.im.legalhack.dao;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

import org.jivesoftware.smack.Chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Fiser on 16/5/15.
 */
public class DataManager {
    private static Activity activityCaller;
    private static DataManager manager = null;
    private DaoMaster.DevOpenHelper helper;
    private SQLiteDatabase db;
    private DaoSession daoSession;
    private String notifName;
    private String destinationUser;

    private DataManager() {
        helper = new DaoMaster.DevOpenHelper(activityCaller, "storage", null);
        db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DataManager getInstance() {
        if (manager == null) {
            manager = new DataManager();
        }
        return manager;
    }

    public static void setActivityCaller(Activity activityCaller) {
        DataManager.activityCaller = activityCaller;
    }

    public void guardarChat(CHATS_INICIADOS chat) {
        ArrayList<CHATS_INICIADOS> c = (ArrayList<CHATS_INICIADOS>) daoSession.getCHATS_INICIADOSDao().queryBuilder().where(CHATS_INICIADOSDao.Properties.USUARIODESTINO.eq(chat.getUSUARIODESTINO())).list();
        if(c == null || c.isEmpty())
            daoSession.getCHATS_INICIADOSDao().insert(chat);
        else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateandTime = sdf.format(new Date());
            c.get(0).setULTIMAHORA(currentDateandTime);
        }
    }

    public void updateChat(CHATS_INICIADOS chat) {
        daoSession.getCHATS_INICIADOSDao().update(chat);
    }

    public void deleteChat(CHATS_INICIADOS chat) {
        daoSession.getCHATS_INICIADOSDao().delete(chat);
    }

    public void guardarMensaje(MENSAJES men) {
        daoSession.getMENSAJESDao().insert(men);
    }

    public void updateMensaje(MENSAJES men) {
        daoSession.getMENSAJESDao().update(men);
    }

    public void deleteMensaje(MENSAJES men) {
        daoSession.getMENSAJESDao().delete(men);
    }

    public void guardarHistorial(Historial hist) {
        daoSession.getHistorialDao().insert(hist);
    }

    public void updateHistorial(Historial hist) {
        daoSession.getHistorialDao().update(hist);
    }

    public void deleteHistorial(Historial hist) {
        daoSession.getHistorialDao().delete(hist);
    }

    public void guardarSitios(Sitios sitios) {
        daoSession.getSitiosDao().insert(sitios);
    }

    public void updateSitios(Sitios sitios) {
        daoSession.getSitiosDao().update(sitios);
    }

    public void deleteSitios(Sitios sitios) {
        daoSession.getSitiosDao().delete(sitios);
    }

    public List<Sitios> sacarSitios() {
        return daoSession.getSitiosDao().queryBuilder().list();
    }

    public List<CHATS_INICIADOS> sacarChats() {
        return daoSession.getCHATS_INICIADOSDao().queryBuilder().list();
    }

    public CHATS_INICIADOS obtenerChat(String num) {
        List<CHATS_INICIADOS> lista = daoSession.getCHATS_INICIADOSDao().queryBuilder().where(CHATS_INICIADOSDao.Properties.USUARIODESTINO.eq(num)).list();
        if(lista.isEmpty())
            return new CHATS_INICIADOS();
        else
        return lista.get(0);
    }

    public List<Historial> sacarHistorial() {
        return daoSession.getHistorialDao().queryBuilder().list();
    }

    public List<MENSAJES> sacarMensajes(CHATS_INICIADOS chats_iniciados) {

        return daoSession.getMENSAJESDao().queryBuilder().where(MENSAJESDao.Properties.Idchat.eq(chats_iniciados.getId())).list();
    }
    public List<MENSAJES> sacarMensajes(String destino) {
        long id = 0;
        for(CHATS_INICIADOS temporal: sacarChats())
            if(temporal.getUSUARIODESTINO().equals(destino))
                id = temporal.getId();
        return daoSession.getMENSAJESDao().queryBuilder().where(MENSAJESDao.Properties.Idchat.eq(id)).list();
    }

    public void guardarChat(Chat newChat, String str, String username) {
        CHATS_INICIADOS ch = new CHATS_INICIADOS();
        ch.setTITULO(newChat.getParticipant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        ch.setULTIMAHORA(currentDateandTime);
        ch.setULTIMOSMS(str);
        ch.setUSUARIODESTINO(username);
        guardarChat(ch);
    }

    public void setNotifName(String notifName) {
        this.notifName = notifName;
    }

    public String getNotifName() {
        return notifName;
    }

    public void setDestinationUser(String destinationUser) {
        this.destinationUser = destinationUser;
    }

    public String getDestinationUser() {
        return destinationUser;
    }
}

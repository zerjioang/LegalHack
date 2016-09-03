package zerjioang.im.legalhack.net.server;

import android.content.Context;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.text.SimpleDateFormat;
import java.util.Date;

import zerjioang.im.legalhack.dao.CHATS_INICIADOS;
import zerjioang.im.legalhack.dao.DataManager;
import zerjioang.im.legalhack.dao.MENSAJES;
import zerjioang.im.legalhack.modules.NotificationManagerModule;

/**
 * Created by sergio on 15/5/15.
 */
public class CustomChatListener implements MessageListener {

    private Context context;

    @Override
    public void processMessage(Chat chat, Message message) {
        //mostrar notificacion con el mensaje
        NotificationManagerModule.getInstance(getContext()).mostrarNotificacionSimple(context, chat.getParticipant(), message.getBody(), message.getFrom());

        MENSAJES m = new MENSAJES();
        CHATS_INICIADOS c = DataManager.getInstance().obtenerChat(chat.getParticipant().substring(0, 9));
        m.setIdchat(c.getId());
        m.setMENSAJE(message.getBody());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        m.setDATE(currentDateandTime);
        m.setPropietario(false);
        DataManager.getInstance().guardarMensaje(m);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

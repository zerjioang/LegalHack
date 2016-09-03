package zerjioang.im.legalhack.net.server;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zerjioang.im.legalhack.dao.CHATS_INICIADOS;
import zerjioang.im.legalhack.dao.DataManager;
import zerjioang.im.legalhack.dao.MENSAJES;
import zerjioang.im.legalhack.exceptions.UserIsNotConnectedException;
import zerjioang.im.legalhack.modules.SettingsManagerModule;
import zerjioang.im.legalhack.ui.ConversationActivity;

/**
 * Created by sanguita on 15/05/2015.
 */
public class ServerManager {

    private static final ServerManager sm = new ServerManager();
    private static ServerManager instance;
    private XMPPConnection connection;
    private boolean connected;
    private boolean logged;
    private Context context;

    public ServerManager() {
        connected = false;
        logged = false;
    }

    public static ServerManager getInstance() {
        return sm;
    }

    public void connect(String server) throws IOException, XMPPException {
        if (!connected) {
            try {
                ConnectionConfiguration config = new ConnectionConfiguration(server);
                config.setDebuggerEnabled(true);
                config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
                config.setServiceName(server);
                connection = new XMPPConnection(config);

                log("connecting");
                connection.connect();
                connected = connection.isConnected();
                log("connected: " + connected);
            } catch (XMPPException e) {
                Log.e("ServerManager::connect", e.getLocalizedMessage());
            }
        }

    }

    public void login(Context c, String user, String pass) throws XMPPException, UserIsNotConnectedException {
        this.context = c;
        if (connected) {
            if (!logged) {
                log("Logging...");
                //login anonimo funciona
                connection.login(user, pass);
                //connection.loginAnonymously();
                log("logged in!");
                logged = true;
                createIncomingChatListener();
            }
        } else
            throw new UserIsNotConnectedException("Not connected to server.");
    }

    public void disconnect() {
        // Disconnect from the server
        if (logged)
            connection.disconnect();
    }

    private void log(String str) {
        Log.i("ServerManager", str);
    }

    public boolean sendMessageCurrentChat(final ConversationActivity parent, String str, String username) {
        ChatManager chatmanager = connection.getChatManager();
        SettingsManagerModule settings = SettingsManagerModule.getInstance(parent);
        final Chat newChat = chatmanager.createChat(username + "@" + settings.getServerName(), new MessageListener() {

            public void processMessage(final Chat chat, final Message message) {
                final String msg = message.getBody();
                log("Mensaje recibido " + msg);
                log("from: " + message.getFrom());
                log("to: " + message.getTo());
                log("tipo: " + message.getType().name());
                if (msg != null) {
                    //se ha recibido un mensaje fijo
                    //mostrarlo por pantalla
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            //pintar el mensaje recibido en la pantalla
                            parent.addNewMessage(msg, false);
                            MENSAJES m = new MENSAJES();
                            CHATS_INICIADOS c = DataManager.getInstance().obtenerChat(chat.getParticipant().substring(0, 10));
                            m.setIdchat(c.getId());
                            m.setMENSAJE(msg);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            String currentDateandTime = sdf.format(new Date());
                            m.setDATE(currentDateandTime);
                            m.setPropietario(true);
                            DataManager.getInstance().guardarMensaje(m);
                        }
                    }.execute();
                }
            }

        });

        try {
            if (logged)
                newChat.sendMessage(str);
            return true;
        } catch (XMPPException e) {
            log("Error Delivering block");
        }
        return false;
    }

    public void createIncomingChatListener() {
        Log.i("ServerManager::createIncomingChatListener", "creating incoming messages listener...");
        if (connected) {
            ChatManager manager = connection.getChatManager();
            manager.addChatListener(
                    new ChatManagerListener() {
                        @Override
                        public void chatCreated(Chat chat, boolean createdLocally) {
                            if (!createdLocally) {
                                CustomChatListener listener = new CustomChatListener();
                                listener.setContext(context);
                                chat.addMessageListener(listener);
                            }
                        }
                    });
            Log.i("ServerManager::createIncomingChatListener", "listener creation ended");
        }
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}

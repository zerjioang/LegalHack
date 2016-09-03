package zerjioang.im.legalhack.ui;


import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.jivesoftware.smack.XMPPException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.conversation.ChatArrayAdapter;
import zerjioang.im.legalhack.adapter.lists.conversation.ChatMessage;
import zerjioang.im.legalhack.dao.DataManager;
import zerjioang.im.legalhack.exceptions.UserIsNotConnectedException;
import zerjioang.im.legalhack.net.DefaultInternetRequestCallback;
import zerjioang.im.legalhack.net.server.ServerManager;
import zerjioang.im.legalhack.util.Utilities;

public class ConversationActivity extends BaseActivity {

    private static final String TAG = "ConversationFragment";

    private ChatArrayAdapter chatArrayAdapter;
    private List mensajes;
    private ListView listView;
    private EditText chatText;
    private ImageView buttonSend;
    private boolean side;
    private String dstUsername;

    public ConversationActivity() {
    }



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        findElements();
        chatArrayAdapter = new ChatArrayAdapter(getActivity(), R.layout.list_message_item);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        String nombreChat = getIntent().getStringExtra("nombreChat");
        dstUsername = getIntent().getStringExtra("destinationUser");

        if (nombreChat == null){
            nombreChat = DataManager.getInstance().getNotifName();
        }

        if (dstUsername==null) {
            dstUsername = DataManager.getInstance().getDestinationUser().substring(0, 9);
        }

        mensajes = DataManager.getInstance().sacarMensajes(dstUsername);
        if(mensajes==null)
            mensajes = new ArrayList();

        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setTitle(nombreChat);
        }

        eventos();

        Utilities.isServerAlive(getActivity(), new DefaultInternetRequestCallback() {
            @Override
            public void execute(boolean alive) {
                // Create a connection to the custom server.
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            String connectedIp = settings.getIp();
                            String user = settings.getUsuario();
                            String pass = settings.getPass();
                            ServerManager.getInstance().connect(connectedIp);
                            ServerManager.getInstance().login(getActivity(), user, pass);
                        } catch (IOException | XMPPException | UserIsNotConnectedException e) {
                            if (e.getLocalizedMessage() != null) {
                                Log.i("ConversationActivity::onCreate", e.getLocalizedMessage());
                            }
                        }
                        return null;
                    }
                }.execute();
            }
        }, DefaultInternetRequestCallback.NETWORK_TASK);
    }

    @Override
    public void findElements() {
        buttonSend = (ImageView) findViewById(R.id.buttonSend);
        chatText = (EditText) findViewById(R.id.editTextMessage);
        listView = (ListView) findViewById(R.id.listViewMessages);
    }

    @Override
    public void eventos() {
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private boolean sendChatMessage() {
        String str = chatText.getText().toString();
        if (str.trim().length() > 0) {
            boolean sent = server.sendMessageCurrentChat(this, str, dstUsername);
            if (sent) {
                addNewMessage(str, true);
                chatText.setText("");
            }
            return sent;
        }
        return false;
    }


    public void addNewMessage(String msg, boolean serverMessage) {
        chatArrayAdapter.add(new ChatMessage(serverMessage, msg));
    }

    public void updateChatView() {
        chatArrayAdapter.notifyDataSetChanged();
    }

}

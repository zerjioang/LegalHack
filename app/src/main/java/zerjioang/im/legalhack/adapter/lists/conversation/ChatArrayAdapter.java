package zerjioang.im.legalhack.adapter.lists.conversation;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zerjioang.im.legalhack.R;

public class ChatArrayAdapter extends ArrayAdapter<ChatMessage> {

    private final Context context;
    private List<ChatMessage> chatMessageList = new ArrayList();

    public ChatArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return this.chatMessageList.get(index);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_message_item, parent, false);
        }
        LinearLayout singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        ChatMessage chatMessageObj = getItem(position);
        TextView chatText = (TextView) row.findViewById(R.id.singleMessage);
        chatText.setText(chatMessageObj.message);
        chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.msg_in : R.drawable.msg_out);
        //modificar el padding
        float scale = context.getResources().getDisplayMetrics().density;
        if (chatMessageObj.left)
            //left, top, right, bottom
            chatText.setPadding(
                    (int) (18 * scale + 0.5f),
                    (int) (9 * scale + 0.5f),
                    (int) (10 * scale + 0.5f),
                    (int) (10 * scale + 0.5f)
            );
        else
            chatText.setPadding(
                    (int) (10 * scale + 0.5f),
                    (int) (9 * scale + 0.5f),
                    (int) (18 * scale + 0.5f),
                    (int) (10 * scale + 0.5f)
            );
        singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public void add(ChatMessage msg) {
        chatMessageList.add(msg);
        notifyDataSetChanged();
    }
}
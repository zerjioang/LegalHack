package zerjioang.im.legalhack.adapter.navbar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.interfaces.IDrawerItem;
import zerjioang.im.legalhack.ui.BaseActivity;

/**
 * Created by sergio on 7/2/15
 */

public class DrawerItem implements IDrawerItem {

    private String itenName;
    private String iconName;
    private int layoutID;
    private View view;
    private BaseActivity ba;

    public DrawerItem(BaseActivity ba, String itemName, String iconName, int layoutID) {
        this.ba = ba;
        itenName = itemName;
        this.iconName = iconName;
        this.layoutID = layoutID;
    }

    public String getItenName() {
        return itenName;
    }

    public void setItenName(String itenName) {
        TextView text = (TextView) view.findViewById(R.id.drawer_itemName);
        this.itenName = itenName;
        text.setText(this.itenName);
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public View getView(Context context, LayoutInflater inflater, ViewGroup parent) {
        view = inflater.inflate(layoutID, parent, false);
        return view;
    }

    @Override
    public void init() {
        setItenName(itenName);
        setIcon();
    }

    private void setIcon() {
        ImageView icon = (ImageView) view.findViewById(R.id.imageViewIcon);
        int icond = getDrawableFromStr(iconName);
        if (icond != -1) {
            icon.setImageResource(icond);
            //White tint icon
            icon.setColorFilter(Color.argb(255, 255, 255, 255));
        }
    }

    public int getDrawableFromStr(String s) {
        Context context = ba;
        int id = context.getResources().getIdentifier(s, "drawable", context.getPackageName());
        return id;
    }

    @Override
    public int getLayoutID() {
        return layoutID;
    }
}
package zerjioang.im.legalhack.adapter.navbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.interfaces.IDrawerItem;

/**
 * Created by sergio on 7/2/15
 */
public class DrawerHeader implements IDrawerItem {

    private int layoutID;
    private String itemName;
    private View view;
    private String nombreDescriptivo;

    public DrawerHeader(int layoutID, String user) {
        this.layoutID = layoutID;
        nombreDescriptivo = user;
    }

    @Override
    public int getLayoutID() {
        return layoutID;
    }

    public String getItenName() {
        return itemName;
    }


    public void setItenName(String itemName) {
        this.itemName = itemName;
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
        TextView text = (TextView) view.findViewById(R.id.textViewNombre);
        text.setText(nombreDescriptivo);
    }

    public String getNombreDescriptivo() {
        return nombreDescriptivo;
    }

    public void setNombreDescriptivo(String nombreDescriptivo) {
        this.nombreDescriptivo = nombreDescriptivo;
    }
}
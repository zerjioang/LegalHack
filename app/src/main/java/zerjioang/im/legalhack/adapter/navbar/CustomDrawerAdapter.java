package zerjioang.im.legalhack.adapter.navbar;

/**
 * Created by Administrador on 03/03/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import zerjioang.im.legalhack.interfaces.IDrawerItem;


/**
 * Created by sergio on 7/2/15
 */
public class CustomDrawerAdapter extends ArrayAdapter<IDrawerItem> {

    final Context context;
    final List<IDrawerItem> drawerItemList;
    int layoutResID;

    public CustomDrawerAdapter(Context context, int layoutResourceID, List<IDrawerItem> listItems) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        IDrawerItem drawerHolder;
        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = drawerItemList.get(position);
            view = drawerHolder.getView(context, inflater, parent);
            view.setTag(drawerHolder);

        } else {
            drawerHolder = (IDrawerItem) view.getTag();
        }

        //una vez tenemos el objeto lo rellenamos con los datos
        drawerHolder.init();

        return view;
    }
}
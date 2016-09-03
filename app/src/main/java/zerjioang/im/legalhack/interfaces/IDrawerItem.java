package zerjioang.im.legalhack.interfaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sergio on 7/2/15
 */
public interface IDrawerItem {

    public int getLayoutID();

    public View getView(Context context, LayoutInflater inflater, ViewGroup parent);

    public void init();
}

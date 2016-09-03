package zerjioang.im.legalhack.net;

/**
 * Created by sanguita on 05/05/2015.
 */
public abstract class DefaultInternetRequestCallback {

    public static final int NETWORK_TASK = 0;
    public static final int GUI_TASK = 1;

    public abstract void execute(boolean alive);
}

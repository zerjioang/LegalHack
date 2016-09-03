package zerjioang.im.legalhack.adapter.lists.centro;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Clase que contiene SOLO los datos relacionados con los datos del servidor
 */
@ParseClassName("Centro")
public class CentroItemList extends ParseObject {

    public CentroItemList() {
    }

    public String getIp() {
        return String.valueOf(this.get("ip"));
    }

    public void setIp(String ip) {
        this.put("ip", ip);
    }

    public String getPort() {
        return String.valueOf(this.get("port"));
    }

    public void setPort(String port) {
        this.put("port", port);
    }

    public String getServername() {
        return String.valueOf(this.get("servername"));
    }

    public void setServername(String servername) {
        this.put("servername", servername);
    }

    public String getVisibleName() {
        return String.valueOf(this.get("visiblename"));
    }

    public void setVisibleName(String visibleName) {
        this.put("visiblename", visibleName);
    }

    public boolean getLoginEnabled() {
        return (boolean) get("loginEnabled");
    }

    public void setLoginEnabled(boolean enable) {
        this.put("loginEnabled", enable);
    }
}

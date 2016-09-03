package zerjioang.im.legalhack.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.lists.contact.ContactItemList;
import zerjioang.im.legalhack.modules.SettingsManagerModule;
import zerjioang.im.legalhack.net.DefaultInternetRequestCallback;

/**
 * Created by Administrador on 02/03/2015.
 */
public class Utilities {

    public static final int DIALOG_OK_CANCEL = 2;
    public static final int DIALOG_OK = 1;
    private static final int NO_BUTTONS = -1;
    private static final int DOWNLOAD_OK = 200;
    private static final String SERVER_URL = "http://217.160.105.215";
    private static final long BYTES_MAX_FOTO = 1000000;

    private static String tempID;

    public static void showMessage(Activity activity, String title, String msg, MaterialDialog.ButtonCallback callback, int buttons) {
        //SOURCE: https://github.com/afollestad/material-dialogs
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(activity)
                .title(title)
                .content(msg);
        if (callback != null) {
            dialog.callback(callback);
        }
        dialog.positiveColorRes(R.color.dialog_btn_color);
        dialog.positiveText(android.R.string.ok);
        if (buttons == DIALOG_OK_CANCEL) {
            dialog.negativeText(android.R.string.cancel);
            dialog.negativeColorRes(R.color.dialog_btn_color);
        }
        dialog.show();
    }

    public static String getUUIDMAC(Context c) {

        if (tempID == null) {
            String id;
            TelephonyManager tManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
            String uuid = tManager.getDeviceId();
            WifiManager manager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            String macAddress = info.getMacAddress();
            id = uuid + " " + macAddress;
            Log.i("Device identifier: ", id);
            tempID = id;
        }
        return tempID;
    }

    public static String getUUIDMAC(Activity mainActivity) {
        return getUUIDMAC(mainActivity.getApplicationContext());
    }

    public static boolean checkSDExists() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static boolean detectEmulatorPresence(Context c) {
        return
                Build.FINGERPRINT.startsWith("generic") ||
                        "google_sdk".equals(Build.PRODUCT) ||
                        "sdk".equals(Build.PRODUCT) ||
                        "sdk_x86".equals(Build.PRODUCT) ||
                        "vbox86p".equals(Build.PRODUCT) ||
                        "goldfish".equals(Build.HARDWARE) || checkCarrier(c);
    }

    private static boolean checkCarrier(Context c) {
        TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String networkOperator = tm.getNetworkOperatorName();
        return "Android".equals(networkOperator);
    }

    public static void showToast(Activity activity, String msg) {
        if (activity != null && msg != null) {
            Context context = activity.getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, msg, duration);
            toast.show();
        }
    }

    public static String convertTagId(byte[] bytesTag) {
        int i, j, in;
        String[] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for (j = 0; j < bytesTag.length; ++j) {
            in = (int) bytesTag[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    public static void showNotCancelableMessage(Activity activity, String title, String msg) {
        Utilities.showForcedMessage(activity, title, msg, null, NO_BUTTONS);
    }

    public static void showForcedMessage(Activity activity, String title, String msg, MaterialDialog.ButtonCallback callback, int buttons) {
        //SOURCE: https://github.com/afollestad/material-dialogs
        MaterialDialog.Builder dialog = new MaterialDialog.Builder(activity)
                .title(title)
                .cancelable(false)
                .content(msg);
        if (callback != null) {
            dialog.callback(callback);
        }
        if (buttons == DIALOG_OK) {
            dialog.positiveColorRes(R.color.dialog_btn_color);
            dialog.positiveText(android.R.string.ok);
        } else if (buttons == DIALOG_OK_CANCEL) {

            dialog.positiveColorRes(R.color.dialog_btn_color);
            dialog.positiveText(android.R.string.ok);

            dialog.negativeText(android.R.string.cancel);
            dialog.negativeColorRes(R.color.dialog_btn_color);
        }
        dialog.show();
    }

    public static String getJSONUrl(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == DOWNLOAD_OK) {
                // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }

    public static String getDeviceId(Activity activity) {
        return Settings.Secure.getString(activity.getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isWifior4G(Activity activity) {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        boolean wifiConnected = wifiInfo.getState() == NetworkInfo.State.CONNECTED;
        boolean mobileConnected = mobileInfo.getState() == NetworkInfo.State.CONNECTED;
        return wifiConnected || mobileConnected;
    }

    public static String getCarrierName(Context c) {
        TelephonyManager manager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        return manager.getNetworkOperatorName();
    }

    public static boolean hasValidCarrier(Context c) {
        return getCarrierName(c) != null && getCarrierName(c).trim().length() > 0;
    }

    public static String getMD5EncryptedString(String encTarget) {
        MessageDigest mdEnc = null;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Exception while encrypting to md5");
            e.printStackTrace();
        } // Encryption algorithm
        mdEnc.update(encTarget.getBytes(), 0, encTarget.length());
        String md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        while (md5.length() < 32) {
            md5 = "0" + md5;
        }
        return md5;
    }

    /**
     * Redimensiona la imagen hasta que esta tiene una resolucion inferior a 1024kb
     */
    public static void redimensionarImagen(File f) {
        Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
        //redimensionarla hasta que sea menor que 1024Kb
        long bytesTotal = f.length();
        Log.i("Bytes totales", bytesTotal + " supera el limite? " + (bytesTotal > (BYTES_MAX_FOTO)));
        Bitmap out = b;
        boolean isResized = false;
        while (bytesTotal > (BYTES_MAX_FOTO)) {
            int width = (int) (out.getWidth() - out.getWidth() * 0.1);
            int height = (int) (out.getHeight() - out.getHeight() * 0.1);
            Log.i("nuevas dimensiones", width + " " + height);
            out = Bitmap.createScaledBitmap(b, width, height, false);
            bytesTotal = out.getRowBytes() * out.getHeight();
            isResized = true;
        }
        if (isResized) {
            //guardar la imagen en disco una vez redimensionada
            File file = new File(f.getAbsolutePath());
            FileOutputStream fOut;
            try {
                fOut = new FileOutputStream(file);
                out.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
                b.recycle();
                out.recycle();
            } catch (Exception e) {
                Log.i("redimensionarImagen", e.getMessage());
            }
        }
    }

    public static File getDataFolder() {
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "infociudadanos");
    }

    public static boolean isServerAlive(final Context c, final DefaultInternetRequestCallback function, final int mode) {

        final boolean[] ret = {false};

        new AsyncTask<Void, Void, Boolean>() {

            private String error;

            @Override
            protected Boolean doInBackground(Void... params) {
                //ejecutar aqui el metodo que se necesita hacer separado de la interfaz de usuario
                try {
                    SettingsManagerModule settings = SettingsManagerModule.getInstance(c);
                    String ip = settings.getIp();
                    String port = settings.getPuerto();
                    if (ip != null && port != null) {
                        SocketAddress sockaddr = new InetSocketAddress(ip, Integer.valueOf(port));
                        // Create an unbound socket
                        Socket sock = new Socket();

                        // This method will block no more than timeoutMs.
                        // If the timeout occurs, SocketTimeoutException is thrown.
                        int timeoutMs = Constants.SERVER_CHECK_TIMEOUT;   // 2 seconds
                        sock.connect(sockaddr, timeoutMs);
                        ret[0] = true;
                    } else
                        ret[0] = false;
                } catch (IOException e) {
                    Log.e("isServerAlive", e.getLocalizedMessage());
                    error = e.getMessage();
                }
                return ret[0];
            }

            @Override
            protected void onPostExecute(final Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                //mostrar error en caso de que haya
                if (error != null)
                    Utilities.showToast((Activity) c, error);
                //ejecutar la funcion del usuario
                if (mode == DefaultInternetRequestCallback.GUI_TASK)
                    //ejecutar la tarea en el hilo de la interfaz
                    function.execute(aBoolean);
                else if (mode == DefaultInternetRequestCallback.NETWORK_TASK) {
                    //ejecutar la tarea en el doInBackground de un Asyntask
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            function.execute(aBoolean);
                            return null;
                        }
                    }.execute();
                }
            }
        }.execute();

        return ret[0];
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static String getStreetFromLocation(Context c, Location loc) throws IOException {
        //Obtener la direcci—n de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            Geocoder geocoder = new Geocoder(c, Locale.getDefault());
            List<Address> list = geocoder.getFromLocation(
                    loc.getLatitude(), loc.getLongitude(), 1);
            if (!list.isEmpty()) {
                Address address = list.get(0);
                return
                        "Código de pais: " + address.getCountryCode() + "\n"
                                + "Pais: " + address.getCountryName() + "\n"
                                + "Dirección: " + address.getThoroughfare() + ", " + address.getSubThoroughfare() + "\n"
                                + "Provincia: " + address.getLocality() + ", " + address.getSubAdminArea() + "\n"
                                + "Codigo postal: " + address.getPostalCode() + "\n"
                                + "Coordenadas: " + loc.getLatitude() + " " + loc.getLongitude();
            }
        }
        throw new IOException();
    }

    public static Bitmap roundImageCorners(Bitmap bitmap, int roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static String obtenerIMEI(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    public static String obtenerIMSI(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telephonyManager.getSimSerialNumber();
        return imsi;
    }

    public static List<ContactItemList> obtenerContactos(Activity activity) {
        List<ContactItemList> lista = Cache.listaContactos;
        if (lista != null) {
            return lista;
        } else {
            lista = new LinkedList<>();
        }

        ContentResolver cr = activity.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int phone = Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (phone > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    String phonestr = "";
                    while (pCur.moveToNext()) {
                        // Do something with phones
                        phonestr = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    pCur.close();
                    Uri image = getPhotoUri(activity, id);
                    lista.add(new ContactItemList(id, name, phonestr, image));
                }
            }
        }
        cur.close();
        Cache.listaContactos = lista;
        return lista;
    }

    public static Uri getPhotoUri(Activity activity, String id) {
        try {
            Cursor cur = activity.getContentResolver().query(
                    ContactsContract.Data.CONTENT_URI,
                    null,
                    ContactsContract.Data.CONTACT_ID + "=" + id + " AND "
                            + ContactsContract.Data.MIMETYPE + "='"
                            + ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE + "'", null,
                    null);
            if (cur != null) {
                if (!cur.moveToFirst()) {
                    cur.close();
                    return null; // no photo
                }
                cur.close();
            } else {
                return null; // error in cursor process
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long
                .parseLong(id));
        return Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);
    }
}

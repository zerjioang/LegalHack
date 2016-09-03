package zerjioang.im.legalhack.ui.settings;

/**
 * Created by Administrador on 02/03/2015.
 */

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.Serializable;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.ui.BaseActivity;

public class SettingsActivity extends BaseActivity implements Serializable {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pref_with_actionbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        getFragmentManager().beginTransaction().replace(R.id.content_frame, new MyPreferenceFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                closeSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        closeSettings();
    }

    private void closeSettings() {
        this.finish();
    }

    @Override
    public void findElements() {

    }

    @Override
    public void eventos() {

    }

    public static class MyPreferenceFragment extends PreferenceFragment {

        public MyPreferenceFragment() {
        }

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.pref_vault);
            addPreferencesFromResource(R.xml.pref_info);

            eventos();
        }

        private void eventos() {

            //changelog
            Preference version = findPreference("changelog");
            version.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    //inflate
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View promptsView = li.inflate(R.layout.changelog, null);

                    //load
                    final WebView webview = (WebView) promptsView.findViewById(R.id.webView);
                    webview.loadUrl("file:///android_asset/changelog.html");

                    //build
                    boolean wrapInScroll = true;
                    MaterialDialog.Builder dialog = new MaterialDialog.Builder(getActivity())
                            .title(getString(R.string.app_name))
                            .customView(promptsView, wrapInScroll);
                    dialog.callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                        }
                    });
                    dialog.backgroundColor(Color.WHITE);
                    dialog.positiveColorRes(R.color.dialog_btn_color);
                    dialog.positiveText(android.R.string.ok);

                    //show
                    dialog.show();
                    return true;
                }
            });

            //licencias de codigo abierto
            Preference license = findPreference("licencias");
            license.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    //inflate
                    LayoutInflater li = LayoutInflater.from(getActivity());
                    View promptsView = li.inflate(R.layout.changelog, null);

                    //load
                    final WebView webview = (WebView) promptsView.findViewById(R.id.webView);
                    webview.loadUrl("file:///android_asset/license.html");

                    //build
                    boolean wrapInScroll = true;
                    MaterialDialog.Builder dialog = new MaterialDialog.Builder(getActivity())
                            .title(getString(R.string.title_licencias))
                            .customView(promptsView, wrapInScroll);
                    dialog.callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                        }
                    });
                    dialog.backgroundColor(Color.WHITE);
                    dialog.positiveColorRes(R.color.dialog_btn_color);
                    dialog.positiveText(android.R.string.ok);

                    //show
                    dialog.show();
                    return true;
                }
            });
        }
    }
}

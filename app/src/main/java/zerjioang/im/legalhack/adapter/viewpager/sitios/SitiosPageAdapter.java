package zerjioang.im.legalhack.adapter.viewpager.sitios;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.ui.fragment.sitios.sub_fragmentos.HistorialSitiosFragment;
import zerjioang.im.legalhack.ui.fragment.sitios.sub_fragmentos.NuevoSitioFragment;

/**
 * Created by sanguita on 15/05/2015.
 */
public class SitiosPageAdapter extends FragmentStatePagerAdapter {

    private static final int tabIcons[] = {R.drawable.domain, R.drawable.clock};
    private static final String tabsTitle[] = {"Nuevo", "Historial"};
    private static int NUM_ITEMS = 2;

    public SitiosPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return NuevoSitioFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return HistorialSitiosFragment.newInstance();
            default:
                return null;
        }
    }

    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {
        return tabsTitle[position];
    }

}

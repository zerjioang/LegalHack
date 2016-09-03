package zerjioang.im.legalhack.adapter.viewpager.sitios.legal;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import zerjioang.im.legalhack.ui.fragment.legal.sub_fragmentos.ColaboradoresFragment;
import zerjioang.im.legalhack.ui.fragment.legal.sub_fragmentos.FinanciacionFragment;
import zerjioang.im.legalhack.ui.fragment.legal.sub_fragmentos.InfoGeneralFragment;
import zerjioang.im.legalhack.ui.fragment.legal.sub_fragmentos.PrivacidadFragment;

/**
 * Created by sanguita on 15/05/2015.
 */
public class LegalPageAdapter extends FragmentStatePagerAdapter {

    private static final String tabsTitle[] = {"Informacion general", "Privacidad", "Colaboradores de terceros", "Financiacion"};
    private static int NUM_ITEMS = tabsTitle.length;

    public LegalPageAdapter(FragmentManager fragmentManager) {
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
                return InfoGeneralFragment.newInstance();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return PrivacidadFragment.newInstance();
            case 2: // Fragment # 0 - This will show FirstFragment
                return ColaboradoresFragment.newInstance();
            case 3: // Fragment # 0 - This will show FirstFragment different title
                return FinanciacionFragment.newInstance();
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

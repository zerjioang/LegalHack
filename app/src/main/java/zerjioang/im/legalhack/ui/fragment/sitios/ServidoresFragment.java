package zerjioang.im.legalhack.ui.fragment.sitios;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.adapter.viewpager.sitios.SitiosPageAdapter;
import zerjioang.im.legalhack.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServidoresFragment extends Fragment {

    private View view;
    private ViewPager vpPager;
    private SitiosPageAdapter adapterViewPager;
    private PagerSlidingTabStrip tabs;

    public ServidoresFragment() {
        // Required empty public constructor
    }

    public static ServidoresFragment newInstance() {
        ServidoresFragment fragment = new ServidoresFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_servidores, container, false);
        findElements();
        init();
        return view;
    }

    private void init() {
        int tabsColor = getResources().getColor(R.color.material_color_White);
        int bgColor = Color.parseColor(Constants.TABS_BG_COLOR);
        tabs.setTextColor(tabsColor);
        tabs.setDividerColor(tabsColor);
        tabs.setIndicatorColor(tabsColor);
        tabs.setBackgroundColor(bgColor);
    }

    private void findElements() {
        vpPager = (ViewPager) view.findViewById(R.id.vpPager);
        vpPager.setAdapter(new SitiosPageAdapter(getActivity().getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(vpPager);
    }
}

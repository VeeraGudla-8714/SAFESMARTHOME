//Veera Gudla N01218714
//Jerin Joy N01276691
//Jacob Stephens N01100888
//Patrick Loboda N01309086

package ca.team.safe.smart.home.it.safe.smart.home.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ca.team.safe.smart.home.it.safe.smart.home.AddressFragment;
import ca.team.safe.smart.home.it.safe.smart.home.ControlFragment;
import ca.team.safe.smart.home.it.safe.smart.home.FireFragment;

import ca.team.safe.smart.home.it.safe.smart.home.R;
import ca.team.safe.smart.home.it.safe.smart.home.ReviewFragment;
import ca.team.safe.smart.home.it.safe.smart.home.SettingsFragment;
import ca.team.safe.smart.home.it.safe.smart.home.StatusFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{ R.string.tab_text_2, R.string.tab_text_3,
            R.string.home_control, R.string.tab_text_5, R.string.tab_text_6,R.string.Settings_page};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch(position){
            case 0:
                fragment = new AddressFragment();
                break;
            case 1:
                fragment = new StatusFragment();
                break;
            case 2:
                fragment = new ControlFragment();
                break;
            case 3:
                fragment = new FireFragment();
                break;
            case 4:
                fragment = new ReviewFragment();
                break;
            case 5:
                fragment = new SettingsFragment();
                break;


        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 7total pages.
        return 6;
    }
}
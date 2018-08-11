package e.ruu.shokusin2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MenuFragmentPagerAdapter extends FragmentPagerAdapter {
    Fragment favoriteFragment=null;
    public MenuFragmentPagerAdapter(FragmentManager fm){
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        fragment = new MenuFragment();
        if(position==0){
            if(favoriteFragment==null) {
                favoriteFragment = new FavoriteMenuFragment();
            }
            fragment=favoriteFragment;
        }
        if(position==1)fragment= new AllMenuFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("menugroupid",position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return MenuDict.getInstance().menugrouplen();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "ページ" + (position + 1);
    }
}

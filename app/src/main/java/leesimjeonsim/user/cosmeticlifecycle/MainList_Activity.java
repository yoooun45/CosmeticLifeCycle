package leesimjeonsim.user.cosmeticlifecycle;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainList_Activity extends AppCompatActivity {

    NavigationAdapter mNavigation;//ViewPager을 위한 어뎁터
    ViewPager mViewPager;
    TabLayout mTab;
    public static int Num = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_);
        /*Intent intent = new Intent(getBaseContext(),LoginActivity.class);
        startActivity(intent);
*/
        mNavigation = new NavigationAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mNavigation);

        mTab = (TabLayout)findViewById(R.id.tab);
        mTab.setupWithViewPager(mViewPager);

    }

    protected void onStart() {
        super.onStart();

    }

    private static class NavigationAdapter extends FragmentPagerAdapter {
        //FragmentPagerAdapter 이전화면을 계속 가지고 있음
        private static final String[] TITLES = new String[]{"리스트", "추천", "기타"};
        public NavigationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //각 페이지의 이름을 얻음
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            // Initialize fragments.
            // Please be sure to pass scroll position to each fragments using setArguments.
            //각 화면으로 넘어가게 해주는 함수
            final int pattern = position % 3;
            switch (pattern) {
                case 0: {
                    return ItemlifeFragment.newInstance(Num);
                }
                case 1: {
                    return rcmdFragmend.newInstance("dd","ss");
                }
                case 2: {
                    return ItemlifeFragment.newInstance(Num);
                }
            }

            return null;
        }

        @Override
        public int getCount() {
            //몇 페이지 인지 알게 해줌
            return TITLES.length;
        }

    }
}

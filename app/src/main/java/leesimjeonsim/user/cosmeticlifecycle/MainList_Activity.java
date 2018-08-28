package leesimjeonsim.user.cosmeticlifecycle;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainList_Activity extends AppCompatActivity {

    NavigationAdapter mNavigation;//ViewPager을 위한 어뎁터
    ViewPager mViewPager;
    TabLayout mTab;
    public static int Num = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("화통기한");
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigation = new NavigationAdapter(getSupportFragmentManager());
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(mNavigation);

        mTab = (TabLayout)findViewById(R.id.tab);
        mTab.setupWithViewPager(mViewPager);

    }

    protected void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //or switch문을 이용하면 될듯 하다.
        if (id == android.R.id.home) {
            Toast.makeText(this, "홈아이콘 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_setting) {
            Toast.makeText(this, "메뉴버튼 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_logout) {
            Toast.makeText(this, "로그아웃버튼 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_about) {
            Toast.makeText(this, "설정버튼 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void refresh(){
        mNavigation.notifyDataSetChanged();
    }
    private static class NavigationAdapter extends FragmentStatePagerAdapter {
        //FragmentPagerAdapter 이전화면을 계속 가지고 있음
        private static final String[] TITLES = new String[]{"리스트", "추천"};
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
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            // Initialize fragments.
            // Please be sure to pass scroll position to each fragments using setArguments.
            //각 화면으로 넘어가게 해주는 함수
            final int pattern = position % 2;
            switch (pattern) {
                case 0: {
                    return ItemlifeFragment.newInstance(Num);
                }
                case 1: {
                    return rcmdFragmend.newInstance("dd","ss");
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

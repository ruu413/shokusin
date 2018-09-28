package e.ruu.shokusin2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import java.util.ArrayList;



public class MenuActivity extends AppCompatActivity {
    ArrayList<MenuButton> buttons=new ArrayList<>();
    ArrayList<LinearLayout> innerlayouts=new ArrayList<>();
    OrderDataHolder orderDataHolder=new OrderDataHolder();
    TextView valueText;
    MenuDict menuDict;
    MenuFragmentPagerAdapter adapter;
    ViewPager viewPager;
    ViewPager viewPager2;
    FragmentPagerAdapter searchadapter;
    AppBarLayout appBar2Layout;
    AppBarLayout searchAppBar2Layout;
    SearchView searchView;
    ToggleButton searchbutton;
    MenuMode menuMode=MenuMode.LIST;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //SetView.setActionbar(getApplication(),getSupportActionBar(),"メニュー選択");

        Context context=getApplication();
        LinearLayout appbarlayout = new LinearLayout(context);
        LinearLayout appbarstrlayout=new LinearLayout(context);
        LinearLayout appbarbuttonlayout=new LinearLayout(context);
        SetView.setActionbar(context,getSupportActionBar(),appbarlayout);
        appbarlayout.addView(appbarstrlayout);
        appbarlayout.addView(appbarbuttonlayout);
        TextView appbartextview=new TextView(context);
        searchbutton=new ToggleButton(context);
        searchbutton.setText("");
        searchbutton.setTextOn("");
        searchbutton.setTextOff("");
        searchbutton.setBackgroundResource(R.drawable.searchbutton);
        searchbutton.setLayoutParams(new LinearLayout.LayoutParams(
                ConvertSize.dp_to_px(context,30),
                ConvertSize.dp_to_px(context,30)
        ));
        appbarstrlayout.addView(appbartextview);
        appbarbuttonlayout.addView(searchbutton);
        appbarbuttonlayout.setLayoutParams(new LinearLayout.LayoutParams(
                ConvertSize.dp_to_px(context,70),
                LinearLayout.LayoutParams.MATCH_PARENT));
        appbarstrlayout.setLayoutParams(new LinearLayout.LayoutParams(
                ConvertSize.width_per_to_px(context,100)-ConvertSize.dp_to_px(context,70),
                LinearLayout.LayoutParams.MATCH_PARENT));
        appbartextview.setText("メニュー選択");
        appbartextview.setTextColor(Color.WHITE);
        appbartextview.setTextSize(ConvertSize.dp_to_px(context,7));
        searchbutton.setChecked(false);
        searchbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToggleButton button = (ToggleButton)v;
                if(button.isChecked())changeMode(MenuMode.SEARCH);
                else changeMode(MenuMode.LIST);
            }
        });

        valueText = findViewById(R.id.valuetext);
        menuDict=MenuDict.getInstance();//new MenuDict(getResources());
        if(menuDict==null)finish();
        valueText.setText("合計:"+String.valueOf(menuDict.getValue(orderDataHolder)+"円"));
        //LinearLayout layout1=findViewById(R.id.layout1);//ここから
        // フラグメントマネージャーでフラグメント操作をする
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        //FragmentManager manager=getFragmentManager();
        viewPager=findViewById(R.id.viewpager);
        viewPager2=findViewById(R.id.viewpager2);
        //vpParent=findViewById(R.id.vpparent);
        //viewPager=new ViewPager(getApplication());
        /*viewPager.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));*/
        //vpParent.addView(viewPager);
        adapter = new MenuFragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);
        android.support.v4.app.FragmentManager manager2=getSupportFragmentManager();
        searchadapter=new FragmentPagerAdapter(manager2) {
            android.support.v4.app.Fragment fragment=null;
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                if(fragment==null)fragment=new SearchMenuFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("menugroupid",0);
                fragment.setArguments(bundle);
                return fragment;
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
        viewPager2.setAdapter(searchadapter);
        //vpParent.addView(searchFragment);
        //final ViewPager viewPager1 =viewPager;
        appBar2Layout=findViewById(R.id.appBarLayout);
        searchAppBar2Layout=findViewById(R.id.searchAppBarLayout);
        searchView = findViewById(R.id.searchview);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                reload(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                reload(newText);
                return true;
            }
            private void reload(String str){
                SearchMenuFragment fragment = (SearchMenuFragment) searchadapter.getItem(0);
                fragment.setSearchString(str);
                fragment.reload();
            }
        });
        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean flag) {

                //フォーカスが外れた場合、ソフトウェアキーボードを非表示
                if (flag == false) {
                    final InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                } else {

                    //フォーカスを取得した場合、ソフトウェアキーボードを表示
                    final InputMethodManager imm =
                            (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(v, 0);
                }
            }
        });
        final Spinner spinner = findViewById(R.id.spinner);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                appBar2Layout.setExpanded(true);
                spinner.setSelection(position,false);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムを取得します
                String item = (String) spinner.getSelectedItem();
                viewPager.setCurrentItem(position,false);
                appBar2Layout.setExpanded(true);
                //Toast.makeText(SpinnerSampleActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        LinearLayout.LayoutParams buttonparam =new LinearLayout.LayoutParams(
                ConvertSize.width_per_to_px(getBaseContext(),50),ConvertSize.dp_to_px(getBaseContext(),70)
        );
        Button progressbutton=findViewById(R.id.button_ok);
        progressbutton.setLayoutParams(buttonparam);
        progressbutton.setWidth(ConvertSize.width_per_to_px(getBaseContext(),50));
        progressbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplication(),SeatDecideActivity.class);
                intent.putExtra("orderdataholder",orderDataHolder);
                //startActivityForResult(intent,finalI);
                startActivity(intent);
            }
        });
        Button godeletebutton = findViewById(R.id.godelete);
        godeletebutton.setLayoutParams(buttonparam);
        godeletebutton.setWidth(ConvertSize.width_per_to_px(getBaseContext(),50));
        godeletebutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplication(),MenuDeleteActivity.class);
                intent.putExtra("orderdataholder",orderDataHolder);
                //startActivityForResult(intent,finalI);
                startActivityForResult(intent,111);
            }
        });
    }

    protected void onActivityResult(int reqCode,int resultCode,Intent intent){
        super.onActivityResult(reqCode, resultCode, intent);
        valueText.setText("合計"+String.valueOf(menuDict.getValue(orderDataHolder))+"円");
        searchView.clearFocus();
        if(intent==null) {
            if(menuDict.getFavoriteChangeFlag()&&(viewPager).getCurrentItem()<2) {
                //recreate();
                ((MenuFragment)(adapter.getItem(0))).reload();
            }
            return;
        }
        if(resultCode==10000){
            finish();
            return;
        }
        if(resultCode==111||reqCode==111){
            orderDataHolder=(OrderDataHolder)intent.getSerializableExtra("orderdataholder");
            //finish();
            valueText.setText("合計"+String.valueOf(menuDict.getValue(orderDataHolder))+"円");

            //if(menuDict.getFavoriteChangeFlag())recreate();
            if(menuDict.getFavoriteChangeFlag()&&(viewPager).getCurrentItem()<2) {
                //recreate();
                ((MenuFragment) (adapter.getItem(0))).reload();
            }
            return;
        }
        //int menuid=reqCode;
        //int optionid[]=intent.getIntArrayExtra("menuoption");
        OrderData retorder=(OrderData)intent.getSerializableExtra("menuoption");
        //buttons.get(0).setText(menuid);
        //buttons.get(2).setText(String.valueOf(retorder.optionid[1]));
        orderDataHolder.puts(retorder);
        valueText.setText("合計"+String.valueOf(menuDict.getValue(orderDataHolder))+"円");
        //if(menuDict.getFavoriteChangeFlag())recreate();

        if(menuDict.getFavoriteChangeFlag()){//&&((ViewPager)findViewById(R.id.viewpager)).getCurrentItem()<2) {
            //recreate();
            ((MenuFragment) (adapter.getItem(0))).reload();
        }
    }
    protected enum MenuMode{
        LIST,SEARCH
    }
    protected void changeMode(MenuMode menuMode){
        if(menuMode==MenuMode.LIST){
            this.menuMode=MenuMode.LIST;
            appBar2Layout.setVisibility(AppBarLayout.VISIBLE);
            searchAppBar2Layout.setVisibility(AppBarLayout.INVISIBLE);
            appBar2Layout.setExpanded(true);
            viewPager.setVisibility(ViewPager.VISIBLE);
            viewPager2.setVisibility(ViewPager.INVISIBLE);
            searchView.setFocusable(false);
            searchView.setFocusableInTouchMode(false);
            searchView.clearFocus();
            //searchView.setIconified(true);
        }
        if(menuMode==MenuMode.SEARCH){
            this.menuMode=MenuMode.SEARCH;
            appBar2Layout.setVisibility(AppBarLayout.INVISIBLE);
            searchAppBar2Layout.setVisibility(AppBarLayout.VISIBLE);
            viewPager.setVisibility(ViewPager.INVISIBLE);
            viewPager2.setVisibility(ViewPager.VISIBLE);
            searchView.setFocusable(true);
            searchView.setFocusableInTouchMode(true);
            searchView.requestFocus();
            searchView.setIconified(false);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != KeyEvent.KEYCODE_BACK){
            return super.onKeyDown(keyCode, event);
        }else {
            if (this.menuMode == MenuMode.LIST) {
                return super.onKeyDown(keyCode, event);
            }else {
                changeMode(MenuMode.LIST);
                searchbutton.setChecked(false);
                searchView.clearFocus();
                return false;
            }
        }
    }
}

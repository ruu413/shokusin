package e.ruu.shokusin2;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity {
    ArrayList<MenuButton> buttons=new ArrayList<>();
    ArrayList<LinearLayout> innerlayouts=new ArrayList<>();
    OrderDataHolder orderDataHolder=new OrderDataHolder();
    TextView valueText;
    MenuDict menuDict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SetView.setActionbar(getApplication(),getSupportActionBar(),"メニュー選択");
        valueText = findViewById(R.id.valuetext);
        menuDict=MenuDict.getInstance();//new MenuDict(getResources());
        if(menuDict==null)finish();
        //setContentView(R.layout.activity_main);
        /*ScrollView scrollView=new ScrollView(this);
        LinearLayout layout1 = new LinearLayout(this);
        layout1.setOrientation(LinearLayout.VERTICAL);
        layout1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        scrollView.addView(layout1);
        setContentView(scrollView);*/
        //MenuDict menuDict=new MenuDict(getResources());
        valueText.setText("合計:"+String.valueOf(menuDict.getValue(orderDataHolder)+"円"));
        //LinearLayout layout1=findViewById(R.id.layout1);//ここから
        // フラグメントマネージャーでフラグメント操作をする
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        //FragmentManager manager=getFragmentManager();
        final ViewPager viewPager=findViewById(R.id.viewpager);
        MenuFragmentPagerAdapter adapter = new MenuFragmentPagerAdapter(manager);
        viewPager.setAdapter(adapter);
        //final ViewPager viewPager1 =viewPager;
        final Spinner spinner = findViewById(R.id.spinner);

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
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
                //Toast.makeText(SpinnerSampleActivity.this, item, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        /*FragmentManager fragmentManager = getFragmentManager();
// 処理の開
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

// 追加したいフラグメントの生成
        MenuFragment fragment = new MenuFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("menugroupid",3);
        fragment.setArguments(bundle);
// フラグメントの追加(第１引数：フラグメントをくっつける親ビュー　第２引数:追加するフラグメント)
        fragmentTransaction.add(R.id.layout1, fragment);
// 処理の確定
        fragmentTransaction.commit();*/
        /*for(int i=0;i<menuDict.menulen();i++) {
            buttons.add(new MenuButton(this));
            buttons.get(i).setText(menuDict.menustr(i));
            buttons.get(i).setValueText(menuDict.menuvaluestr(i));

            //buttons.get(i).setText("a");
            final int finalI = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    Intent intent=new Intent(getApplication(),MenuoptionActivity.class);
                    intent.putExtra("menuid", finalI);
                    startActivityForResult(intent,finalI);
                }
            });

        }
        for(int i=0;i<=menuDict.menulen()/2;i++){
            innerlayouts.add(new LinearLayout(this));
            innerlayouts.get(i).setOrientation(LinearLayout.HORIZONTAL);
            innerlayouts.get(i).setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            if(menuDict.menulen()>i*2) {
                innerlayouts.get(i).addView(buttons.get(i * 2));
                if (menuDict.menulen() > i * 2 + 1) {
                    innerlayouts.get(i).addView(buttons.get(i * 2 + 1));
                }
            }
            layout1.addView(innerlayouts.get(i));

        }
        /*Button menubutton =findViewById(R.id.gomenubutton);
        menubutton.setText("menu");
        menubutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplication(),MenuoptionActivity.class);
                startActivity(intent);
            }
        });*/

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
        if(intent==null) {
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
            return;
        }
        //int menuid=reqCode;
        //int optionid[]=intent.getIntArrayExtra("menuoption");
        OrderData retorder=(OrderData)intent.getSerializableExtra("menuoption");
        //buttons.get(0).setText(menuid);
        //buttons.get(2).setText(String.valueOf(retorder.optionid[1]));
        orderDataHolder.puts(retorder);
        valueText.setText("合計"+String.valueOf(menuDict.getValue(orderDataHolder))+"円");
    }
}

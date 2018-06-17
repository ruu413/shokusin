package e.ruu.shokusin2;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


public class MenuActivity extends AppCompatActivity {
    ArrayList<MenuButton> buttons=new ArrayList<MenuButton>();
    ArrayList<LinearLayout> innerlayouts=new ArrayList<LinearLayout>();
    OrderDataHolder orderDataHolder=new OrderDataHolder();
    TextView valueText;
    MenuDict menuDict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        valueText = findViewById(R.id.valuetext);
        menuDict=new MenuDict(getResources());
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
        valueText.setText(String.valueOf(menuDict.getValue(orderDataHolder)));
        LinearLayout layout1=findViewById(R.id.layout1);
        for(int i=0;i<menuDict.menulen();i++) {
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
        Button progressbutton=findViewById(R.id.button_ok);
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
        valueText.setText(String.valueOf(menuDict.getValue(orderDataHolder)));
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
            valueText.setText(String.valueOf(menuDict.getValue(orderDataHolder)));
            return;
        }
        //int menuid=reqCode;
        //int optionid[]=intent.getIntArrayExtra("menuoption");
        OrderData retorder=(OrderData)intent.getSerializableExtra("menuoption");
        //buttons.get(0).setText(menuid);
        //buttons.get(2).setText(String.valueOf(retorder.optionid[1]));
        orderDataHolder.puts(retorder);
        valueText.setText(String.valueOf(menuDict.getValue(orderDataHolder)));
    }
}

package e.ruu.shokusin2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MenuDeleteActivity extends AppCompatActivity {
    OrderDataHolder orderDataHolder;
    ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
    LinearLayout rootlayout;
    ArrayList<Button> deletebuttons = new ArrayList<>();
    //MenuDict menuDict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menudelete);
        SetView.setActionbar(getApplication(),getSupportActionBar(),"注文確認/削除");
        //menuDict =new MenuDict(getResources());
        MenuDict menuDict=MenuDict.getInstance();//new MenuDict(getResources());
        if(menuDict==null)finish();
        rootlayout = findViewById(R.id.menudelete);
        Intent intent = getIntent();
        orderDataHolder = (OrderDataHolder) intent.getSerializableExtra("orderdataholder");
        if(orderDataHolder==null)finish();
        setButton(menuDict);
    }
    void setButton(final MenuDict menuDict){
        rootlayout.removeAllViews();
        linearLayouts.clear();
        for(int i = 0;i<orderDataHolder.size();++i) {
            LinearLayout linearLayout = new LinearLayout(getApplication());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setBackgroundResource(R.drawable.text_border);
            linearLayouts.add(linearLayout);
            LinearLayout linearLayout1 = new LinearLayout(getApplication());
            linearLayout1.setOrientation(LinearLayout.VERTICAL);
            linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(
                    ConvertSize.width_per_to_px(getApplication(),40),
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout linearLayout2 = new LinearLayout(getApplication());
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                    ConvertSize.width_per_to_px(getApplication(),20),
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout linearLayout3 = new LinearLayout(getApplication());
            linearLayout3.setOrientation(LinearLayout.VERTICAL);
            linearLayout3.setLayoutParams(new LinearLayout.LayoutParams(
                    ConvertSize.width_per_to_px(getApplication(),20),
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout linearLayout4 = new LinearLayout(getApplication());
            linearLayout4.setOrientation(LinearLayout.VERTICAL);
            linearLayout4.setLayoutParams(new LinearLayout.LayoutParams(
                    ConvertSize.width_per_to_px(getApplication(),20),
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(linearLayout1);
            linearLayout.addView(linearLayout2);
            linearLayout.addView(linearLayout3);
            linearLayout.addView(linearLayout4);
            int textsize=16;
            TextView textView1 = new TextView(getApplication());
            textView1.setTextColor(Color.BLACK);
            textView1.setTextSize(textsize);
            textView1.setText(menuDict.menustr(orderDataHolder.get(i).menugroupid,orderDataHolder.get(i).menuid));
            linearLayout1.addView(textView1);
            for(int ii=0;ii<orderDataHolder.get(i).optnum();ii++) {
                TextView textView2=new TextView(getApplication());
                if(ii%2==1){textView2.setTextColor(Color.BLACK);}
                textView2.setTextSize(textsize);
                textView2.setText(menuDict.menuoptstr(orderDataHolder.get(i).menugroupid,orderDataHolder.get(i).menuid, orderDataHolder.get(i).optionid.get(ii)));
                linearLayout2.addView(textView2);
            }
            TextView textView3= new TextView(getApplication());
            textView3.setTextColor(Color.BLACK);
            textView3.setTextSize(textsize);
            textView3.setText(menuDict.menuvaluestrwithopt(orderDataHolder.get(i))+"円");
            linearLayout3.addView(textView3);

            final Button button = new Button(getApplication());
            button.setText("削除");
            linearLayout4.addView(button);
            rootlayout.addView(linearLayout);

            final int finali = i;
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(!orderDataHolder.delete(finali)){
                        button.setText("a");
                    }
                    setButton(menuDict);
                    //reload();
                }
            });
        }

        LinearLayout spaceLayout =new LinearLayout(getApplication());
        spaceLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ConvertSize.dp_to_px(getApplication(),70)
        ));
        rootlayout.addView(spaceLayout);
        Button backbutton = findViewById(R.id.button_ok);
        backbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                intent.putExtra("orderdataholder",orderDataHolder);
                setResult(111,intent);
                finish();
            }
        });

    }
    private void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();

        overridePendingTransition(0, 0);
        startActivity(intent);
    }
}

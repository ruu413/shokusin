package e.ruu.shokusin2;

import android.content.Intent;
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
        //menuDict =new MenuDict(getResources());
        rootlayout = (LinearLayout) findViewById(R.id.menudelete);
        Intent intent = getIntent();
        orderDataHolder = (OrderDataHolder) intent.getSerializableExtra("orderdataholder");
        setButton();
    }
    void setButton(){
        rootlayout.removeAllViews();
        linearLayouts.clear();
        for(int i = 0;i<orderDataHolder.size();++i) {
            MenuDict menuDict=new MenuDict(getResources());
            LinearLayout linearLayout = new LinearLayout(getApplication());
            linearLayouts.add(linearLayout);
            TextView textView1 = new TextView(getApplication());
            textView1.setText(menuDict.menustr(orderDataHolder.get(i).menuid));
            linearLayout.addView(textView1);
            for(int ii=0;ii<orderDataHolder.get(i).optnum();ii++) {
                TextView textView2=new TextView(getApplication());
                textView2.setText(menuDict.menuoptstr(orderDataHolder.get(i).menuid, orderDataHolder.get(i).optionid.get(ii)));
                linearLayout.addView(textView2);
            }
            final Button button = new Button(getApplication());
            button.setText("削除");

            linearLayout.addView(button);
            rootlayout.addView(linearLayout);

            final int finali = i;
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if(!orderDataHolder.delete(finali)){
                        button.setText("a");
                    }
                    setButton();
                    //reload();
                }
            });
        }

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

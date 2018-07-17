package e.ruu.shokusin2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SeatDecideActivity extends AppCompatActivity {
    OrderDataHolder orderDataHolder;
    ArrayList<Button> buttons=new ArrayList<Button>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatdecide);
        SetView.setActionbar(getApplication(),getSupportActionBar(),"座席選択");
        Intent intent=getIntent();
        orderDataHolder=(OrderDataHolder)intent.getSerializableExtra("orderdataholder");
        setButton();
        for(int i=0;i<buttons.size();i++){
            final int finalI=i;
            buttons.get(i).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    Intent intent=new Intent(getApplication(),MenuConfirmActivity.class);
                    orderDataHolder.seat=finalI;
                    intent.putExtra("orderdataholder", orderDataHolder);
                    startActivityForResult(intent,finalI);
                }
            });
        }
    }
    void setButton(){
        buttons.clear();
        buttons.add((Button)findViewById(R.id.button1));
        buttons.add((Button)findViewById(R.id.button2));
        buttons.add((Button)findViewById(R.id.button3));
        buttons.add((Button)findViewById(R.id.button4));
        buttons.add((Button)findViewById(R.id.button5));
        buttons.add((Button)findViewById(R.id.button6));
        buttons.add((Button)findViewById(R.id.button7));
        buttons.add((Button)findViewById(R.id.button8));
        buttons.add((Button)findViewById(R.id.button9));
        buttons.add((Button)findViewById(R.id.button10));
        buttons.add((Button)findViewById(R.id.buttonA));
        buttons.add((Button)findViewById(R.id.buttonB));
        buttons.add((Button)findViewById(R.id.buttonC));
    }

}

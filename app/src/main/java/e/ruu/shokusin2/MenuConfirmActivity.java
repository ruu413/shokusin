package e.ruu.shokusin2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenuConfirmActivity extends AppCompatActivity {

    OrderDataHolder orderDataHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MenuDict menuDict=MenuDict.getInstance();//new MenuDict(getResources());

        if(menuDict==null)finish();
        setContentView(R.layout.activity_menuconfirm);
        SetView.setActionbar(getApplication(),getSupportActionBar(),"注文確認");
        //MenuDict menuDict=new MenuDict(getResources());
        String str="席";
        String url="席";
        Intent intent=getIntent();
        orderDataHolder=(OrderDataHolder)intent.getSerializableExtra("orderdataholder");
        str+=menuDict.seatstr(orderDataHolder.seat);
        str+="\n";
        url+=menuDict.seatstr(orderDataHolder.seat);
        url+="%0D%0A";
        for(int i=0;i<orderDataHolder.size();i++){
            str+=menuDict.menustr2(orderDataHolder.get(i).menugroupid,orderDataHolder.get(i).menuid);
            str+=" ";
            for(int ii=0;ii<orderDataHolder.get(i).optionid.size();++ii) {
                str += menuDict.menuoptstr(orderDataHolder.get(i).menugroupid,orderDataHolder.get(i).menuid, orderDataHolder.get(i).optionid.get(ii));
                str += " ";
            }
            str += "\n";
            url+=menuDict.menustr2(orderDataHolder.get(i).menugroupid,orderDataHolder.get(i).menuid);
            url+=" ";
            for(int ii=0;ii<orderDataHolder.get(i).optionid.size();++ii) {
                url += menuDict.menuoptstr(orderDataHolder.get(i).menugroupid,orderDataHolder.get(i).menuid, orderDataHolder.get(i).optionid.get(ii));
                url += " ";
            }
            url+="%0D%0A";
        }
        TextView textView=findViewById(R.id.textView);
        textView.setText(str);
        final String url_=url;
        Button urlbutton=findViewById(R.id.button_url);
        urlbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplication(),MenuConfirmActivity.class);
                Uri uri = Uri.parse("line://msg/text/"+url_);
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        });
        /*Button goMenuButton =findViewById(R.id.gomenubutton);
        goMenuButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplication(),MenuActivity.class);
                startActivity(intent);
            }
        });*/
    }
}

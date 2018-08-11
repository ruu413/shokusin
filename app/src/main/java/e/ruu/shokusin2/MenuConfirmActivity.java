package e.ruu.shokusin2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MenuConfirmActivity extends AppCompatActivity {

    OrderDataHolder orderDataHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        MenuDict menuDict=MenuDict.getInstance();//new MenuDict(getResources());
        if(menuDict==null)finish();
        setContentView(R.layout.activity_menuconfirm);
        SetView.setActionbar(getApplication(),getSupportActionBar(),"注文確認");
        //MenuDict menuDict=new MenuDict(getResources());
        Intent intent=getIntent();
        orderDataHolder=(OrderDataHolder)intent.getSerializableExtra("orderdataholder");
        if(orderDataHolder==null)finish();
        String str = createMenuString(orderDataHolder,menuDict);
        EditText editText=findViewById(R.id.editText);
        editText.setText(str);
        editText.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s){
                setUrlButton(s.toString());
            }
        });

        setUrlButton(str);
    }
    String createMenuString(OrderDataHolder orderDataHolder,MenuDict menuDict){
        String str="席";
        str+=menuDict.seatstr(orderDataHolder.seat);
        str+="\n";
        //url+="%0D%0A";
        for(int i=0;i<orderDataHolder.size();i++){
            str+=menuDict.menustr2(orderDataHolder.get(i).menugroupid,orderDataHolder.get(i).menuid);
            str+=" ";
            for(int ii=0;ii<orderDataHolder.get(i).optionid.size();++ii) {
                str += menuDict.menuoptstr(orderDataHolder.get(i).menugroupid,orderDataHolder.get(i).menuid, orderDataHolder.get(i).optionid.get(ii));
                str += " ";
            }
            str += "\n";
        }
        return str;
    }
    void setUrlButton(String str){
        final String url=str.replace("\n","%0D%0A");
        Button urlbutton=findViewById(R.id.button_url);
        urlbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplication(),MenuConfirmActivity.class);
                Uri uri = Uri.parse("line://msg/text/"+url);
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        });
    }
}

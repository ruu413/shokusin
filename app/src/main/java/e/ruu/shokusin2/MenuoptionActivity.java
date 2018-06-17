package e.ruu.shokusin2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


public class
MenuoptionActivity extends AppCompatActivity implements View.OnClickListener {
    public final ArrayList<ToggleButton> toggleButtons=new ArrayList<ToggleButton>();
    public ArrayList<Integer> buttonids=new ArrayList<>();
    ArrayList<LinearLayout> linearLayouts=new ArrayList<LinearLayout>();
    ArrayList<Integer> groupflag=new ArrayList<>();
    int optweight=0;
    int menuid=0;
    MenuDict menuDict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuopiton);
        //setToggleButtons();
        //setContentView(R.layout.activity_menuopiton);
        Button progressbutton=findViewById(R.id.button_ok);
        final Intent intent=getIntent();
        //final ToggleButton toggleButton = findViewById(R.id.button2);
        //toggleButton.setOnCheckedChangeListener(this);

        menuid =intent.getIntExtra("menuid",0);


        menuDict=new MenuDict(getResources());
        //TypedArray menuarray=getResources().obtainTypedArray(R.array.menuname);
        LinearLayout layout1=findViewById(R.id.layout1);
        {
            toggleButtons.add((ToggleButton) findViewById(R.id.half));
            toggleButtons.add((ToggleButton) findViewById(R.id.amakuti));
            toggleButtons.add((ToggleButton) findViewById(R.id.tyoama));
            toggleButtons.add((ToggleButton) findViewById(R.id.syoukara));
            toggleButtons.add((ToggleButton) findViewById(R.id.tyuukara));
            toggleButtons.add((ToggleButton) findViewById(R.id.karakuti));
        }
        int commonoptnum=6;
        for(int i=0;i<commonoptnum;i++) {//commonopt
            toggleButtons.get(i).setText(menuDict.menuoptstr(menuid,i));
            toggleButtons.get(i).setTextOn(menuDict.menuoptstr(menuid,i));
            toggleButtons.get(i).setTextOff(menuDict.menuoptstr(menuid,i));
            buttonids.add(i);
            //buttons.get(i).setText("a");
            final int finalI = i;
            //toggleButton.setOnCheckedChangeListener(this);
            toggleButtons.get(i).setOnClickListener(this);
        }
        if(menuid>10)toggleButtons.get(0).setVisibility(View.GONE);//ハーフオプションを消す
        for(int i=0;i<menuDict.menuoptlen(menuid);i++) {//elseopt
            ToggleButton toggleButton=new ToggleButton(this);
            toggleButtons.add(toggleButton);
            buttonids.add(i+10);
            toggleButton.setText(menuDict.menuoptstr(menuid,i+10));
            toggleButton.setTextOn(menuDict.menuoptstr(menuid,i+10));
            toggleButton.setTextOff(menuDict.menuoptstr(menuid,i+10));
            toggleButton.setId((int)(i+10));
            toggleButton.setText(String.valueOf(i+10));
            toggleButton.setTextOff(String.valueOf(i+10));
            toggleButton.setTextOn(String.valueOf(i+10));
            //toggleButton.setTextOff(String.valueOf(menuDict.menuoptweight(menuid,i+10)));

            //buttons.get(i).setText("a");
            final int finalI = i+10;
            toggleButton.setOnClickListener(this);
        }
        for(int i=0;i<=menuDict.menuoptlen(menuid)/3;i++){
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayouts.add(linearLayout);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            if(toggleButtons.size()>i*3+commonoptnum) {
                linearLayout.addView(toggleButtons.get(i * 3 + commonoptnum));
                if(toggleButtons.size()>i*3+1+commonoptnum) {
                    linearLayout.addView(toggleButtons.get(i * 3 + 1 + commonoptnum));
                    if (toggleButtons.size() > i * 3 + 2 + commonoptnum) {
                        linearLayout.addView(toggleButtons.get(i * 3 + 2 + commonoptnum));
                    }
                }
            }
            layout1.addView(linearLayout);
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
        progressbutton.setText(String.valueOf(menuid));
        progressbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //setToggleButtons();
                ArrayList<Integer> opt=new ArrayList<Integer>();
                for(int i=0;i<toggleButtons.size();i++){
                    if(toggleButtons.get(i).isChecked()==true){
                            opt.add(buttonids.get(i));
                    }
                }
                //intent.putExtra("menuoption",new int[]{opt[0],opt[1]});
                intent.putExtra("menuoption",new OrderData(menuid,opt));
                setResult(menuid,intent);
                finish();
            }
        });
    }
    @Override
    public void onClick(View v){
        ToggleButton buttonView =(ToggleButton)v;
        boolean isChecked =((ToggleButton)buttonView).isChecked();
        int maxweight=290;
    //setToggleButtons();
        //buttonView.setChecked();
        for(int i=0;i<toggleButtons.size();++i){
            if(buttonView.getId()==toggleButtons.get(i).getId()){
                if(isChecked) {
                    optweight+=menuDict.menuoptweight(menuid,buttonids.get(i));
                    /*if(optweight>maxweight){
                        optweight-=menuDict.menuoptweight(menuid,buttonids.get(i));
                        buttonView.setChecked(false);
                    }*/
                    if(menuDict.menuoptgroup(menuid,buttonids.get(i))!=0){//メニューグループフラグ追加
                        groupflag.add(menuDict.menuoptgroup(menuid,buttonids.get(i)));
                    }
                }else{
                    optweight-=menuDict.menuoptweight(menuid,buttonids.get(i));
                    if(menuDict.menuoptgroup(menuid,buttonids.get(i))!=0){//メニューグループフラグ削除
                        ArrayList<Integer> arrayList = new ArrayList<>();
                        arrayList.add(menuDict.menuoptgroup(menuid,buttonids.get(i)));
                        groupflag.removeAll(arrayList);
                    }
                }
                toggleButtons.get(1).setTextOn(String.valueOf(buttonids.get(i)));
                toggleButtons.get(1).setTextOff(String.valueOf(buttonids.get(i)));
                toggleButtons.get(1).setText(String.valueOf(buttonids.get(i)));
                /*for(int ii=0;ii<buttonids.size();++ii){
                    toggleButtons.get(ii).setTextOn(String.valueOf(buttonids.get(ii)));
                    toggleButtons.get(ii).setTextOff(String.valueOf(buttonids.get(ii)));
                    toggleButtons.get(ii).setText(String.valueOf(buttonids.get(ii)));
                }*/
                break;
            };
        }
        for (int i=0;i<toggleButtons.size();++i){//追加できないメニューのボタンを使用不可に
            if(!toggleButtons.get(i).isChecked()){
                if (optweight+menuDict.menuoptweight(menuid,buttonids.get(i))>maxweight){//weightが規定以上
                    toggleButtons.get(i).setEnabled(false);
                }else{
                    toggleButtons.get(i).setEnabled(true);
                }
                if(groupflag.contains(menuDict.menuoptgroup(menuid,buttonids.get(i)))){//groupのメニューがすでに選ばれている
                    toggleButtons.get(i).setEnabled(false);
                }
            }
        }
        /*if(buttonView.getId()==R.id.button2){
            for(int i=0;i<toggleButtons.size();i++){
                toggleButtons.get(i).setChecked(false);
                toggleButtons.get(i).isChecked();

            }
        }*/
    }
    /*void setToggleButtons(){
        toggleButtons.clear();
        //toggleButtons.add((ToggleButton)findViewById(R.id.button1));
        //toggleButtons.add((ToggleButton)findViewById(R.id.button2));
        toggleButtons.add((ToggleButton)findViewById(R.id.button3));
        toggleButtons.add((ToggleButton)findViewById(R.id.button4));
        toggleButtons.add((ToggleButton)findViewById(R.id.button11));
        toggleButtons.add((ToggleButton)findViewById(R.id.button12));
        toggleButtons.add((ToggleButton)findViewById(R.id.button13));
        toggleButtons.add((ToggleButton)findViewById(R.id.button14));
        toggleButtons.add((ToggleButton)findViewById(R.id.button21));
        toggleButtons.add((ToggleButton)findViewById(R.id.button22));
        toggleButtons.add((ToggleButton)findViewById(R.id.button23));
        toggleButtons.add((ToggleButton)findViewById(R.id.button24));
        toggleButtons.add((ToggleButton)findViewById(R.id.button31));
        toggleButtons.add((ToggleButton)findViewById(R.id.button32));
        toggleButtons.add((ToggleButton)findViewById(R.id.button33));
    }*/
}

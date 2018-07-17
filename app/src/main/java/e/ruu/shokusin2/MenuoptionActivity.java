package e.ruu.shokusin2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;


public class
MenuoptionActivity extends AppCompatActivity implements View.OnClickListener {//TODO menugroupidをどうにかする
    public final ArrayList<ToggleButton> toggleButtons=new ArrayList<>();
    public ArrayList<Integer> buttonids=new ArrayList<>();
    ArrayList<LinearLayout> linearLayouts=new ArrayList<>();
    ArrayList<Integer> groupflag=new ArrayList<>();
    int optweight=0;
    int menuid=0;
    int menugroupid=0;
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
        menuDict=MenuDict.getInstance();//new MenuDict(getResources());
        if(menuDict==null)finish();
        menugroupid=intent.getIntExtra("menugroupid",0);
        if(menugroupid==0){//menugroupid==0(全メニュー)の時それぞれのメニューグループに振り分ける
            if(menuid<menuDict.menulen(0)/2) {
                for (int i = 1; i < menuDict.menugrouplen(); ++i) {
                    for (int ii = 0; ii < menuDict.menulen(i); ++ii) {
                        if (menuDict.menustr(i, ii) == menuDict.menustr(menugroupid, menuid)) {
                            menugroupid = i;
                            menuid = ii;
                            i = 1000;
                            break;
                        }
                    }
                }
            }else{
                for (int i = menuDict.menugrouplen()-1; i>0; --i) {
                    for (int ii = 0; ii < menuDict.menulen(i); ++ii) {
                        if (menuDict.menustr(i, ii) == menuDict.menustr(menugroupid, menuid)) {
                            menugroupid = i;
                            menuid = ii;
                            i = 0;
                            break;
                        }
                    }
                }
            }
        }
        String menustr=menuDict.menustr(menugroupid,menuid);
        SetView.setActionbar(getApplication(),getSupportActionBar(),menustr+"のオプション選択");
        //TypedArray menuarray=getResources().obtainTypedArray(R.array.menuname);
        LinearLayout layout1=findViewById(R.id.layout1);
        /*{
            toggleButtons.add((ToggleButton) findViewById(R.id.amakuti));
            toggleButtons.add((ToggleButton) findViewById(R.id.tyoama));
            toggleButtons.add((ToggleButton) findViewById(R.id.syoukara));
            toggleButtons.add((ToggleButton) findViewById(R.id.tyuukara));
            toggleButtons.add((ToggleButton) findViewById(R.id.karakuti));
        }*/

        LinearLayout.LayoutParams buttonparam = new LinearLayout.LayoutParams(
                ConvertSize.width_per_to_px(getBaseContext(),50),
                ConvertSize.dp_to_px(getBaseContext(),50));
        int commonoptnum=menuDict.commonoptlen(menugroupid);
        for(int i=0;i<commonoptnum;i++) {//commonopt
            ToggleButton toggleButton=new ToggleButton(this);
            String menuoptstr=menuDict.menuoptstr(menugroupid,menuid,i);
            String valuestr =menuDict.menuoptvaluestr(menugroupid,menuid,i);
            if(!valuestr.isEmpty()&&Integer.parseInt(valuestr)!=0)
            {
                menuoptstr+="("+valuestr+"円)";
            }
            toggleButton.setText(menuoptstr);
            toggleButton.setTextOn(menuoptstr);
            toggleButton.setTextOff(menuoptstr);
            toggleButton.setLayoutParams(buttonparam);
            toggleButton.setId(i);
            buttonids.add(i);
            //buttons.get(i).setText("a");
            //final int finalI = i;
            //toggleButton.setOnCheckedChangeListener(this);
            toggleButton.setOnClickListener(this);
            toggleButtons.add(toggleButton);
        }
        for(int i=0;i<menuDict.menuoptlen(menugroupid,menuid);i++) {//elseopt
            ToggleButton toggleButton=new ToggleButton(this);
            toggleButtons.add(toggleButton);
            buttonids.add(i+50);
            String menuoptstr=menuDict.menuoptstr(menugroupid,menuid,i+50);

            String valuestr =menuDict.menuoptvaluestr(menugroupid,menuid,i+50);
            if(!valuestr.isEmpty()&&Integer.parseInt(valuestr)!=0)
            {
              menuoptstr+="("+valuestr+"円)";
            }
            toggleButton.setText(menuoptstr);
            toggleButton.setTextOn(menuoptstr);
            toggleButton.setTextOff(menuoptstr);
            toggleButton.setLayoutParams(buttonparam);
            toggleButton.setId(i+50);
            //toggleButton.setTextOff(String.valueOf(menuDict.menuoptweight(menuid,i+50)));

            //buttons.get(i).setText("a");
            //final int finalI = i+50;
            toggleButton.setOnClickListener(this);
        }
        int togglebuttonssize=toggleButtons.size();
        for(int i=0;i<=toggleButtons.size()/2;i++){
            LinearLayout linearLayout=new LinearLayout(this);
            linearLayouts.add(linearLayout);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            if(togglebuttonssize>i*2) {
                linearLayout.addView(toggleButtons.get(i * 2 ));
                if(togglebuttonssize>i*2+1) {
                    linearLayout.addView(toggleButtons.get(i * 2 + 1));
                    /*if (togglebuttonssize > i * 3 + 2) {
                        linearLayout.addView(toggleButtons.get(i * 3 + 2));
                    }*/
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
        //progressbutton.setText(String.valueOf(menuid));
        progressbutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //setToggleButtons();
                ArrayList<Integer> opt=new ArrayList<>();
                for(int i=0;i<toggleButtons.size();i++){
                    if(toggleButtons.get(i).isChecked()){
                            opt.add(buttonids.get(i));
                    }
                }
                //intent.putExtra("menuoption",new int[]{opt[0],opt[1]});
                intent.putExtra("menuoption",new OrderData(menugroupid,menuid,opt));
                setResult(menuid,intent);
                finish();
            }
        });
        LinearLayout spaceLayout =new LinearLayout(getApplication());
        spaceLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ConvertSize.dp_to_px(getApplication(),70)
        ));
        layout1.addView(spaceLayout);
    }
    @Override
    public void onClick(View v){
        ToggleButton buttonView =(ToggleButton)v;
        boolean isChecked =buttonView.isChecked();
        int maxweight=290;
    //setToggleButtons();
        //buttonView.setChecked();
        for(int i=0;i<toggleButtons.size();++i){
            if(buttonView.getId()==toggleButtons.get(i).getId()){
                int menuoptgroup=menuDict.menuoptgroup(menugroupid,menuid,buttonids.get(i));
                int optweight_1=menuDict.menuoptweight(menugroupid,menuid,buttonids.get(i));//今のメニューオプションのウェイト
                if(isChecked) {
                    optweight+=optweight_1;
                    /*if(optweight>maxweight){
                        optweight-=menuDict.menuoptweight(menuid,buttonids.get(i));
                        buttonView.setChecked(false);
                    }*/
                    if(menuoptgroup!=0){//メニューグループフラグ追加
                        groupflag.add(menuoptgroup);
                    }
                }else{
                    optweight-=optweight_1;
                    if(menuoptgroup!=0){//メニューグループフラグ削除
                        ArrayList<Integer> arrayList = new ArrayList<>();
                        arrayList.add(menuoptgroup);
                        groupflag.removeAll(arrayList);
                    }
                }
                /*
                toggleButtons.get(1).setTextOn(String.valueOf(buttonids.get(i)));
                toggleButtons.get(1).setTextOff(String.valueOf(buttonids.get(i)));
                toggleButtons.get(1).setText(String.valueOf(buttonids.get(i)));*/
                /*for(int ii=0;ii<buttonids.size();++ii){
                    toggleButtons.get(ii).setTextOn(String.valueOf(buttonids.get(ii)));
                    toggleButtons.get(ii).setTextOff(String.valueOf(buttonids.get(ii)));
                    toggleButtons.get(ii).setText(String.valueOf(buttonids.get(ii)));
                }*/
                break;
            }
        }
        for (int i=0;i<toggleButtons.size();++i){//追加できないメニューのボタンを使用不可に
            if(!toggleButtons.get(i).isChecked()){
                if (optweight+menuDict.menuoptweight(menugroupid,menuid,buttonids.get(i))>maxweight){//weightが規定以上
                    toggleButtons.get(i).setEnabled(false);
                }else{
                    toggleButtons.get(i).setEnabled(true);
                }
                if(groupflag.contains(menuDict.menuoptgroup(menugroupid,menuid,buttonids.get(i)))){//groupのメニューがすでに選ばれている
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

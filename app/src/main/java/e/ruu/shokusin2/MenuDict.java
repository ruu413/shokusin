package e.ruu.shokusin2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.R.array;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.Serializable;

public class MenuDict{
    public TypedArray typedArray;//=getResources().obtainTypedArray(0);
    public Resources res;
    public String[] commonoptstr={"ハーフ,290","甘口,100,0,1","超甘,100,0,1","小辛,100,0,2","中辛,100,0,2","辛口,100,0,2","","","","","","",""};
    public MenuDict(Resources res){
        this.res=res;
        typedArray=res.obtainTypedArray(R.array.menuname);
    }
    public String menustr(int menuid) {
        return (String) typedArray.getTextArray(menuid)[0];
    }
    public String menustr2(int menuid) {
        return (String) typedArray.getTextArray(menuid)[1];
    }
    public String seatstr(int seatnum){
        String[] seats={"1","2","3","4","5","6","7","8","9","A","B","C"};
        return seats[seatnum];
    }
    public String menuvaluestr(int menuid){
        return (String)typedArray.getTextArray(menuid)[2];
    }
    public int menulen(){
        return typedArray.length();
    }
    public String menuoptstr(int menuid,int menuoptid){//メニューオプション
        if(menuid>menulen())return"";
        if(menuoptid-10>menuoptlen(menuid))return "";
        if(menuoptid>=10){
            return ((String)typedArray.getTextArray(menuid)[menuoptid+4-10]).split(",")[0];
        }else if(menuoptid>=0){
            return (String)commonoptstr[menuoptid].split(",")[0];
        }else{
            return"";
        }
    }
    public String menuoptvaluestr(int menuid,int menuoptid){
        if(menuid>menulen())return"0";
        if(menuoptid-10>menuoptlen(menuid))return "0";
        if(menuoptid>=10){
            String retstr="0";
            try { retstr= ((String)typedArray.getTextArray(menuid)[menuoptid+4-10]).split(",")[2];}catch (ArrayIndexOutOfBoundsException e){}
            return retstr;
        }else if(menuoptid>=0){
            String retstr="0";
            try{retstr = (String)commonoptstr[menuoptid].split(",")[2];}catch (ArrayIndexOutOfBoundsException e){}
            return retstr;
        }else{
            return "0";
        }
    }
    public int menuoptweight(int menuid,int menuoptid){
        if(menuid>menulen())return 0;
        if(menuoptid-10>menuoptlen(menuid))return 0;
        if(menuoptid>=10){
            String retstr="100";
            try { retstr= ((String)typedArray.getTextArray(menuid)[menuoptid+4-10]).split(",")[1];}catch (ArrayIndexOutOfBoundsException e){return 100;}
            return Integer.parseInt(retstr);
        }else if(menuoptid>=0){
            String retstr="100";
            try{retstr = (String)commonoptstr[menuoptid].split(",")[1];}catch (ArrayIndexOutOfBoundsException e){return 100;}
            return Integer.parseInt(retstr);
        }else{
            return 100;
        }
    }
    public int menuoptgroup(int menuid,int menuoptid){
        if(menuid>menulen())return 0;
        if(menuoptid-10>menuoptlen(menuid))return 0;
        if(menuoptid>=10){
            String retstr="0";
            try { retstr= ((String)typedArray.getTextArray(menuid)[menuoptid+4-10]).split(",")[3];}catch (ArrayIndexOutOfBoundsException e){return 0;}
            return Integer.parseInt(retstr);
        }else if(menuoptid>=0){
            String retstr="0";
            try{retstr = (String)commonoptstr[menuoptid].split(",")[3];}catch (ArrayIndexOutOfBoundsException e){return 0;}
            return Integer.parseInt(retstr);
        }else{
            return 0;
        }
    }
    /*
    0-9 commonoption
    10- array[4]-
    */
    public int menuoptlen(int menuid){//オプションの数(普遍含まない)
        if(menuid>menulen())return 0;
        return (int)typedArray.getTextArray(menuid).length-4;
    }

    public int getValue(OrderDataHolder orderDataHolder){//そこそこ処理重い？
        int value=0;
        for(int i=0;i<orderDataHolder.size();i++){
            OrderData orderData=orderDataHolder.get(i);
            value+=Integer.parseInt(menuvaluestr(orderData.menuid));
            for(int ii=0;ii<orderData.optnum();++ii) {
                value += Integer.parseInt(menuoptvaluestr(orderData.menuid, orderData.optionid.get(ii)));
            }
        }
        return value;
    }

}

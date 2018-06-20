package e.ruu.shokusin2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.R.array;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class MenuDict{
    private ArrayList<TypedArray> typedArrays;// = new TypedArray<TypedArray>();//=getResources().obtainTypedArray(0);
    private Resources res;
    private String[] commonoptstr={"甘口,100,0,1","超甘,100,0,1","小辛,100,0,2","中辛,100,0,2","辛口,100,0,2"};
    private static MenuDict instance;
    private MenuDict(Resources res){
        this.res=res;
        typedArrays = new ArrayList<>();
        TypedArray typedArray1=res.obtainTypedArray(R.array.menugroup);
        for(int i=0;i<typedArray1.length();++i) {
            typedArrays.add(res.obtainTypedArray(typedArray1.getResourceId(i, 0)));
        }
        instance=this;
    }

    public static MenuDict getInstance() {
        return instance;
    }
    public static void createInstance(Resources res){
        instance = new MenuDict(res);
    }
    public int commonoptlen(){
        return commonoptstr.length;
    }
    public int menugrouplen() {
        return typedArrays.size();
    }    /*
    public String menustr(int menuid) {
        return (String) typedArrays.get(0).getTextArray(menuid)[0];
    }
    public String menustr2(int menuid) {
        return (String) typedArrays.get(0).getTextArray(menuid)[1];
    }*/
    public String seatstr(int seatnum){
        String[] seats={"1","2","3","4","5","6","7","8","9","A","B","C"};
        return seats[seatnum];
    }/*
    public String menuvaluestr(int menuid){
        return (String)typedArrays.get(0).getTextArray(menuid)[2];
    }
    public int menulen(){
        return typedArrays.get(0).length();
    }
    public String menuoptstr(int menuid,int menuoptid){//メニューオプション
        if(menuid>menulen())return"";
        if(menuoptid-10>menuoptlen(menuid))return "";
        if(menuoptid>=10){
            return ((String)typedArrays.get(0).getTextArray(menuid)[menuoptid+4-10]).split(",")[0];
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
            try { retstr= ((String)typedArrays.get(0).getTextArray(menuid)[menuoptid+4-10]).split(",")[2];}catch (ArrayIndexOutOfBoundsException e){}
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
            try { retstr= ((String)typedArrays.get(0).getTextArray(menuid)[menuoptid+4-10]).split(",")[1];}catch (ArrayIndexOutOfBoundsException e){return 100;}
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
            try { retstr= ((String)typedArrays.get(0).getTextArray(menuid)[menuoptid+4-10]).split(",")[3];}catch (ArrayIndexOutOfBoundsException e){return 0;}
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

    public int menuoptlen(int menuid){//オプションの数(普遍含まない)
        if(menuid>menulen())return 0;
        return (int)typedArrays.get(0).getTextArray(menuid).length-4;
    }*/

    public int getValue(OrderDataHolder orderDataHolder){//そこそこ処理重い？
        int value=0;
        for(int i=0;i<orderDataHolder.size();i++){
            OrderData orderData=orderDataHolder.get(i);
            value+=Integer.parseInt(menuvaluestr(orderData.menugroupid,orderData.menuid));
            for(int ii=0;ii<orderData.optnum();++ii) {
                value += Integer.parseInt(menuoptvaluestr(orderData.menugroupid,orderData.menuid, orderData.optionid.get(ii)));
            }
        }
        return value;
    }

    public String menustr(int menugroupid,int menuid) {
        return (String) typedArrays.get(menugroupid).getTextArray(menuid)[0];
    }
    public String menustr2(int menugroupid,int menuid) {
        return (String) typedArrays.get(menugroupid).getTextArray(menuid)[1];
    }
    public String menuvaluestr(int menugroupid,int menuid){
        return (String)typedArrays.get(menugroupid).getTextArray(menuid)[2];
    }
    public int menulen(int menugroupid){
        return typedArrays.get(menugroupid).length();
    }
    public String menuoptstr(int menugroupid,int menuid,int menuoptid){//メニューオプション
        if(menuid>menulen(menugroupid))return"";
        if(menuoptid-10>menuoptlen(menugroupid,menuid))return "";
        if(menuoptid>=10){
            return ((String)typedArrays.get(menugroupid).getTextArray(menuid)[menuoptid+4-10]).split(",")[0];
        }else if(menuoptid>=0){
            return commonoptstr[menuoptid].split(",")[0];
        }else{
            return"";
        }
    }
    public String menuoptvaluestr(int menugroupid,int menuid,int menuoptid){
        if(menuid>menulen(menugroupid))return"0";
        if(menuoptid-10>menuoptlen(menugroupid,menuid))return "0";
        if(menuoptid>=10){
            String retstr;
            try { retstr= ((String)typedArrays.get(menugroupid).getTextArray(menuid)[menuoptid+4-10]).split(",")[2];}catch (ArrayIndexOutOfBoundsException e){return "0";}
            return retstr;
        }else if(menuoptid>=0){
            String retstr;
            try{retstr = commonoptstr[menuoptid].split(",")[2];}catch (ArrayIndexOutOfBoundsException e){return "0";}
            return retstr;
        }else{
            return "0";
        }
    }
    public int menuoptweight(int menugroupid,int menuid,int menuoptid){
        if(menuid>menulen(menugroupid))return 0;
        if(menuoptid-10>menuoptlen(menugroupid,menuid))return 0;
        if(menuoptid>=10){
            String retstr;
            try { retstr= ((String)typedArrays.get(menugroupid).getTextArray(menuid)[menuoptid+4-10]).split(",")[1];}catch (ArrayIndexOutOfBoundsException e){return 100;}
            return Integer.parseInt(retstr);
        }else if(menuoptid>=0){
            String retstr;
            try{retstr = commonoptstr[menuoptid].split(",")[1];}catch (ArrayIndexOutOfBoundsException e){return 100;}
            return Integer.parseInt(retstr);
        }else{
            return 100;
        }
    }
    public int menuoptgroup(int menugroupid,int menuid,int menuoptid){
        if(menuid>menulen(menugroupid))return 0;
        if(menuoptid-10>menuoptlen(menugroupid,menuid))return 0;
        if(menuoptid>=10){
            String retstr;
            try { retstr= ((String)typedArrays.get(menugroupid).getTextArray(menuid)[menuoptid+4-10]).split(",")[3];}catch (ArrayIndexOutOfBoundsException e){return 0;}
            return Integer.parseInt(retstr);
        }else if(menuoptid>=0){
            String retstr;
            try{retstr = commonoptstr[menuoptid].split(",")[3];}catch (ArrayIndexOutOfBoundsException e){return 0;}
            return Integer.parseInt(retstr);
        }else{
            return 0;
        }
    }
    /*
    0-9 commonoption
    10- array[4]-
    */
    public int menuoptlen(int menugroupid,int menuid){//オプションの数(普遍含まない)
        if(menuid>menulen(menugroupid))return 0;
        return typedArrays.get(menugroupid).getTextArray(menuid).length-4;
    }

}

package e.ruu.shokusin2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.R.array;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MenuDict{
    private ArrayList<TypedArray> typedArrays;// = new TypedArray<TypedArray>();//=getResources().obtainTypedArray(0);
    private Resources res;
    private Context context;
    private ArrayList<ArrayList<Integer>> favoritemenu;//[0]menugroupid[1]menuid
    private boolean favoritechangeflag= false;
    //private String[] commonoptstr={"甘口,100,0,1","超甘,100,0,1","小辛,100,0,2","中辛,100,0,2","辛口,100,0,2"};
    private ArrayList<TypedArray> commonoptstrs;
    private static MenuDict instance;
    private MenuDict(Context context){
        this.context=context;
        this.res=context.getResources();
        typedArrays = new ArrayList<>();
        commonoptstrs = new ArrayList<>();
        TypedArray typedArray1=res.obtainTypedArray(R.array.menugroup);
        TypedArray typedArray2=res.obtainTypedArray(R.array.commonoptmenugroup);
        for(int i=0;i<typedArray1.length();++i) {
            typedArrays.add(res.obtainTypedArray(typedArray1.getResourceId(i, 0)));
        }
        for(int i=0;i<typedArray2.length();++i) {
            commonoptstrs.add(res.obtainTypedArray(typedArray2.getResourceId(i, 0)));
        }
        favoritemenu = new ArrayList<>();
        /*favoritemenu.clear();
        for(int i=2;i<10;i++) {
            ArrayList<Integer> array = new ArrayList<Integer>();
            array.add(i);
            array.add(1);
            favoritemenu.add(array);
        }*/
        readFileFavorite();
        instance=this;
    }

    public static MenuDict getInstance() {
        return instance;
    }
    public static void createInstance(Context context){
        instance = new MenuDict(context);
    }
    /*public int commonoptlen(){
        return commonoptstr.length;
    }*/
    public int commonoptlen(int menugroupid){
        return commonoptstrs.get(menugroupid).length();
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
        String[] seats={"1","2","3","4","5","6","7","8","9","10","A","B","C"};
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
    public String menuvaluestr(int menugroupid,int menuid) {
        return (String) typedArrays.get(menugroupid).getTextArray(menuid)[2];
    }
    public int menulen(int menugroupid){
        return typedArrays.get(menugroupid).length();
    }
    public String menuoptstr(int menugroupid,int menuid,int menuoptid){//メニューオプション
        if(menuid>menulen(menugroupid))return"";
        if(menuoptid-50>menuoptlen(menugroupid,menuid))return "";
        if(menuoptid>=50){
            return ((String)typedArrays.get(menugroupid).getTextArray(menuid)[menuoptid+4-50]).split(",")[0];
        }/*else if(menuoptid>=0){
            return commonoptstr[menuoptid].split(",")[0];
        }*/else if(menuoptid>=0) {
            try{return ((String)commonoptstrs.get(menugroupid).getText(menuoptid)).split(",")[0];}catch (ArrayIndexOutOfBoundsException e){return"";}
        }else {
            return"";
        }
    }
    public String menuoptvaluestr(int menugroupid,int menuid,int menuoptid){
        if(menuid>menulen(menugroupid))return"0";
        if(menuoptid-50>menuoptlen(menugroupid,menuid))return "0";
        if(menuoptid>=50){
            String retstr;
            try { retstr= ((String)typedArrays.get(menugroupid).getTextArray(menuid)[menuoptid+4-50]).split(",")[2];}catch (ArrayIndexOutOfBoundsException e){return "0";}
            if(retstr.isEmpty())return "0";
            return retstr;
        }else if(menuoptid>=0){
            String retstr;
            try{retstr = ((String)commonoptstrs.get(menugroupid).getText(menuoptid)).split(",")[2];}catch (ArrayIndexOutOfBoundsException e){return "0";}
            if(retstr.isEmpty())return "0";
            return retstr;
        }else{
            return "0";
        }
    }
    public int menuoptweight(int menugroupid,int menuid,int menuoptid){
        if(menuid>menulen(menugroupid))return 0;
        if(menuoptid-50>menuoptlen(menugroupid,menuid))return 0;
        if(menuoptid>=50){
            String retstr;
            try { retstr= ((String)typedArrays.get(menugroupid).getTextArray(menuid)[menuoptid+4-50]).split(",")[1];}catch (ArrayIndexOutOfBoundsException e){return 100;}
            if(retstr.isEmpty())return 100;
            return Integer.parseInt(retstr);
        }else if(menuoptid>=0){
            String retstr;
            try{retstr=((String)commonoptstrs.get(menugroupid).getText(menuoptid)).split(",")[1];}catch (ArrayIndexOutOfBoundsException e){return 100;}
            if(retstr.isEmpty())return  100;
            return Integer.parseInt(retstr);
        }else{
            return 100;
        }
    }
    public int menuoptgroup(int menugroupid,int menuid,int menuoptid){
        if(menuid>menulen(menugroupid))return 0;
        if(menuoptid-50>menuoptlen(menugroupid,menuid))return 0;
        if(menuoptid>=50){
            String retstr;
            try { retstr= ((String)typedArrays.get(menugroupid).getTextArray(menuid)[menuoptid+4-50]).split(",")[3];}catch (ArrayIndexOutOfBoundsException e){return 0;}
            if(retstr.isEmpty())return 0;
            return Integer.parseInt(retstr);
        }else if(menuoptid>=0){
            String retstr;
            //try{retstr = commonoptstr[menuoptid].split(",")[3];}catch (ArrayIndexOutOfBoundsException e){return 0;}
            try{retstr = ((String)commonoptstrs.get(menugroupid).getText(menuoptid)).split(",")[3];}catch (ArrayIndexOutOfBoundsException e){return 0;}
            if(retstr.isEmpty())return 0;
            return Integer.parseInt(retstr);
        }else{
            return 0;
        }
    }
    /*；
    0-9 commonoption
    50- array[4]-
    */
    public int menuoptlen(int menugroupid,int menuid){//オプションの数(普遍含まない)
        if(menuid>menulen(menugroupid))return 0;
        return typedArrays.get(menugroupid).getTextArray(menuid).length-4;
    }
    public String menuvaluestrwithopt(OrderData orderData){
        int value = Integer.parseInt(menuvaluestr(orderData.menugroupid,orderData.menuid));
        for(int i=0;i<orderData.optionid.size();i++){
            value+=Integer.parseInt(menuoptvaluestr(orderData.menugroupid,orderData.menuid,orderData.optionid.get(i)));
        }
        return String.valueOf(value);
    }
    public void setFavoriteMenu(int menugroupid, int menuid, boolean set){//favoriteに登録する
        ArrayList array=new ArrayList();
        array.add(menugroupid);
        array.add(menuid);
        if(checkFavoriteMenu(menugroupid,menuid)){
            if(set){
                //to do nothing
            }else{
                for(int i=0;i<favoritemenu.size();i++){
                    if(favoritemenu.get(i).get(0)==menugroupid&&favoritemenu.get(i).get(1)==menuid){
                        favoritemenu.remove(i);
                        writeFileFavorite();
                        break;
                    }
                }
            }
        }else{
            if(set){
                favoritemenu.add(array);
                writeFileFavorite();
            }else{
                //to do nothing
            }
        }
    }
    public boolean checkFavoriteMenu(int menugroupid,int menuid){//favoriteに登録されているか確認する
        for(int i=0;i<favoritemenu.size();i++){
            if(favoritemenu.get(i).get(0)==menugroupid&&favoritemenu.get(i).get(1)==menuid){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Integer> getFavoritemenu(int menugroupid) {//menuidの配列を返す
        return favoritemenu.get(menugroupid);
    }
    public ArrayList<ArrayList<Integer>> getFavoritemenu() {//[menugroupid,menuid]の配列を返す
        return favoritemenu;
    }
    void setFavoriteChangeFlag(){
        favoritechangeflag=true;
    }
    boolean getFavoriteChangeFlag(){
        if(favoritechangeflag){
            favoritechangeflag=false;
            return true;
        }
        return false;
    }
    void readFileFavorite(){
        try {
            FileInputStream in = context.openFileInput("favmenu.dat");
            ObjectInputStream ois = new ObjectInputStream(in);
            favoritemenu = (ArrayList<ArrayList<Integer>>)ois.readObject();
        }catch (IOException e){

        }catch (ClassNotFoundException e){

        }

    }
    void writeFileFavorite(){
        try{
            OutputStream out = context.openFileOutput("favmenu.dat",MODE_PRIVATE);
            ObjectOutputStream oos= new ObjectOutputStream(out);
            oos.writeObject(favoritemenu);
            oos.close();
        }catch (IOException e){

        }
    }
}

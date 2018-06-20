package e.ruu.shokusin2;

import android.content.res.Resources;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDataHolder implements Serializable{
    public ArrayList<OrderData> data=new ArrayList<>();
    public int seat = 0;
    public void puts(int menugroupid,int menuid,ArrayList<Integer>optionid){
        data.add(new OrderData(menugroupid,menuid,optionid));
    }
    public void puts(OrderData data_){
        data.add(data_);
    }
    public int size(){
        return data.size();
    }
    public OrderData get(int i){
        return data.get(i);
    }
    public boolean delete(int index){
        if(index>=size())return false;
        data.remove(index);
        return true;
    }
}

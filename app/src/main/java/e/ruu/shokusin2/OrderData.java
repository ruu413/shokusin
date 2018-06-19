package e.ruu.shokusin2;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class OrderData implements Serializable {
    public int menuid;
    public int menugroupid;
    public ArrayList<Integer> optionid=new ArrayList<>();
    //public Date date;
    public OrderData(){
        this.menuid=0;
        this.menugroupid=0;
        for (int i=0;i<20;++i) {
            this.optionid.add(0);
        }
    }
    /*public OrderData(int menuid,ArrayList<Integer>optionid){
        this.menuid=menuid;
        this.optionid=optionid;
    }*/
    public OrderData(int menugroupid,int menuid,ArrayList<Integer>optionid){
        this.menugroupid=menugroupid;
        this.menuid=menuid;
        this.optionid=optionid;
    }
    public int optnum(){
        return optionid.size();
    }
}

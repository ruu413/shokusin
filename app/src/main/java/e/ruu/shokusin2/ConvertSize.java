package e.ruu.shokusin2;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ConvertSize {
    public static int dp_to_px(Context context, int dp){
        float d = context.getResources().getDisplayMetrics().density;
        return (int)((dp * d) + 0.5);
    }
    public static int width_per_to_px(Context context,int per){
       int width = context.getResources().getDisplayMetrics().widthPixels;
       return (int)(width*0.01*per);
    }
    public static int height_per_to_px(Context context,int per){
        int height = context.getResources().getDisplayMetrics().heightPixels;
        return (int)(height*0.01*per);
    }
    public static int sp_to_px(Context context,int sp){
        float scaled_density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int)(sp * scaled_density);
    }

}

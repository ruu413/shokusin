package e.ruu.shokusin2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MenuButton extends LinearLayout{
    public Button button;
    public TextView textView;
    public TextView textValueView;
    protected int height;
    protected int width;
    protected Paint paint;
    public MenuButton(Context context) {
        super(context);
        setWillNotDraw(false);
        width= getResources().getDisplayMetrics().widthPixels/2;
        height=200;
        setOrientation(LinearLayout.VERTICAL);
        textView= new TextView(getContext());
        textValueView = new TextView(getContext());
        //textValueView.setText("500");
        paint= new Paint();
        ViewGroup.MarginLayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        layoutParams.height = height;
        layoutParams.width = width;
        setLayoutParams(layoutParams);
        addView(textView);
        addView(textValueView);
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        //canvas.drawColor(Color.argb(125, 0, 0, 255));
        // 矩形

        paint.setColor(Color.argb(100, 255, 0, 255));
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        // (x1,y1,x2,y2,paint) 左上の座標(x1,y1), 右下の座標(x2,y2)
        canvas.drawRect(3, 3, width-3, height-3, paint);
    }
    public void setValueText(String s){textValueView.setText(s);}
    public void setText(String s){
        textView.setText(s);
    }
}

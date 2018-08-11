package e.ruu.shokusin2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SetView {
    static void setActionbar(Context context, ActionBar actionBar, String string){
        TextView textView=new TextView(context);
        textView.setText(string);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(ConvertSize.dp_to_px(context,7));
        // 通常表示されるタイトルを非表示にする。
        actionBar.setDisplayShowTitleEnabled(false);
        // 独自のビューを表示するように設定。
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM, android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(textView);
    }
    static void setActionbar(Context context, ActionBar actionBar, View view){
        // 通常表示されるタイトルを非表示にする。
        actionBar.setDisplayShowTitleEnabled(false);
        // 独自のビューを表示するように設定。
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM, android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(view);
    }
}

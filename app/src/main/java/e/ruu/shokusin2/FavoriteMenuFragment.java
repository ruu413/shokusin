package e.ruu.shokusin2;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class FavoriteMenuFragment extends MenuFragment {
    protected void setMenuButton(LinearLayout layout){
        //layout.removeAllViewsInLayout();
        ArrayList<MenuButton> buttons=new ArrayList<>();
        ArrayList<LinearLayout> innerlayouts=new ArrayList<>();
        ArrayList<ArrayList<Integer>> favoritemenu=menuDict.getFavoritemenu();
        for(int i=0;i<favoritemenu.size();i++) {
            final int menugroupid_=favoritemenu.get(i).get(0);
            final int menuid_=favoritemenu.get(i).get(1);
            buttons.add(new MenuButton(getActivity()));
            buttons.get(i).setText(menuDict.menustr(menugroupid_,menuid_));
            buttons.get(i).setValueText(menuDict.menuvaluestr(menugroupid_,menuid_)+"円");

            //buttons.get(i).setText("a");
            buttons.get(i).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    Intent intent=new Intent(getActivity(),MenuoptionActivity.class);
                    intent.putExtra("menuid", menuid_);
                    intent.putExtra("menugroupid",menugroupid_);
                    startActivityForResult(intent,menuid_);
                }
            });

        }

        for(int i=0;i<=buttons.size()/2;i++){
            innerlayouts.add(new LinearLayout(getActivity()));
            innerlayouts.get(i).setOrientation(LinearLayout.HORIZONTAL);
            innerlayouts.get(i).setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            if(buttons.size()>i*2) {
                innerlayouts.get(i).addView(buttons.get(i * 2));
                if (buttons.size() > i * 2 + 1) {
                    innerlayouts.get(i).addView(buttons.get(i * 2 + 1));
                }
            }
            layout.addView(innerlayouts.get(i));
        }
    }
}

package e.ruu.shokusin2;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchMenuFragment extends MenuFragment {
    public String searchstr="";
    public boolean setSearchString(String str){
        searchstr=str;
        return true;
    }

    protected void setMenuButton(LinearLayout layout){
        //layout.removeAllViewsInLayout();
        ArrayList<MenuButton> buttons=new ArrayList<>();
        ArrayList<LinearLayout> innerlayouts=new ArrayList<>();
        int menugroupid=2;
        ArrayList<String> sstr = new ArrayList<>(Arrays.asList(searchstr.split(" ")));
        sstr.remove("");
        sstr.remove(" ");
        if(sstr.size()==0)return;
        for(menugroupid=2;menugroupid<menuDict.menugrouplen();++menugroupid) {
            for (int i = 0; i < menuDict.menulen(menugroupid); i++) {
                int flag=1;
                String menustr=menuDict.menustr(menugroupid, i);
                for(int ii=0;ii<sstr.size();++ii) {
                    if (! menustr.contains(sstr.get(ii))) {
                        flag=0;
                    }
                }
                if(flag==0)continue;
                MenuButton button=new MenuButton((getContext()));
                buttons.add(button);
                button.setText(menustr);
                button.setValueText(menuDict.menuvaluestr(menugroupid, i) + "円");

                //buttons.get(i).setText("a");
                final int finalI = i;
                final int finalMenugroupid = menugroupid;
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MenuoptionActivity.class);
                        intent.putExtra("menuid", finalI);
                        intent.putExtra("menugroupid", finalMenugroupid);
                        startActivityForResult(intent, finalI);
                    }
                });

            }
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

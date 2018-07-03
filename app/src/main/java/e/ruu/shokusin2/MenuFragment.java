package e.ruu.shokusin2;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;


import java.util.ArrayList;

public class MenuFragment extends Fragment {//TODO menugroupidをどうにかする
    public MenuDict menuDict;
    //OrderDataHolder orderDataHolder=new OrderDataHolder();
    int menugroupid=0;
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {
        ArrayList<MenuButton> buttons=new ArrayList<>();
        ArrayList<LinearLayout> innerlayouts=new ArrayList<>();
        menugroupid=getArguments().getInt("menugroupid");
        //menuDict = new MenuDict(getResources());
        menuDict = MenuDict.getInstance();
        NestedScrollView scrollView=new NestedScrollView(getActivity());
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        LinearLayout spaceLayout =new LinearLayout(getContext());
        spaceLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ConvertSize.dp_to_px(getContext(),40)
                ));
        LinearLayout layout1=new LinearLayout(getActivity());
        layout1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout1.setOrientation(LinearLayout.VERTICAL);
        layout1.addView(spaceLayout);
        for(int i=0;i<menuDict.menulen(menugroupid);i++) {
            buttons.add(new MenuButton(getActivity()));
            buttons.get(i).setText(menuDict.menustr(menugroupid,i));
            buttons.get(i).setValueText(menuDict.menuvaluestr(menugroupid,i));

            //buttons.get(i).setText("a");
            final int finalI = i;
            buttons.get(i).setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                    Intent intent=new Intent(getActivity(),MenuoptionActivity.class);
                    intent.putExtra("menuid", finalI);
                    intent.putExtra("menugroupid",menugroupid);
                    startActivityForResult(intent,finalI);
                }
            });

        }
        for(int i=0;i<=menuDict.menulen(menugroupid)/2;i++){
            innerlayouts.add(new LinearLayout(getActivity()));
            innerlayouts.get(i).setOrientation(LinearLayout.HORIZONTAL);
            innerlayouts.get(i).setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            if(menuDict.menulen(menugroupid)>i*2) {
                innerlayouts.get(i).addView(buttons.get(i * 2));
                if (menuDict.menulen(menugroupid) > i * 2 + 1) {
                    innerlayouts.get(i).addView(buttons.get(i * 2 + 1));
                }
            }
            layout1.addView(innerlayouts.get(i));
        }
        LinearLayout spaceLayout2 =new LinearLayout(getContext());
        spaceLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ConvertSize.dp_to_px(getContext(),100)
        ));
        layout1.addView(spaceLayout2);
        scrollView.addView(layout1);
        return scrollView;
    }
    /*
    @Override
    public void onActivityResult(int reqCode,int resultCode,Intent intent){
        super.onActivityResult(reqCode, resultCode, intent);
        if(intent==null) {
            return;
        }
        //int menuid=reqCode;
        //int optionid[]=intent.getIntArrayExtra("menuoption");
        OrderData retorder=(OrderData)intent.getSerializableExtra("menuoption");
        //buttons.get(0).setText(menuid);
        //buttons.get(2).setText(String.valueOf(retorder.optionid[1]));
        ((MenuActivity)getActivity()).orderDataHolder.puts(retorder);
        ((MenuActivity)getActivity()).valueText.setText(String.valueOf(menuDict.getValue(((MenuActivity)getActivity()).orderDataHolder)));
    }*/
}

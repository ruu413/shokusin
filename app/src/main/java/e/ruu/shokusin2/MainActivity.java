package e.ruu.shokusin2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MenuDict.createInstance(getResources());
        MenuDict menuDict= MenuDict.getInstance();
        Button goMenuButton =findViewById(R.id.gomenubutton);
        //goMenuButton.setText(menuDict.menuoptvaluestr(0,0));
        goMenuButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplication(),MenuActivity.class);
                startActivity(intent);
            }
        });
    }
}

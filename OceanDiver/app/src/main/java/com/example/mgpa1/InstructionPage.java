package com.example.mgpa1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class InstructionPage extends Activity {
    ListView lst;
    String[] objects= {"Player", "Obstacles", "Enemy"};
    String[] desc = {"This is player", "This is obstacle", "This is enemy"};
    Integer[] imgid={R.drawable.alienblue, R.drawable.dirt, R.drawable.shark};

    Button btnback;

    @Override
    protected void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.activity_instructions);

        lst = (ListView) findViewById(R.id.listview);
        CustomListview customListview=new CustomListview(this, objects,desc,imgid);
        lst.setAdapter(customListview);


        btnback = findViewById(R.id.button3);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

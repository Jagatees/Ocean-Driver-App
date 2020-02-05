package com.example.mgpa1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class InstructionPage extends AppCompatActivity {
    ListView lst;
    String[] objects= {"Player", "Obstacles", "Enemy", "Coins", "Tap", "Rotate", "Credits"};
    String[] desc = {"This is player", "This is obstacle", "This is enemy", "This is a coin", "This is the controls: tap", "This is the controls: rotate", "Game made by Yanson and Jagatees"};
    Integer[] imgid={R.drawable.alienblue, R.drawable.dirt, R.drawable.shark, R.drawable.coint, R.drawable.fingertap, R.drawable.rotate, R.drawable.zombieidle};
    @Override
    protected void onCreate(Bundle savedinstanceState) {
        super.onCreate(savedinstanceState);
        setContentView(R.layout.activity_instructions);

        lst = (ListView) findViewById(R.id.listview);
        CustomListview customListview=new CustomListview(this, objects,desc,imgid);
        lst.setAdapter(customListview);

    }
}

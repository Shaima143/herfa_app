package com.herfa.android.herfa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LevelsScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    private int [] levels={R.string.introduction,R.string.basic_tools,
            R.string.the_first_project,R.string.the_second_project,R.string.more_professional_tools};
    private int[] lock_imags={R.drawable.ic_menu_camera,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera
            ,R.drawable.ic_menu_camera,R.drawable.ic_menu_camera};
    private ArrayList<LevelsDetails> carpenteryDetailsList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels_screen);
        setTitle(R.string.carpentry);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        navigation.setSelectedItemId(R.id.navigation_home);

        carpenteryDetailsList= new ArrayList<>();


        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_carpentryLevels);
        createlist();

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager
                (LevelsScreen.this,LinearLayoutManager.VERTICAL,false);
        LevelsAdapter carpenteryLevelsAdapter=new LevelsAdapter
                (LevelsScreen.this,carpenteryDetailsList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(carpenteryLevelsAdapter);

    }
    public void createlist(){
        for(int i=0;i<levels.length;i++){
            LevelsDetails carpenteryDetails=new LevelsDetails();
            carpenteryDetails.setLevel(levels[i]);
            carpenteryDetails.setImage(lock_imags[i]);
            carpenteryDetailsList.add(carpenteryDetails);

        }
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            View view;

            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;

                case R.id.navigation_market:

                    return true;

                case R.id.navigation_achievements:

                    return true;

                case R.id.navigation_profile:
                    Intent i = new Intent(LevelsScreen.this, ProfileScreen.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }
    };

}

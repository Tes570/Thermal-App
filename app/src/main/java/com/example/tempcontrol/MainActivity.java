package com.example.tempcontrol;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.tempcontrol.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {


    //The database where we get the current room temp
    private DatabaseReference GetValues;

    //the database where we post the room settins
    private DatabaseReference PostValues;

    private int RoomNum = 3;
    int testingVal = 0;


    //This is where the temp of the rooms is saved. It will load whenever there is a change.
    Vector GetTemp;

    //This is where you set the variables of the temp. From the app, replace the variable in
    //firebase with the one found in the app. Don't get app variable, except at the begining/ onCreate.
    Vector PostTemp;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        //testing

        GetTemp = new Vector();
        PostTemp = new Vector();

        GetValues = FirebaseDatabase.getInstance().getReference("Demo").child("Room Temp" );
        PostValues = FirebaseDatabase.getInstance().getReference("Demo").child("Room Setting");


        /*
        //This is to set the variables in firebase. Run this to reset the room temp.
        int tes = 70;
        for(int i = 0; i < RoomNum; ++i)
        {
            GetValues.child("room" + String.valueOf(i)).setValue(tes);
            PostValues.child("room" + String.valueOf(i)).setValue(tes);
        }
        //*/

        //How to get the variable from firebase? Look at onStart


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Stemp = "";

                Stemp += "GetTemp: ";

                for(int i = 0; i < RoomNum; ++i)
                {
                    Stemp += String.valueOf(GetTemp.elementAt(i)) + " ";
                }

                Stemp += "PostTemp: ";

                for(int i = 0; i < RoomNum; ++i)
                {
                    Stemp += String.valueOf(PostTemp.elementAt(i)) + " ";
                }


                Snackbar.make(view, Stemp, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }




    @Override
    public void onStart() {
        super.onStart();

        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener GetListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                if(GetTemp.size() <= 1)
                {
                    for(int i = 0; i < RoomNum; ++i)
                    {
                        GetTemp.add(Integer.parseInt(dataSnapshot.child("room" + String.valueOf(i)).getValue().toString()));
                        Log.d("myTag" + String.valueOf(i), String.valueOf(GetTemp.elementAt(i)));
                        //Log.d("Tes" + String.valueOf(i), String.valueOf(GetTemp.size()));

                    }
                }
                else
                {
                    for(int i = 0; i < GetTemp.size(); ++i)
                    {
                        GetTemp.setElementAt((Integer.parseInt(dataSnapshot.child("room" + String.valueOf(i)).getValue().toString())), i);
                        Log.d("myTag" + String.valueOf(i), String.valueOf(GetTemp.elementAt(i)));
                    }
                }


                //temp = Integer.parseInt(dataSnapshot.child("room1").getValue().toString());
                //Log.d("myTag", String.valueOf(temp));
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // [END_EXCLUDE]
            }
        };

        GetValues.addValueEventListener(GetListener);


        ValueEventListener SetListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                if(PostTemp.size() <= 1) {
                    for (int i = 0; i < RoomNum; ++i) {
                        PostTemp.add(Integer.parseInt(dataSnapshot.child("room" + String.valueOf(i)).getValue().toString()));
                        Log.d("setTag" + String.valueOf(i), String.valueOf(PostTemp.elementAt(i)));
                    }
                }
                else
                {
                    for(int i = 0; i < RoomNum; ++i)
                    {
                        PostTemp.setElementAt((Integer.parseInt(dataSnapshot.child("room" + String.valueOf(i)).getValue().toString())), i);
                        Log.d("myTag" + String.valueOf(i), String.valueOf(PostTemp.elementAt(i)));
                    }
                }

                //temp = Integer.parseInt(dataSnapshot.child("room1").getValue().toString());
                //Log.d("myTag", String.valueOf(temp));
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // [END_EXCLUDE]
            }
        };

        //PostValues.addListenerForSingleValueEvent(SetListener);
        PostValues.addValueEventListener(SetListener);


        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        //mPostListener = postListener;

    }







}
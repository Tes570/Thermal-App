package com.example.tempcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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

    //This is where the temp of the rooms is saved. It will load whenever there is a change.
    Vector GetTemp;

    //This is where you set the variables of the temp. From the app, replace the variable in
    //firebase with the one found in the app. Don't get app variable, except at the begining/ onCreate.
    Vector PostTemp;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.view_pager);


        Button Edit1 = (Button) findViewById(R.id.Bedroom1);

        Edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("TempApp", "Button 1 being clicked!");
//                Toast.makeText(getApplicationContext(), "Edit Temp for B1", Toast.LENGTH_SHORT).show();
                moveToActivityButton();
            }
        });

        Button Edit2 = (Button) findViewById(R.id.Bedroom2);

        Edit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("TempApp", "Button 2 being clicked!");
//                Toast.makeText(getApplicationContext(), "Edit Temp for B2", Toast.LENGTH_SHORT).show();
                moveToActivityButton();
            }
        });

        Button Edit3 = (Button) findViewById(R.id.Bedroom3);

        Edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("TempApp", "Button 3 being clicked!");
//                Toast.makeText(getApplicationContext(), "Edit Temp for B3", Toast.LENGTH_SHORT).show();
                moveToActivityButton();
            }
        });

        //testing

        GetTemp = new Vector();
        PostTemp = new Vector();

        GetValues = FirebaseDatabase.getInstance().getReference("Demo").child("Room Temp" );
        PostValues = FirebaseDatabase.getInstance().getReference("Demo").child("Room Setting");


        //This is to set the variables in firebase. Run this to reset the room temp.
        int tes = 70;
        for(int i = 0; i < RoomNum; ++i)
        {
            GetValues.child("room" + String.valueOf(i)).setValue(tes);
            PostValues.child("room" + String.valueOf(i)).setValue(tes);
        }


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
                        GetTemp.add(dataSnapshot.child("room" + String.valueOf(i)).getValue());
                        Log.d("myTag" + String.valueOf(i), String.valueOf(GetTemp.elementAt(i)));
                    }
                }
                else
                {
                    for(int i = 0; i < GetTemp.size(); ++i)
                    {
                        GetTemp.setElementAt((dataSnapshot.child("room" + String.valueOf(i)).getValue()), i);
                        Log.d("myTag" + String.valueOf(i), String.valueOf(GetTemp.elementAt(i)));
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        GetValues.addValueEventListener(GetListener);


        ValueEventListener SetListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI

                if(PostTemp.size() <= 1) {
                    for (int i = 0; i < RoomNum; ++i) {
                        PostTemp.add(dataSnapshot.child("room" + String.valueOf(i)).getValue());
                        Log.d("setTag" + String.valueOf(i), String.valueOf(PostTemp.elementAt(i)));
                    }
                }
                else
                {
                    for(int i = 0; i < RoomNum; ++i)
                    {
                        PostTemp.setElementAt((dataSnapshot.child("room" + String.valueOf(i)).getValue()), i);
                        Log.d("myTag" + String.valueOf(i), String.valueOf(PostTemp.elementAt(i)));
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //PostValues.addListenerForSingleValueEvent(SetListener);
        PostValues.addValueEventListener(SetListener);


        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        //mPostListener = postListener;

    }


    private void moveToActivityButton() {
        Intent nextActivity = new Intent(MainActivity.this, ButtonPressActivity.class);
        startActivity(nextActivity);
    }




}

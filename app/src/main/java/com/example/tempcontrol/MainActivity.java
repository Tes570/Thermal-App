package com.example.tempcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

//import com.example.tempcontrol.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
//import com.google.android.material.tabs.TabLayout;
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

    //variable for Text view
    private TextView retriveB1, retriveB2, retriveB3;

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
        //SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        //viewPager.setAdapter(sectionsPagerAdapter);
        //TabLayout tabs = findViewById(R.id.tabs);
        //tabs.setupWithViewPager(viewPager);
        //FloatingActionButton fab = findViewById(R.id.fab);

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
                moveToActivityButton2();
            }
        });

        Button Edit3 = (Button) findViewById(R.id.Bedroom3);

        Edit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("TempApp", "Button 3 being clicked!");
//                Toast.makeText(getApplicationContext(), "Edit Temp for B3", Toast.LENGTH_SHORT).show();
                moveToActivityButton3();
            }
        });

        //testing

        GetTemp = new Vector();
        PostTemp = new Vector();

        GetValues = FirebaseDatabase.getInstance().getReference("Demo").child("Room Temp" );
        PostValues = FirebaseDatabase.getInstance().getReference("Demo").child("Room Setting");

        retriveB1 = (TextView)findViewById(R.id.B1Current);
        retriveB2 = (TextView)findViewById(R.id.B2Current);
        retriveB3 = (TextView)findViewById(R.id.B3Current);


        //How to get the variable from firebase? Look at onStart


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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

                String room0=dataSnapshot.child("room0").getValue().toString();
                String room1=dataSnapshot.child("room1").getValue().toString();
                String room2=dataSnapshot.child("room2").getValue().toString();
                retriveB1.setText(room0);
                if(GetValues.elementAt(0) > PostValues.elementAt(0)) {
                    ImageView imageView = (ImageView) findViewById(R.id.image1);
                    imageView.setImageResource(R.drawable.ac_on);
                }
                else {
                    ImageView imageView = (ImageView) findViewById(R.id.image1);
                    imageView.setImageResource(R.drawable.ac_off);
                }

                retriveB2.setText(room1);
                if(70 > 60) {
                    ImageView imageView = (ImageView) findViewById(R.id.image2);
                    imageView.setImageResource(R.drawable.ac_on);
                }
                else {
                    ImageView imageView = (ImageView) findViewById(R.id.image2);
                    imageView.setImageResource(R.drawable.ac_off);
                }

                retriveB3.setText(room2);
                if(70 > 60) {
                    ImageView imageView = (ImageView) findViewById(R.id.image3);
                    imageView.setImageResource(R.drawable.ac_on);
                }
                else {
                    ImageView imageView = (ImageView) findViewById(R.id.image3);
                    imageView.setImageResource(R.drawable.ac_off);
                }

                //temp = Integer.parseInt(dataSnapshot.child("room1").getValue().toString());
                //Log.d("myTag", String.valueOf(temp));
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
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

    private void moveToActivityButton2() {
        Intent nextActivity = new Intent(MainActivity.this, Button2PressActivity.class);
        startActivity(nextActivity);
    }

    private void moveToActivityButton3() {
        Intent nextActivity = new Intent(MainActivity.this, Button3PressActivity.class);
        startActivity(nextActivity);
    }
}

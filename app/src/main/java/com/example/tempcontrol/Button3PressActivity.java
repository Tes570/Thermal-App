package com.example.tempcontrol;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

//import com.example.tempcontrol.ui.main.SectionsPagerAdapter;
//import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Button3PressActivity extends AppCompatActivity {

    EditText DesiredTempEdt;
    Button sendData;

    //FirebaseDatabase firebaseDatabase;

    DatabaseReference databaseReference;

    Demo demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button3_press);

        DesiredTempEdt = (EditText) findViewById(R.id.idDesiredTemp);

        sendData = (Button) findViewById(R.id.idSendData);

        demo = new Demo();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Demo").child("Room Setting");

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demo.setTemp(DesiredTempEdt.getText().toString().trim());

                databaseReference.child("room2").setValue(demo);
                Toast.makeText(Button3PressActivity.this, "desired temp added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
//    private EditText DesiredTempEdt;
//    private Button sendData;
//
//    FirebaseDatabase firebaseDatabase;
//
//    DatabaseReference databaseReference;
//
//    DesiredTemp desiredTemp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_button3_press);
//
//        DesiredTempEdt = findViewById(R.id.idDesiredTemp);
//
//        firebaseDatabase = FirebaseDatabase.getInstance();
//
//        databaseReference = firebaseDatabase.getReference("DesiredTemp");
//
//        desiredTemp = new DesiredTemp();
//
//        sendData = findViewById(R.id.idSendData);
//
//        sendData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String temp = DesiredTempEdt.getText().toString();
//
//                if(TextUtils.isEmpty(temp)) {
//                    Toast.makeText(Button3PressActivity.this, "Please add desired temperature.", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    addDatatoFirebase(temp);
//                }
//            }
//        });
//    }
//
//    private void addDatatoFirebase(String temp) {
//        desiredTemp.setDesiredTemp(temp);
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseReference.setValue(desiredTemp);
//                Toast.makeText(Button3PressActivity.this, "temp added", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Button3PressActivity.this, "Fail to add temp " + error, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}

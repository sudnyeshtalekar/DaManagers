package com.sud.parallax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView textViewdam;
    private TextView textViewtemp;
    private TextView textViewhum;
    private TextView textViewflow;
    private ImageView pointer1;
    private ImageView pointer2;
    private ImageView pointer3;
    private ImageView pointer4;
    private ImageView pointer5;
    private TextView alert;
    DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewdam = findViewById(R.id.textViewdam);
        textViewtemp = findViewById(R.id.textViewtemp);
        textViewhum = findViewById(R.id.textViewhum);
        textViewflow = findViewById(R.id.textViewflow);
        alert = findViewById(R.id.alert);

        pointer1 = findViewById(R.id.pointer1);
        pointer2 = findViewById(R.id.pointer2);
        pointer3 = findViewById(R.id.pointer3);
        pointer4 = findViewById(R.id.pointer4);
        pointer5 = findViewById(R.id.pointer5);

        final MediaPlayer alarm = MediaPlayer.create(this, R.raw.ala);

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, instruc.class);
                startActivity(intent);

                alarm.start();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("Sensors");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String FlowSpeed = dataSnapshot.child("Inflow").getValue(String.class);
                String Humidity = dataSnapshot.child("IFml").getValue(String.class);
                String Temperature = dataSnapshot.child("TFL").getValue(String.class);
                String Waterlevel = dataSnapshot.child("Water").getValue(String.class);
                String level = dataSnapshot.child("level").getValue(String.class);


                //boolean b = Boolean.parseBoolean(level);


                Log.d("TAG", "Value is: " + FlowSpeed);
                Log.d("TAG", "Value is: " + Humidity);
                Log.d("TAG", "Value is: " + Temperature);
                Log.d("TAG", "Value is: " + Waterlevel);

                textViewdam.setText(Waterlevel + "");
                textViewtemp.setText(Temperature + "");
                textViewhum.setText(Humidity + "");
                textViewflow.setText(FlowSpeed + "");
                alert.setText(level + "");

                if (level == "4"){
                    pointer4.setVisibility(View.VISIBLE);
                    pointer3.setVisibility(View.INVISIBLE);
                    pointer2.setVisibility(View.INVISIBLE);
                    pointer1.setVisibility(View.INVISIBLE);
                }
                else if(alert.getText().toString() == "3"){
                    pointer3.setVisibility(View.VISIBLE);
                    pointer2.setVisibility(View.INVISIBLE);
                    pointer1.setVisibility(View.INVISIBLE);
                    pointer4.setVisibility(View.INVISIBLE);

                }
                else if(alert.getText().toString() == "2"){
                    pointer2.setVisibility(View.VISIBLE);
                    pointer4.setVisibility(View.INVISIBLE);
                    pointer3.setVisibility(View.INVISIBLE);
                    pointer1.setVisibility(View.INVISIBLE);
                }
                else{pointer1.setVisibility(View.VISIBLE);
                    pointer2.setVisibility(View.INVISIBLE);
                     pointer4.setVisibility(View.INVISIBLE);
                        pointer3.setVisibility(View.INVISIBLE);}

               // Log.d("TAG", "Value is: " + alert.getText().toString());



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }

}





package com.sud.parallax;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class instruc extends AppCompatActivity {


    private ImageView instbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruc);

        instbut=findViewById(R.id.instbut);

        instbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(instruc.this, Safeplaces.class);
                startActivity(intent);
            }
        });


    }
}

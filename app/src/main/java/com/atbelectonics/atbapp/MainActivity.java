package com.atbelectonics.atbapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.atbelectonics.atbapp.ui.activities.AddDeviceActivity;
import com.atbelectonics.atbapp.ui.activities.ControllerActivity;
import com.atbelectonics.atbapp.ui.activities.StaticAddressActivity;

public class MainActivity extends AppCompatActivity {
    Button btn_dinamic, btn_static;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_dinamic =findViewById(R.id.btn_dinamic_id);
        btn_static = findViewById(R.id.btn_static_id);

        //работа со статическим адресом
        btn_static.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), StaticAddressActivity.class);
                startActivity(i);
            }
        });

        //работа с динамическим (mdns адресом)
        btn_dinamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddDeviceActivity.class);
                startActivity(i);
            }
        });
    }
}
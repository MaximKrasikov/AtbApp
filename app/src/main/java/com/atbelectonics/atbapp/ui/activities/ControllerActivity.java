package com.atbelectonics.atbapp.ui.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.atbelectonics.atbapp.R;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//for api test connect
//with static address activity
public class ControllerActivity extends AppCompatActivity {
    public static String address="";
    public static String ip="";
    public static int port=0;
    public static String message="";
    Button fist_open, fist_close;
    TextView tv_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        if (getIntent().hasExtra("address")){
            address = getIntent().getStringExtra("address");
            String list[] = address.split(":");
            ip = list[0];
            port = Integer.valueOf(list[1]);
        }
        tv_address = findViewById(R.id.address_id);
        fist_open = findViewById(R.id.f_open_id);
        fist_close = findViewById(R.id.f_close_id);

        tv_address.setText(address);

        fist_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "o";
                SockedTransfer socket = new SockedTransfer();
                socket.execute();
            }
        });

        fist_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = "c";
                SockedTransfer socket = new SockedTransfer();
                socket.execute();
            }
        });

    }

    public void exit()
    {
        System.exit(0);
    }

    public class SockedTransfer extends AsyncTask<Void,Void,Void>
    {
        Socket socket;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                InetAddress inet = InetAddress.getByName(ip);
                socket = new java.net.Socket(inet, port);
                DataOutputStream stream =  new DataOutputStream(socket.getOutputStream());
                stream.writeBytes(message);
                stream.close();
                socket.close();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

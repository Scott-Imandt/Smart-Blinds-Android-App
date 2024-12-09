package com.example.blindscontroller;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    BtConnection bluetooth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView statustxt = (TextView) findViewById(R.id.connectedTextView);
        Button Onbtn = (Button) findViewById(R.id.Onbtn);
        Button Offbtn = (Button) findViewById(R.id.Offbtn);
        Button Disconnectbtn = (Button) findViewById(R.id.Disconnectbtn);
        Button Connectbtn = (Button) findViewById(R.id.Connectbtn);
        Button Configurebtn = (Button) findViewById(R.id.configureBtn);

        ToggleButton Ambiantbtn = (ToggleButton) findViewById(R.id.toggleAmbiantButton);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);


        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(progress > 0) {

                            try {
                                bluetooth.variableTurnOn(progress);

                            }catch (Exception E){
                                System.out.println(E.toString());
                                statustxt.setText("Unable to Compute");
                                statustxt.setTextColor(Color.RED);
                            }
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        //bluetooth.turnOn();

                        // not perfect cause delay with bar butstops crashing arduino
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //bluetooth.turnOff();

                    }

                }
        );

        Configurebtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(bluetooth != null){
                    bluetooth.btclose();
                }

                openConfigureActivity();
            }
        });
        

        Onbtn.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {

               try {
                   bluetooth.turnOn();
               }catch (Exception E){
                   System.out.println(E.toString());
                   statustxt.setText("Unable to Compute");
                   statustxt.setTextColor(Color.RED);
               }

           }
       });

        Ambiantbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                try {
                    if (isChecked){
                        bluetooth.variableTurnOn(231);
                    } else {
                        bluetooth.variableTurnOn(0);
                    }
                }catch (Exception E){
                    System.out.println(E.toString());
                    statustxt.setText("Unable to Compute");
                    statustxt.setTextColor(Color.RED);
                }

            }
        });

        Disconnectbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    bluetooth.btclose();
                    statustxt.setText("Not Connected");
                    statustxt.setTextColor(Color.RED);

                }catch(Exception E){
                    System.out.println(E.toString());
                    statustxt.setText("Unable to Compute");
                    statustxt.setTextColor(Color.RED);
                }


            }
        });

        Offbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    bluetooth.turnOff();
                }catch(Exception E){
                    System.out.println(E.toString());
                    statustxt.setText("Unable to Compute");
                    statustxt.setTextColor(Color.RED);
                }

            }
        });

        Connectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bluetooth = BtConnection.makeConnection();

                if(bluetooth.getBtSocketStatus()){
                    statustxt.setText("Connected");
                    statustxt.setTextColor(Color.GREEN);
                }

            }
        });

    }

    private void openConfigureActivity() {
        Intent intent = new Intent(this, ConfigureActivity.class);
        startActivity(intent);
    }
}
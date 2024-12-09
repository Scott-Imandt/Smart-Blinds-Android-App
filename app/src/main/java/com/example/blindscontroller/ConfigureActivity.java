package com.example.blindscontroller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;


public class ConfigureActivity extends AppCompatActivity {

    BtConnection bluetooth = null;
    //String[] temp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

    Button backButton = (Button) findViewById(R.id.backButton);
    Button setButton = (Button) findViewById(R.id.setButton);

    final Button refreshButton = (Button) findViewById(R.id.refreshButton);
    Button lplusButton = (Button) findViewById(R.id.lplusButton);
    Button lminusButton = (Button) findViewById(R.id.lminusButton);
    Button rplusButton = (Button) findViewById(R.id.rplusButton);
    Button rminusButton = (Button) findViewById(R.id.rminusButton);


    final TextView rvalueTextView = (TextView) findViewById(R.id.rvalueTextView);
    final TextView lvalueTextView = (TextView) findViewById(R.id.lvalueTextView);
    final ToggleButton minmaxToggleButton = (ToggleButton) findViewById(R.id.minmaxToggleButton);
    final TextView lvalueStoredTextView = (TextView) findViewById(R.id.lvalueStoredTextView);
    final TextView rvalueStoredTextView = (TextView) findViewById(R.id.rvalueStoredTextView);

    refreshButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int maxMin = 2;

            if(minmaxToggleButton.getText().equals("MIN")){
                maxMin = 0;
            }
            if(minmaxToggleButton.getText().equals("MAX")){
                maxMin = 1;
            }

            bluetooth = BtConnection.makeConnection();

            bluetooth.sendData(2,1,maxMin,90);

            String[] temp1 = bluetooth.recieveData();

            lvalueStoredTextView.setText(temp1[0]);
            rvalueStoredTextView.setText(temp1[1]);

            if(bluetooth != null){
                bluetooth.btclose();
                bluetooth = null;
            }


        }
    });


    backButton.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v){
            if(bluetooth != null){
                bluetooth.btclose();
            }
                openMainActivity();

        }
    });

    setButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int lValue = Integer.parseInt(String.valueOf(lvalueTextView.getText()));
            int rValue = Integer.parseInt(String.valueOf(rvalueTextView.getText()));
            int maxMin = 2;

            if(minmaxToggleButton.getText().equals("MIN")){
                maxMin = 0;
            }
            if(minmaxToggleButton.getText().equals("MAX")){
                maxMin = 1;
            }

            bluetooth = BtConnection.makeConnection();

            bluetooth.sendData(1,0,maxMin,lValue);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            bluetooth.sendData(1,1,maxMin,rValue);

            if(bluetooth != null){
                bluetooth.btclose();
                bluetooth = null;
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            refreshButton.performClick();

        }
    });

    minmaxToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                lvalueTextView.setText("90");
                rvalueTextView.setText("90");
            } else {
                lvalueTextView.setText("1");
                rvalueTextView.setText("1");
            }
        }
    });



    lminusButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int temp = Integer.parseInt(String.valueOf(lvalueTextView.getText()));
            temp = temp -1;
            lvalueTextView.setText(String.valueOf(temp));

        }
    });

    lplusButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int temp = Integer.parseInt(String.valueOf(lvalueTextView.getText()));
            temp = temp +1;
            lvalueTextView.setText(String.valueOf(temp));

        }
    });

    rminusButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int temp = Integer.parseInt(String.valueOf(rvalueTextView.getText()));
            temp = temp -1;
            rvalueTextView.setText(String.valueOf(temp));
        }
    });

    rplusButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int temp = Integer.parseInt(String.valueOf(rvalueTextView.getText()));
            temp = temp +1;
            rvalueTextView.setText(String.valueOf(temp));
        }
    });


    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

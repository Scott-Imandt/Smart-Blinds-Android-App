package com.example.blindscontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BtConnection{

    static private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    static private BluetoothDevice btDevice = btAdapter.getRemoteDevice("00:14:03:06:0C:64");
    static private BluetoothSocket btSocket = null;
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

public BtConnection(){

}


public static BtConnection makeConnection() {
    int counter = 0;
    BtConnection btConnection = null;
    do {
        try {
            btConnection = new BtConnection();

            btSocket = btDevice.createRfcommSocketToServiceRecord(mUUID);
            System.out.println(btSocket);
            btSocket.connect();
            System.out.println(btSocket.isConnected());

        } catch (IOException e) {
            System.out.println("Unable to create socket");
            e.printStackTrace();
        }
        counter++;
    } while (!btSocket.isConnected() && counter < 1);

    return btConnection;
}

public boolean getBtSocketStatus(){
    return btSocket.isConnected();
}

public void variableTurnOn(int value){
    int[] message = {0,value, 999};

    try {
        OutputStream outputStream = btSocket.getOutputStream();
        for(int i =0; i< message.length; i++){
            outputStream.write(message[i]);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}


public void turnOn(){
// send an array of numbers individually 999 = finish message
    int[] message = {0,100, 999};

    try {
        OutputStream outputStream = btSocket.getOutputStream();
        //outputStream.write(100);
        for(int i =0; i< message.length; i++){
            outputStream.write(message[i]);
        }


    } catch (IOException e) {
        e.printStackTrace();
    }
}
public void turnOff(){
    int[] message = {0,1, 999};

    try {
        OutputStream outputStream = btSocket.getOutputStream();
        for(int i =0; i< message.length; i++){
            outputStream.write(message[i]);
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public String[] recieveData(){

    String[] data = new String[2];
    int temp = 0;
    int i=0;

    try {
        InputStream inputStream = btSocket.getInputStream();
        inputStream.skip(inputStream.available());

        while(inputStream.available() >0 || i != 2){

            temp = inputStream.read();

            if(temp == 9){
                System.out.println("THIS IS THE SYSOUT INFO:\t" +data[i]);
                i++;
            }
            else{
                if(data[i] != null){
                    data[i] = data[i] + ((char) temp);
                }
                else{
                    data[i] = String.valueOf((char) temp);
                }
            }
        }

    }catch (Exception e){
        System.out.println(e);
    }

    return data;
}


    public void sendData(int function, int servoNumber, int maxOrMin, int degreeValue){
        int[] message = {function, servoNumber, maxOrMin, degreeValue, 999};

        try {
            OutputStream outputStream = btSocket.getOutputStream();
            for(int i =0; i< message.length; i++){
                outputStream.write(message[i]);
                System.out.println("Sent data: " + message[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


public void btclose(){
    try {
        btSocket.close();
        System.out.println(btSocket.isConnected());
    } catch (IOException e) {
        e.printStackTrace();
    }
}



}

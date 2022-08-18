package com.example.remote_control;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGatt GATT;
    private String deviceaddress=("2C:AB:33:54:98:A5");
    public static final UUID RX_SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID RX_CHAR_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    public static final UUID TX_CHAR_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);

        final BluetoothManager mbluetoothManager=(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter=mbluetoothManager.getAdapter();
        connect();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BluetoothGattCharacteristic characteristic = mBluetoothGatt.getService(RX_SERVICE_UUID).getCharacteristic(RX_CHAR_UUID);
                byte[] byteArrray = "1".getBytes();
                characteristic.setValue(byteArrray);
                boolean stat = GATT.writeCharacteristic(characteristic);
                Log.e("GATT", "Send :"+stat);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BluetoothGattCharacteristic characteristic = mBluetoothGatt.getService(RX_SERVICE_UUID).getCharacteristic(RX_CHAR_UUID);
                byte[] byteArrray = "2".getBytes();
                characteristic.setValue(byteArrray);
                boolean stat = GATT.writeCharacteristic(characteristic);
                Log.e("GATT", "Send :"+stat);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BluetoothGattCharacteristic characteristic = mBluetoothGatt.getService(RX_SERVICE_UUID).getCharacteristic(RX_CHAR_UUID);
                byte[] byteArrray = "3".getBytes();
                characteristic.setValue(byteArrray);
                boolean stat = GATT.writeCharacteristic(characteristic);
                Log.e("GATT", "Send :"+stat);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BluetoothGattCharacteristic characteristic = mBluetoothGatt.getService(RX_SERVICE_UUID).getCharacteristic(RX_CHAR_UUID);
                byte[] byteArrray = "4".getBytes();
                characteristic.setValue(byteArrray);
                boolean stat = GATT.writeCharacteristic(characteristic);
                Log.e("GATT", "Send :"+stat);
            }
        });



    }

    public void connect(){
        //connect to given device addr
        Log.e("GATT", "=== start ===");
        BluetoothDevice device=mBluetoothAdapter.getRemoteDevice(deviceaddress);
        mBluetoothGatt=device.connectGatt(this, false, mGattCallback);
    }

    //get callbacks when something changes
    private final BluetoothGattCallback mGattCallback=new BluetoothGattCallback() {
        //public BluetoothGatt GATT;
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (BluetoothGatt.GATT_SUCCESS == status) {
                Log.e("GATT", "Connected");
                gatt.discoverServices();
                GATT = gatt;
            } else {
                Log.e("GATT", "Status :" + status);
                gatt.close();
                connect();
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.e("GATT", "msg :" + status);

            sendmsg(gatt , "10");
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            if (BluetoothGatt.GATT_SUCCESS == status) {
                Log.d("GATT", "enter success");
            }
        }

        //func to send msg to lego spike
        public void sendmsg(BluetoothGatt gatt , String msg){
            BluetoothGattCharacteristic characteristic = mBluetoothGatt.getService(RX_SERVICE_UUID).getCharacteristic(RX_CHAR_UUID);
            byte[] byteArrray = msg.getBytes();
            characteristic.setValue(byteArrray);
            boolean stat = GATT.writeCharacteristic(characteristic);
            Log.e("GATT", "Send :" + stat);
        }
    };
}
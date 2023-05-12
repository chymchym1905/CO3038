package com.example.mobileiotapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import java.nio.charset.Charset;

public class MainActivity extends AppCompatActivity {
    MQTTHelper mqttHelper;
    TextView temp,light,humid;
    LabeledSwitch btnLIGHT, btnPUMP;
    public Data data;
    public static  final String HUMID_TEXT = "com.example.mobileiotapp.humid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = findViewById(R.id.temperature);
        light = findViewById(R.id.light);
        humid = findViewById(R.id.humid);
        btnLIGHT = findViewById(R.id.nut1);
        btnPUMP = findViewById(R.id.nut2);

        btnPUMP.setOnToggledListener(new OnToggledListener()
        {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn)
            {
                if (isOn == true)
                {
                    sendDataMQTT("chymchym1905/feeds/nut2","1");
                }
                else
                {
                    sendDataMQTT("chymchym1905/feeds/nut2","0");
                }
            }
        });

        temp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, tempgraph.class);
//            intent.putExtra("tempdata", data.getTemp());
            startActivity(intent);
        });

        light.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, lightgraph.class);
//            intent.putExtra("lightdata", data.getLight());
            startActivity(intent);
        });


        humid.setOnClickListener(v -> openhumidgraph());


        btnLIGHT.setOnToggledListener(new OnToggledListener()
        {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn)
            {
                if (isOn == true)
                {
                    sendDataMQTT("chymchym1905/feeds/nut1","1");
                }
                else
                {
                    sendDataMQTT("chymchym1905/feeds/nut1","0");
                }
            }
        });
        startMQTT();

    }

    public void openhumidgraph(){
        Intent intent = new Intent(this, humidgraph.class);
//        Log.d("TEST", data.getHumid().toString());
//        intent.putExtra(HUMID_TEXT, data.getHumid());
        startActivity(intent);
    }

    public void sendDataMQTT(String topic, String value)
    {
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try
        {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }
        catch (MqttException e){
        }
    }


    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST",topic + "***" + message.toString());
                if (topic.contains("cambien1")){
                    temp.setText(message.toString()+ "°C");
                    data.setTemp(data.appendToArray(data.getTemp(), Integer.parseInt(message.toString())));
                }if(topic.contains("cambien2")){
                    light.setText(message.toString()+ " LUX");
                    data.setLight(data.appendToArray(data.getLight(), Integer.parseInt(message.toString())));
                }if(topic.contains("cambien3")){
                    humid.setText(message.toString()+ "%");
                    data.setHumid(data.appendToArray(data.getHumid(), Integer.parseInt(message.toString())));
                }if (topic.contains("nut1"))
                {
                    if (message.toString().equals("1")){
                        btnLIGHT.setOn(true);
                    }
                    else{
                        btnLIGHT.setOn(false);
                    }
                }if (topic.contains("nut2"))
                {
                    if (message.toString().equals("1")){
                        btnPUMP.setOn(true);
                    }
                    else{
                        btnPUMP.setOn(false);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
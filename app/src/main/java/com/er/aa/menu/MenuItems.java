package com.er.aa.menu;

import android.content.Context;

import com.er.aa.R;
import com.er.aa.smartalarm.SmartAlarmActivity;
import com.er.aa.smsreceiver.SmsActivity;
import com.er.aa.weather.WeatherActivity;
import com.er.aa.whatsappscheduler.WhatsappSchedulerActivity;

import java.util.ArrayList;
import java.util.List;

public class MenuItems {

    private Context context;
    private List<Item> itemList;

    public MenuItems(Context context){
        this.itemList = new ArrayList<>();
        this.context = context;
        initaliseMenuList();
    }

    public List<Item> getMenuItems() {
        return this.itemList;
    }

    private int getItemListLength(){
        return this.itemList.size();
    }

    private void initaliseMenuList(){
        if (this.itemList != null){
            this.itemList.clear();
            itemList.add(new Item(R.drawable.whatsapp, this.context.getString(R.string.feature_whatsapp), WhatsappSchedulerActivity.class));
            itemList.add(new Item(R.drawable.gate, this.context.getString(R.string.feature_gate_opener) ));
            itemList.add(new Item(R.drawable.weather_sunny, "not fetched temperature", WeatherActivity.class));
            itemList.add(new Item(R.drawable.sms2fa, this.context.getString(R.string.feature_2fa), SmsActivity.class));
            itemList.add(new Item(R.drawable.alarm_clock, this.context.getString(R.string.feature_alarm), SmartAlarmActivity.class));
        }
    }

    public void removeAndReplaceItem(int itemIndex, Item newItem){
        if ( itemIndex >= 0 && itemIndex < this.itemList.size()) {
            if (newItem != null){
                this.itemList.remove(itemIndex);
                this.itemList.add(itemIndex, newItem);
            }
        }
    }



}

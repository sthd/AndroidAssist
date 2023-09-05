package com.er.aa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.er.aa.menu.Item;
import com.er.aa.menu.MenuItems;
import com.er.aa.menu.MyAdapter;
import com.er.aa.smsreceiver.SmsActivity;
import com.er.aa.weather.WeatherViewModel;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Item> itemList;
    private Handler handler;
    private Runnable layoutChangeRunnable;

    private static final long LAYOUT_CHANGE_DELAY = 6000; // 6 seconds
    private int numlayouts = 0;

    private Button button;
    private MenuItems menuItems;
    static final String TAG = "mainactivity";
    private WeatherViewModel viewModel;
    AtomicReference<String> temperature_view = new AtomicReference<>("");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        menuItems = new MenuItems(getApplicationContext());
        //worked
        //AtomicReference<String> temperature_view = new AtomicReference<>("");
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        String apiUrl = getString(R.string.STANMORE_API_URL)  +"&q=ha74sg";
        viewModel.fetchWeatherData(apiUrl);
        viewModel.getWeatherData().observe(this, weatherInfo -> {
            // Update UI with weatherInfo data
            //String temperatureMessage = "Temperature: " + weatherInfo.getTemperatureC() + " °C";

            temperature_view.set(weatherInfo.getTemperatureC() + " °C");
            //Item newItem = new Item(R.drawable.sun,  temperature_view.toString());
            //Toast.makeText(this, temperature_view.toString(), Toast.LENGTH_SHORT).show();
            //menuItems.removeAndReplaceItem(2, newItem);
            adapter.notifyDataSetChanged();
            adapter.updateData(menuItems.getMenuItems());
            recyclerView.setAdapter(adapter);

        });


        viewModel.getError().observe(this, errorMessage -> {
            // Handle error
        });

        viewModel.fetchWeatherData(apiUrl);
        //fetchTemperatureData(); // causes crush
        // Initialize the RecyclerView and its layout manager
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Thread.sleep(2500);


        // Create the adapter and set it to the RecyclerView
        adapter = new MyAdapter(menuItems.getMenuItems(), this);
        recyclerView.setAdapter(adapter);

        handler = new Handler();
        layoutChangeRunnable = new Runnable() {
            @Override
            public void run() {
                numlayouts = 5;
                if (numlayouts == 0) {
                    Item newItem = new Item(R.drawable.elior,  "layoutNumber 0, index 0");
                    menuItems.removeAndReplaceItem(0, newItem);
                    adapter.notifyDataSetChanged();
                    adapter.updateData(menuItems.getMenuItems());
                    recyclerView.setAdapter(adapter);
                    //fetchTemperatureData();
                    numlayouts = 1;

                } else if (numlayouts == 1){
                    Item newItem = new Item(R.drawable.weather_snow,  "layoutNumber 1, index 1");
                    menuItems.removeAndReplaceItem(1, newItem);
                    adapter.notifyDataSetChanged();
                    adapter.updateData(menuItems.getMenuItems());
                    recyclerView.setAdapter(adapter);
                    numlayouts = 2;

                } else if (numlayouts == 2){
                    Item newItem = new Item(R.drawable.sun,  "layoutNumber 2, index 2");
                    menuItems.removeAndReplaceItem(2, newItem);
                    adapter.notifyDataSetChanged();
                    adapter.updateData(menuItems.getMenuItems());
                    recyclerView.setAdapter(adapter);

                    numlayouts = 3;

                } else if (numlayouts == 3){
                    Item newItem = new Item(R.drawable.weather_partly_cloudy_rain,  "layoutNumber 3, index 3");
                    menuItems.removeAndReplaceItem(3, newItem);
                    adapter.notifyDataSetChanged();
                    adapter.updateData(menuItems.getMenuItems());
                    recyclerView.setAdapter(adapter);

                    numlayouts = 0;
                }

                handler.postDelayed(layoutChangeRunnable, LAYOUT_CHANGE_DELAY); // Reschedule the layout change
            }
        };

        handler.postDelayed(layoutChangeRunnable, LAYOUT_CHANGE_DELAY); // Start the initial layout change

    }


    private void fetchTemperatureData() {
        String apiUrl = getString(R.string.STANMORE_API_URL) +"&q=ha74sg";
        viewModel.fetchWeatherData(apiUrl);

        viewModel.getWeatherData().observe(this, weatherInfo -> {
            // Update UI with weatherInfo data
            String temperatureMessage = weatherInfo.getTemperatureC() + " °C";
            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter.notifyDataSetChanged();
            adapter.updateData(itemList);
            recyclerView.setAdapter(adapter);
        });

        viewModel.getError().observe(this, errorMessage -> {
            // Handle error
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(layoutChangeRunnable); // Remove the layout change callbacks to avoid memory leaks
    }


}

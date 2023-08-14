package com.er.aa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.er.aa.menu.Item;
import com.er.aa.menu.MyAdapter;
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

    private boolean isLayout1 = true; // Initial layout flag
    private static final long LAYOUT_CHANGE_DELAY = 6000; // 6 seconds
    private int numlayouts = 0;

    private Button button;

    static final String TAG = "mainactivity";
    private WeatherViewModel viewModel;
    AtomicReference<String> temperature_view = new AtomicReference<>("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        //worked
        //AtomicReference<String> temperature_view = new AtomicReference<>("");
        viewModel = new ViewModelProvider(this).get(WeatherViewModel.class);

        String apiUrl = getString(R.string.STANMORE_API_URL)  +"&q=ha74sg";

        viewModel.getWeatherData().observe(this, weatherInfo -> {
            // Update UI with weatherInfo data
            //String temperatureMessage = "Temperature: " + weatherInfo.getTemperatureC() + " °C";
            //Toast.makeText(this, temperatureMessage, Toast.LENGTH_SHORT).show();

            temperature_view.set(weatherInfo.getTemperatureC() + " °C");
            //Toast.makeText(this, temperature_view, Toast.LENGTH_SHORT).show();
            //String temperature = weatherInfo.getTemperatureC() + " °C";
            itemList = generateSampleData("temperature_view.get()");
            //itemList = generateSampleData(temperature_view.get());
            adapter.notifyDataSetChanged();

        });

        viewModel.getError().observe(this, errorMessage -> {
            // Handle error
        });

        viewModel.fetchWeatherData(apiUrl);
        //Toast.makeText(getApplicationContext(), "tmp: " + viewModel.getWeatherData().toString(), Toast.LENGTH_SHORT).show();
        fetchTemperatureData();
        // Initialize the RecyclerView and its layout manager
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Thread.sleep(2500);

        // Prepare sample data for the RecyclerView
        itemList = generateSampleData(temperature_view.get());


        // Create the adapter and set it to the RecyclerView
        adapter = new MyAdapter(itemList, this);
        recyclerView.setAdapter(adapter);

        handler = new Handler();
        layoutChangeRunnable = new Runnable() {
            @Override
            public void run() {

                if (numlayouts == 0) {
                    fetchTemperatureData();
                    //recyclerView
                    //setContentView(R.layout.l1); // Change to layout_main2.xml
                    numlayouts = 1;
                } else if (numlayouts == 1){
                    itemList =removeAndReplaceItem();
                    adapter.notifyDataSetChanged();
                    adapter.updateData(itemList);
                    recyclerView.setAdapter(adapter);
                    //fetchTemperatureData();
                    //setContentView(R.layout.activity_main); // Change back to layout_main.xml

                    numlayouts = 2;
                } else if (numlayouts == 2){
                    //setContentView(R.layout.layout_main); // Change back to layout_main.xml

                    numlayouts = 0;
                }
                //isLayout1 = !isLayout1; // Toggle the layout flag
                handler.postDelayed(layoutChangeRunnable, LAYOUT_CHANGE_DELAY); // Reschedule the layout change
            }
        };

        handler.postDelayed(layoutChangeRunnable, LAYOUT_CHANGE_DELAY); // Start the initial layout change




        button = findViewById(R.id.changeLayoutButton);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String apiUrl = getString(R.string.STANMORE_API_URL) +"&q=ha74sg";
                //viewModel.fetchWeatherData(apiUrl);
                fetchTemperatureData();
            }
        });


    }


    private void fetchTemperatureData() {
        //
        String apiUrl = getString(R.string.STANMORE_API_URL) +"&q=ha74sg";
        viewModel.fetchWeatherData(apiUrl);

        viewModel.getWeatherData().observe(this, weatherInfo -> {
            // Update UI with weatherInfo data
            String temperatureMessage = weatherInfo.getTemperatureC() + " °C";
            Toast.makeText(this, temperatureMessage, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, temperature_view, Toast.LENGTH_SHORT).show();
            //String temperature = weatherInfo.getTemperatureC() + " °C";
            itemList = generateSampleData(temperatureMessage);
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


    private List<Item> removeAndReplaceItem(){
            itemList = generateSampleData("");
            int indexToRemoveAndReplace = 2;

            // Create the new Item object that you want to insert
            Item newItem = new Item(R.drawable.weather_sunny,  "29" + "\u00B0 " + " short clothes");

            // Remove the item at the specified index
            itemList.remove(indexToRemoveAndReplace);

            // Insert the new item at the same index
            itemList.add(indexToRemoveAndReplace, newItem);
            return itemList;
    }


    private List<Item> generateSampleData(String temperature) {

        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(R.drawable.whatsapp, getString(R.string.feature_whatsapp)));
        itemList.add(new Item(R.drawable.gate, getString(R.string.feature_gate_opener)));
        itemList.add(new Item(R.drawable.weather_partly_cloudy_rain, temperature + " " + " short clothes"));
        itemList.add(new Item(R.drawable.sms2fa, getString(R.string.feature_2fa)));



        //itemList.add(new Item(R.drawable.weather, this.temperature_view + "29" + "\u00B0 " + " short clothes"));
        //Toast.makeText(this, this.temperature_view, Toast.LENGTH_SHORT).show();
        //itemList.add(new Item(R.drawable.weather, getString(R.string.feature_temperature) + "29" + "\u00B0 " + " short clothes"));
        //itemList.add(new Item(R.drawable.sms2fa, temperature_view));
        // Add more items as needed

        return itemList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(layoutChangeRunnable); // Remove the layout change callbacks to avoid memory leaks
    }



}

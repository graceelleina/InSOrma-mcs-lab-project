package com.example.insorma;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.insorma.models.Furniture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {

    private Furniture emp;
    private ImageView furnitureImage;
    private TextView furnitureName, furniturePrice, furnitureRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int id = getIntent().getIntExtra("id", -1);

        init();

        String url = "https://bit.ly/InSOrmaJSON";

        furnitureName.setText(id + "");

    }

    private void init(){
        furnitureImage = findViewById(R.id.furImg);
        furnitureName = findViewById(R.id.furName);
        furnitureRating = findViewById(R.id.furRating);
        furniturePrice = findViewById(R.id.furPrice);
    }
}


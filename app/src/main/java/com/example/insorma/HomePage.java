package com.example.insorma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.insorma.adapters.FurnitureAdapter;
import com.example.insorma.models.Furniture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class HomePage extends AppCompatActivity {

    DatabaseHelper db;
    private Vector<Furniture> listFurniture;
    private RecyclerView rvMain;
    private FurnitureAdapter adapter;
    TextView Username;

    Button history, about, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        db = new DatabaseHelper(this);

        Username = findViewById(R.id.homeUsername);

        String usernames = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String phone = getIntent().getStringExtra("phone");

        String userId = db.getReadUserid(usernames);

        Username.setText(usernames);

        init();

        String url = "https://bit.ly/InSOrmaJSON";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest req = new JsonObjectRequest(url, response -> {
            try {
                JSONArray furnitures = response.getJSONArray("furnitures");
                for(int i=0; i< furnitures.length(); i++){
                    JSONObject emp = furnitures.getJSONObject(i);
                    String pict = emp.getString("image");
                    String name = emp.getString("product_name");
                    String rating = emp.getString("rating");
                    String price = emp.getString("price");
                    String desc = emp.getString("description");
                    String usernamePass = usernames;

                    Boolean insertProduk = db.insertProduct(name, rating, price, pict, desc);
                    Furniture newFurniture = new Furniture(pict, name, rating, price, usernamePass);
                    listFurniture.add(newFurniture);
                }
                adapter = new FurnitureAdapter(this, listFurniture);
                rvMain.setAdapter(adapter);
                rvMain.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
            Log.wtf("error", error.toString());
        });

        queue.add(req);

        about = (Button) findViewById(R.id.btnAbout);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutIntent = new Intent();
                aboutIntent.setClass(getApplicationContext(),MapsActivity.class);
                startActivity(aboutIntent);
            }
        });

        history = (Button) findViewById(R.id.btnHistory);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent();
                historyIntent.setClass(getApplicationContext(),TransactionHistoryPage.class);
                historyIntent.putExtra("userID", userId);
                startActivity(historyIntent);
            }
        });

        profile = (Button) findViewById(R.id.btnProfile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent = new Intent();
                profileIntent.setClass(getApplicationContext(),ProfilePage.class);
                profileIntent.putExtra("username", usernames);
                profileIntent.putExtra("email", email);
                profileIntent.putExtra("phone", phone);
                startActivity(profileIntent);
            }
        });

    }

    private void init(){
        listFurniture = new Vector<>();
        rvMain = findViewById(R.id.rvMain);
    }

}
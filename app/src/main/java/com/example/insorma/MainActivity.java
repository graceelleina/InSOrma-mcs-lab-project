package com.example.insorma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.insorma.adapters.FurnitureAdapter;
import com.example.insorma.models.Furniture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    Button login, register;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        email = (EditText) findViewById(R.id.edtText_email);
        password = (EditText) findViewById(R.id.edtText_password);
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterPage.class);
                startActivity(registerIntent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();
                String strPassword = password.getText().toString();
//              String username = getIntent().getStringExtra("username");

                Boolean entry = db.checkLogin(strEmail, strPassword);

                if(entry == true) {
                        String username = db.getReadUsername(strEmail);
                        String phone = db.getReadPhone(strEmail);
                        Toast.makeText(getApplicationContext(), "ANJENG MASUK GES", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(MainActivity.this, HomePage.class);
                        mainIntent.putExtra("username", username);
                        mainIntent.putExtra("email", strEmail);
                        mainIntent.putExtra("phone", phone);
                        Log.wtf("testdata", username);
                        startActivity(mainIntent);
                        finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "ANJENG GAGAL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onBackPressed() {

        startActivity(new Intent(MainActivity.this,MainActivity.class));

    }
}
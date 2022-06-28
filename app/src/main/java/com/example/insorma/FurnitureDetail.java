package com.example.insorma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class FurnitureDetail extends AppCompatActivity {

    DatabaseHelper db;
    TextView nama, ulasan, harga, quantity;
    Button buy;
    ImageView gambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_detail);

        db = new DatabaseHelper(this);

        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

        //textview
        nama = findViewById(R.id.furDetailName);
        ulasan = findViewById(R.id.furDetailRating);
        harga = findViewById(R.id.furDetailPrice);
        gambar = findViewById(R.id.furDetailImg);
        quantity = findViewById(R.id.furDetailQuantity);

        buy = findViewById(R.id.furButton);


        String name = getIntent().getStringExtra("name");
        String rating = getIntent().getStringExtra("rating");
        String price = getIntent().getStringExtra("price");
        String usernames = getIntent().getStringExtra("username");
        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");

        nama.setText(name);
        ulasan.setText(rating);
        harga.setText(price);
        gambar.setImageBitmap(bmp);

        String productId = db.getProductid(name);
        String userId = db.getReadUserid(usernames);
        String phone = db.getReadPhonebyUsername(usernames);

        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
        if(permission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS}, 123);
        }

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strQuantity = quantity.getText().toString();
                String strUsername = usernames.toString();
                String strDate = currentDate.toString();
                String message = "selamat tranksaksi anda berhasil";

                if(strQuantity.isEmpty()){
                    Toast.makeText(getApplicationContext(), "quantity tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else if(Integer.valueOf(strQuantity) > 0){
                    boolean detailInsert = db.insertDetail(userId, strDate, strQuantity, productId);

                    SmsManager manager = SmsManager.getDefault();;
                    manager.sendTextMessage(phone, null, message, null, null);

                    Intent intent = new Intent(FurnitureDetail.this, TransactionHistoryPage.class);
                    intent.putExtra("userID", userId);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "masukan quantity lebih dari 0", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
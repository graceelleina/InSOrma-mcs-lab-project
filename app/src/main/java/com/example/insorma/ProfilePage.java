package com.example.insorma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {

    DatabaseHelper db;
    TextView email, username, phone;
    EditText edtusername;
    Button edit, save;
    Button logout, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        db = new DatabaseHelper(this);

        //getExtra
        String emailExtra = getIntent().getStringExtra("email");
        String phoneExtra = getIntent().getStringExtra("phone");
        String usernameExtra = db.getReadUsername(emailExtra);

        //textview
        email = findViewById(R.id.tvEmail);
        username = findViewById(R.id.tvUsername);
        phone = findViewById(R.id.tvPhone);

        //edittext
        edtusername = findViewById(R.id.edtUsername);
        edtusername.setVisibility(View.INVISIBLE);

        //button
        edit = findViewById(R.id.btnEdit);
        save = findViewById(R.id.btnSave);
        logout = findViewById(R.id.btnLogout);
        delete = findViewById(R.id.btnDelete);
        save.setVisibility(View.INVISIBLE);

        username.setText(usernameExtra);
        email.setText(emailExtra);
        phone.setText(phoneExtra);
        
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtusername.getVisibility() == View.INVISIBLE){
                    edtusername.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    //
                    edit.setVisibility(View.INVISIBLE);
                    email.setVisibility(View.INVISIBLE);
                    username.setVisibility(View.INVISIBLE);
                    phone.setVisibility(View.INVISIBLE);

                }
                else{
                    edtusername.setVisibility(View.INVISIBLE);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtusername.getVisibility() == View.VISIBLE){
                    String usernameChage = edtusername.getText().toString();
                    Boolean updatedb = db.updateUsername(emailExtra, usernameChage);
                }
                else{
                    edtusername.setVisibility(View.VISIBLE);
                }
                Intent refreshProfile = new Intent(ProfilePage.this, ProfilePage.class);
                refreshProfile.putExtra("email", emailExtra);
                refreshProfile.putExtra("phone", phoneExtra);
                startActivity(refreshProfile);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean userDelete = db.deleteUser(emailExtra);
                Intent deleteAcc = new Intent(ProfilePage.this, MainActivity.class);
                startActivity(deleteAcc);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logout = new Intent(ProfilePage.this, MainActivity.class);
                startActivity(logout);
                finish();
            }
        });
    }
}
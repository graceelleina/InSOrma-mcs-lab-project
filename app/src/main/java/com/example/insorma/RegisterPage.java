package com.example.insorma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterPage extends AppCompatActivity {

    DatabaseHelper db;
    Button login, register;
    EditText email, username, phone, password;

    boolean isValidEmail = false;
    boolean isValidPassword = false;
    boolean isValidUsername = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        db = new DatabaseHelper(this);

        email = (EditText)findViewById(R.id.edtText_emailRegist);
        username = (EditText)findViewById(R.id.edtText_usernameRegist);
        phone = (EditText)findViewById(R.id.edtText_phoneRegist);
        password = (EditText)findViewById(R.id.edtText_passwordRegist);
        login = (Button)findViewById(R.id.btn_loginRegist);
        register = (Button)findViewById(R.id.btn_registerRegist);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterPage.this, MainActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        register.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString();
                String strUsername = username.getText().toString();
                String strPhone = phone.getText().toString();
                String strPassword = password.getText().toString();

                if(strEmail.isEmpty() || strUsername.isEmpty() || strPhone.isEmpty() || strPassword.isEmpty()){
                    Toast.makeText(RegisterPage.this, "Filled is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    isValidEmail = validateEmail(strEmail);

                    if(isValidEmail){
                        isValidUsername = validateUsername(strUsername);

                        if(isValidUsername)
                        {
                            isValidPassword = validatePassword(strPassword);
                            if(isValidPassword)
                            {
                                Boolean cek = db.checkRegister(strEmail, strUsername, strPhone, strPassword);

                                if(cek == true) {
                                    Toast.makeText(getApplicationContext(), "data udah ada", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Boolean signup = db.insertUser(strEmail, strUsername, strPhone, strPassword);
                                    Toast.makeText(getApplicationContext(), "Sign Up Success", Toast.LENGTH_SHORT).show();
                                    Intent loginIntent = new Intent(RegisterPage.this, MainActivity.class);
                                    loginIntent.putExtra("phone", strPhone);
                                    loginIntent.putExtra("username", strUsername);
                                    startActivity(loginIntent);
                                    finish();
                                }
                            }
                        }
                    }
                }
            }
        }));

    }

    private boolean validateEmail(String inputEmail) {
        if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
            Toast.makeText(RegisterPage.this, "email address must end with ‘.com’", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validateUsername(String inputUsername) {
        if (inputUsername.length() < 3 || inputUsername.length() > 20) {
            Toast.makeText(RegisterPage.this, "Username Must be between 3 and 20 Characters", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean validatePassword(String inputPassword) {

        Pattern upperCase = Pattern.compile("[A-Z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern digitCase = Pattern.compile("[0-9]");

        if (!digitCase.matcher(inputPassword).find() || !lowerCase.matcher(inputPassword).find() || !upperCase.matcher(inputPassword).find()) {
            Toast.makeText(RegisterPage.this, "Password Must be Alphanumeric", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

}
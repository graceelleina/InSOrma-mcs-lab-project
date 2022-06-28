package com.example.insorma;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "LoginSQL.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, email text, username text, phone text, password text)");
        db.execSQL("CREATE TABLE Product(ProductID integer PRIMARY KEY AUTOINCREMENT, ProductName text, ProductRating text, ProductPrice text, ProductImage text, ProductDescription Text)");
        db.execSQL("CREATE TABLE Transactions(TransactionID integer PRIMARY KEY AUTOINCREMENT, ProductID text, id text, TransactionDate text, Quantity text, FOREIGN KEY(id) REFERENCES user(id),  FOREIGN KEY(ProductID) REFERENCES Product(ProductID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Transactions");
        onCreate(db);
    }


    public Boolean insertUser(String email, String username, String phone, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("username", username);
        contentValues.put("phone", phone);
        contentValues.put("password", password);
        long insert = db.insert("user", null, contentValues);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean insertProduct(String name, String rating, String price, String image, String desc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ProductName", name);
        contentValues.put("ProductRating", rating);
        contentValues.put("ProductPrice", price);
        contentValues.put("ProductImage", image);
        contentValues.put("ProductDescription", desc);
        long insert = db.insert("Product", null, contentValues);
        if (insert == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    public Boolean insertDetail(String userId, String date, String quantity, String productId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", userId);
        contentValues.put("ProductID", productId);
        contentValues.put("TransactionDate", date);
        contentValues.put("Quantity", quantity);
        long insert = db.insert("Transactions", null, contentValues);
        if (insert == -1) {
            return false;
        }
       else {
           return true;
       }
    }

    public Boolean updateUsername(String emailExtra, String usernamesChage){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("username", usernamesChage);

        db.update("user", contentValues, "email = ?", new String[]{emailExtra});

        db.close();

        return true;
    }

    public Boolean deleteUser(String emailExtra){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("user", "email = ?", new String[]{emailExtra});

        db.close();

        return true;
    }

    public Boolean checkRegister(String email, String username, String phone, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ? AND username = ? AND phone = ? AND password = ?", new String[]{email, username, phone, password});
        if (cursor.getCount() > 0 ) {
            return true;
        }
        else {
            return false;
        }
    }

    public Boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ? AND password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

//    public String getRead(String email, String password)
//    {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT username FROM user WHERE email = ?", new String[]{email});
//        int column = cursor.getColumnIndex();
//
//        String username = cursor.getString();
//
//        return username;
//
//    }


    public String getReadUsername(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", new String[]{email});
        cursor.moveToFirst();

        String id = cursor.getString(0);
        String mail = cursor.getString(1);
        String username = cursor.getString(2);
        String phone = cursor.getString(3);

        return username;

    }

    public String getReadPhone(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", new String[]{email});
        cursor.moveToFirst();

        String id = cursor.getString(0);
        String mail = cursor.getString(1);
        String username = cursor.getString(2);
        String phone = cursor.getString(3);

        return phone;

    }

    public String getReadPhonebyUsername(String usernames)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ?", new String[]{usernames});
        cursor.moveToFirst();

        String id = cursor.getString(0);
        String mail = cursor.getString(1);
        String username = cursor.getString(2);
        String phone = cursor.getString(3);

        return phone;

    }

    public String getReadUserid(String usernames)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ?", new String[]{usernames});
        cursor.moveToFirst();

        String id = cursor.getString(0);
        String mail = cursor.getString(1);
        String username = cursor.getString(2);
        String phone = cursor.getString(3);

        return id;
    }

    public String getProductid(String productName)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE ProductName = ?", new String[]{productName});
        cursor.moveToFirst();

        String productId = cursor.getString(0);
        String productNames = cursor.getString(1);
        String productRating = cursor.getString(2);
        String productImage = cursor.getString(3);
        String productDescription = cursor.getString(4);

        return productId;
    }

    public Cursor viewData(String idUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from Transactions WHERE id =?", new String[]{idUser});
        return cursor;
    }

//    public Cursor viewData(String idUser) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("Select * from Transactions", null);
//        return cursor;
//    }

    public String getFurniturename(String furnitureId)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE ProductID = ?", new String[]{furnitureId});
        cursor.moveToFirst();

        String furnitureName = cursor.getString(1);

        return furnitureName;

    }

    public String getFurniturePrice(String furnitureId, String Quantity)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Product WHERE ProductID = ?", new String[]{furnitureId});
        cursor.moveToFirst();

        String furniturePrice = cursor.getString(3);

        int quantityBefore = Integer.parseInt(Quantity);
        int price = Integer.parseInt(furniturePrice);

        int totalPrice = quantityBefore*price;

        String strTotalPrice = String.valueOf(totalPrice);

        return strTotalPrice;

    }
}


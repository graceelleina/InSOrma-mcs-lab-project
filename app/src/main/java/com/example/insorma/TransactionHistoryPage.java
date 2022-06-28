package com.example.insorma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class TransactionHistoryPage extends AppCompatActivity {
    DatabaseHelper db;
    TextView transaction_id, furniture_name, bought_quantity, total_price, transaction_date;

    ListView historyList;
    ArrayList<String> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_page);
        db = new DatabaseHelper(this);

        listItem = new ArrayList<>();
        transaction_id = findViewById(R.id.historyTransactionID);
        furniture_name = findViewById(R.id.historyName);
        bought_quantity = findViewById(R.id.historyQuantity);
        total_price = findViewById(R.id.historyPrice);
        transaction_date = findViewById(R.id.historyDate);
        historyList = findViewById(R.id.lvHistory);

        viewData();

        historyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = historyList.getItemAtPosition(i).toString();
                Toast.makeText(TransactionHistoryPage.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void viewData() {
        String idUser = getIntent().getStringExtra("userID");
        Cursor cursor = db.viewData(idUser);

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                String furnitureId = cursor.getString(1);
                String quantity = cursor.getString(4);

                String furnitureName = db.getFurniturename(furnitureId);
                String furniturePrice = db.getFurniturePrice(furnitureId, quantity);

                listItem.add("Transaction ID: " + cursor.getString(0) + "\n" + // transaction_id
                        "Furniture Name: " + furnitureName.toString() + "\n" + // nembak string name
                        "Quantity: " + cursor.getString(4)+ "\n" +// quantity
                        "Total Price : " + furniturePrice.toString() + "\n" +// quantity harus ambil harga deban kunci fur id
                        "Transaction Date: " + cursor.getString(3));  // transaction_date
            }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listItem);
            historyList.setAdapter(adapter);
        }
    }
}


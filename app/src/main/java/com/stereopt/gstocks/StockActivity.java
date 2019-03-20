package com.stereopt.gstocks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.stereopt.gstocks.models.Stock;

public class StockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Intent intent = getIntent();
        Stock stock = new Stock(intent.getStringExtra("stock_symbol"), intent.getStringExtra("stock_name"));

        TextView tv = (TextView)findViewById(R.id.testStock);
        String stockFormated = stock.getSymbol() + " - " + stock.getName();
        tv.setText(stockFormated);
    }
}

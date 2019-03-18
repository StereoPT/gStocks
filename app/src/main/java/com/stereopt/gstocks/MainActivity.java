package com.stereopt.gstocks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.stereopt.gstocks.adapters.StockAdapter;
import com.stereopt.gstocks.models.Stock;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Stock> stockList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StockAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.stockList);

        mAdapter = new StockAdapter(stockList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareStockData();
    }

    private void prepareStockData() {
        Stock stock = new Stock("Altri");
        stockList.add(stock);

        stock = new Stock("Banco Comercial");
        stockList.add(stock);

        stock = new Stock("Corticeira Amorim");
        stockList.add(stock);

        stock = new Stock("CTT Correios de Portugal SA");
        stockList.add(stock);

        stock = new Stock("EDP");
        stockList.add(stock);

        stock = new Stock("EDP Renovaveis");
        stockList.add(stock);

        stock = new Stock("Galp Energia");
        stockList.add(stock);

        stock = new Stock("Ibersol");
        stockList.add(stock);

        stock = new Stock("J.Martins");
        stockList.add(stock);

        stock = new Stock("Mota Engil");
        stockList.add(stock);

        stock = new Stock("Nos SGPS SA");
        stockList.add(stock);

        stock = new Stock("Pharol SGPS SA");
        stockList.add(stock);

        stock = new Stock("Ramada");
        stockList.add(stock);

        stock = new Stock("REN");
        stockList.add(stock);

        stock = new Stock("Semapa");
        stockList.add(stock);

        stock = new Stock("Sonae");
        stockList.add(stock);

        stock = new Stock("Sonae Capital");
        stockList.add(stock);

        stock = new Stock("The Navigator");
        stockList.add(stock);

        mAdapter.notifyDataSetChanged();
    }
}

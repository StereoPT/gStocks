package com.stereopt.gstocks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stereopt.gstocks.adapters.StockAdapter;
import com.stereopt.gstocks.helpers.StockDBHelper;
import com.stereopt.gstocks.listeners.RecyclerTouchListener;
import com.stereopt.gstocks.models.Stock;
import com.stereopt.gstocks.models.Stock.StockEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StockDBHelper dbHelper;

    private List<Stock> stockList = new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private StockAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Local Database
        dbHelper = new StockDBHelper(this.getApplicationContext());

        //Activity Elements
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        recyclerView = (RecyclerView)findViewById(R.id.stockList);

        SetupRecycler();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mAdapter.clear();
                prepareStockData();
                mAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Stock stock = stockList.get(position);
                Intent intent = new Intent(getApplicationContext(), StockActivity.class);
                    intent.putExtra("stock_symbol", stock.getSymbol());
                    intent.putExtra("stock_name", stock.getName());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) { }
        }));

        prepareStockData();
    }

    private void SetupRecycler() {
        mAdapter = new StockAdapter(stockList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
    }

    public void btnAddNewStock(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchStock.class);
        startActivity(intent);
    }

    private void prepareStockData() {
        stockList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
            BaseColumns._ID,
            StockEntry.COLUMN_SYMBOL,
            StockEntry.COLUMN_NAME,
            StockEntry.COLUMN_REGION };

        String sortOrder = StockEntry.COLUMN_SYMBOL + " DESC";
        Cursor cursor = db.query(
            StockEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder);

        while(cursor.moveToNext()) {
            long stockID = cursor.getLong(cursor.getColumnIndexOrThrow(StockEntry._ID));
            String stockSymbol = cursor.getString(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_SYMBOL));
            String stockName = cursor.getString(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME));
            String stockRegion = cursor.getString(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_REGION));

            createStock(stockSymbol, stockName);
        }

        if(swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }
    }

    private void createStock(String symbol, String name) {
        stockList.add(new Stock(symbol, name, ""));
    }
}

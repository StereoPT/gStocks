package com.stereopt.gstocks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
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

    private List<Stock> stockList = new ArrayList<>();
    private RecyclerView recyclerView;
    private StockAdapter mAdapter;
    private StockDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.stockList);

        dbHelper = new StockDBHelper(this.getApplicationContext());

        mAdapter = new StockAdapter(stockList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Stock stock = stockList.get(position);
                //Toast.makeText(getApplicationContext(), stock.getSymbol() + " is selected!", Toast.LENGTH_SHORT).show();
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

    public void btnAddNewStock(View view) {
        Intent intent = new Intent(getApplicationContext(), SearchStock.class);
        startActivity(intent);
    }

    private void prepareStockData() {
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

        /*createStock("ALTR", "Altri");
        createStock("BCP", "Banco Comercial Português");
        createStock("COR", "Corticeira Amorim");
        createStock("CTT", "CTT Correios de Portugal");
        createStock("EDP", "Energias de Portugal");
        createStock("EDPR", "EDP Renováveis");
        createStock("GALP", "Galp Energia");
        createStock("IBS", "Ibersol");
        createStock("JMT", "Jerónimo Martins");
        createStock("EGL", "Mota-Engil");
        createStock("NOS", "NOS");
        createStock("NBA", "Novabase");
        createStock("PHR", "Pharol");
        createStock("RENE", "Redes Energéticas Nacionais");
        createStock("SEM", "Semapa");
        createStock("SON", "Sonae");
        createStock("SONC", "Sonae Capital");
        createStock("NVG", "The Navigator Company");*/

        mAdapter.notifyDataSetChanged();
    }

    private void createStock(String symbol, String name) {
        stockList.add(new Stock(symbol, name, ""));
    }
}

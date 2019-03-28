package com.stereopt.gstocks;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.stereopt.gstocks.adapters.StockAdapter;
import com.stereopt.gstocks.listeners.RecyclerTouchListener;
import com.stereopt.gstocks.models.Stock;

import org.json.JSONObject;

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
        createStock("ALTR", "Altri");
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
        createStock("NVG", "The Navigator Company");

        mAdapter.notifyDataSetChanged();
    }

    private void createStock(String symbol, String name) {
        stockList.add(new Stock(symbol, name, ""));
    }
}

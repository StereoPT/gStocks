package com.stereopt.gstocks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stereopt.gstocks.adapters.SearchAdapter;
import com.stereopt.gstocks.adapters.StockAdapter;
import com.stereopt.gstocks.models.Stock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchStock extends AppCompatActivity {

    private List<Stock> searchList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stock);

        recyclerView = (RecyclerView)findViewById(R.id.stockSearchResults);

        mAdapter = new SearchAdapter(searchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        final EditText stockSearch = (EditText)findViewById(R.id.txtStock);
        stockSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent) {
                boolean handled = false;

                if(actionID == EditorInfo.IME_ACTION_DONE) {
                    String stockName = textView.getText().toString();
                    fetchStocks(stockName);
                    handled = true;
                }

                return handled;
            }
        });
    }

    private void fetchStocks(String name) {
        String url = "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&keywords="+ name +"&apikey="+ R.string.alphaVantageKey;
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue reqQueue = Volley.newRequestQueue(SearchStock.this);
        reqQueue.add(req);
    }

    private void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONArray matches = object.getJSONArray("bestMatches");

            for(int i = 0; i < matches.length(); i++) {
                Log.d("GGG", matches.getJSONObject(i).toString());

                String stockSymbol = matches.getJSONObject(i).getString("1. symbol");
                String stockName = matches.getJSONObject(i).getString("2. name");
                String stockRegion = matches.getJSONObject(i).getString("4. region");

                Stock stock = new Stock(stockSymbol, stockName, stockRegion);
                searchList.add(stock);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        mAdapter.notifyDataSetChanged();
    }
}

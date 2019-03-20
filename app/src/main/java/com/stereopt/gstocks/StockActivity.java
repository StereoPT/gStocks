package com.stereopt.gstocks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stereopt.gstocks.models.Stock;

import org.json.JSONException;
import org.json.JSONObject;

public class StockActivity extends AppCompatActivity {
    //This is Deprecated, change it Later
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Intent intent = getIntent();
        Stock stock = new Stock(intent.getStringExtra("stock_symbol"), intent.getStringExtra("stock_name"));

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol="+ stock.getSymbol() +"&apikey="+ R.string.alphaVantageKey;
        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                parseJsonData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Some Error Occurred!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        RequestQueue reqQueue = Volley.newRequestQueue(StockActivity.this);
        reqQueue.add(req);
    }

    private void parseJsonData(String jsonString) {
        try {
            JSONObject object = new JSONObject(jsonString);
            JSONObject metadata = object.getJSONObject("Meta Data");

            TextView tv = (TextView)findViewById(R.id.testStock);
            tv.setText(metadata.toString());
        } catch(JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }
}

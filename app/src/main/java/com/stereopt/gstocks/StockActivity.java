package com.stereopt.gstocks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.stereopt.gstocks.models.Stock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;

public class StockActivity extends AppCompatActivity {
    //This is Deprecated, change it Later
    private ProgressDialog dialog;
    private CandleStickChart stockChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);

        Intent intent = getIntent();
        Stock stock = new Stock(intent.getStringExtra("stock_symbol"), intent.getStringExtra("stock_name"));

        TextView stockName = (TextView)findViewById(R.id.stockName);
        stockName.setText(stock.getName());

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
            JSONObject data = object.getJSONObject("Time Series (Daily)");

            stockChart = (CandleStickChart)findViewById(R.id.stockChart);
            setupChart(data);
        } catch(JSONException e) {
            e.printStackTrace();
        }

        dialog.dismiss();
    }

    private void setupChart(JSONObject data) {
        stockChart.setHighlightPerDragEnabled(true);

        //stockChart.setDrawBorders(true);
        //stockChart.setBorderColor(R.color.colorPrimaryDark);

        //YAxis yAxis = stockChart.getAxisLeft();
        //YAxis rightAxis = stockChart.getAxisRight();
        //yAxis.setDrawGridLines(false);
        //rightAxis.setDrawGridLines(false);
        //stockChart.requestDisallowInterceptTouchEvent(true);

        //XAxis xAxis = stockChart.getXAxis();
        //xAxis.setDrawGridLines(false);
        //xAxis.setDrawLabels(false);

        //rightAxis.setTextColor(Color.WHITE);
        //yAxis.setDrawLabels(false);

        //xAxis.setGranularity(1f);
        //xAxis.setGranularityEnabled(true);
        //xAxis.setAvoidFirstLastClipping(true);

        //Legend l = stockChart.getLegend();
        //l.setEnabled(false);

        ArrayList<CandleEntry> yValsCandleStick = new ArrayList<CandleEntry>();

        try {
            JSONArray dataArray = data.names();
            int size = dataArray.length();

            for(int i = 0; i < size; i++) {
                String currentStockDate = dataArray.get(size - 1 - i).toString();
                JSONObject stockInfo = new JSONObject(data.get(currentStockDate).toString());

                float high = Float.parseFloat(stockInfo.getString("2. high"));
                float low = Float.parseFloat(stockInfo.getString("3. low"));
                float open = Float.parseFloat(stockInfo.getString("1. open"));
                float close = Float.parseFloat(stockInfo.getString("4. close"));

                yValsCandleStick.add(new CandleEntry(i, high, low, open, close));
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        CandleDataSet set1 = new CandleDataSet(yValsCandleStick, "DataSet 1");

        //set1.setColor(Color.rgb(80, 80, 80));
        //set1.setShadowColor(R.color.colorPrimaryLight);
        //set1.setShadowWidth(0.8f);
        set1.setDecreasingColor(Color.RED);
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setIncreasingColor(Color.GREEN);
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(Color.LTGRAY);
        //set1.setDrawValues(false);

        CandleData candleData = new CandleData(set1);
        stockChart.setData(candleData);
        stockChart.invalidate();
    }
}

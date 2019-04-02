package com.stereopt.gstocks.adapters;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.stereopt.gstocks.R;
import com.stereopt.gstocks.helpers.StockDBHelper;
import com.stereopt.gstocks.models.Stock;
import com.stereopt.gstocks.models.Stock.StockEntry;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private List<Stock> stockList;

    public StockDBHelper dbHelper;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView symbol;
        public TextView name;
        public TextView region;

        public ImageButton btnSelect;

        public MyViewHolder(View view) {
            super(view);

            dbHelper = new StockDBHelper(view.getContext());

            symbol = (TextView)view.findViewById(R.id.searchStockSymbol);
            name = (TextView)view.findViewById(R.id.searchStockName);
            region = (TextView)view.findViewById(R.id.searchStockRegion);

            btnSelect = (ImageButton)view.findViewById(R.id.btnSelectStock);
            btnSelect.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Stock theStock = stockList.get(getAdapterPosition());

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
                values.put(StockEntry.COLUMN_SYMBOL, theStock.getSymbol());
                values.put(StockEntry.COLUMN_NAME, theStock.getName());
                values.put(StockEntry.COLUMN_REGION, theStock.getRegion());

            long newRowID = db.insert(StockEntry.TABLE_NAME, null, values);
        }
    }

    public SearchAdapter(List<Stock> stockList) {
        this.stockList = stockList;
    }

    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_search_list_row, parent, false);
        return new SearchAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.MyViewHolder holder, int position) {
        Stock stock = stockList.get(position);
        holder.symbol.setText(stock.getSymbol());
        holder.name.setText(stock.getName());
        holder.region.setText(stock.getRegion());
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }
}

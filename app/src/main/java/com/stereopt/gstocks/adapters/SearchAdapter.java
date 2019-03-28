package com.stereopt.gstocks.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stereopt.gstocks.R;
import com.stereopt.gstocks.models.Stock;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private List<Stock> stockList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView symbol;
        public TextView name;
        public TextView region;

        public MyViewHolder(View view) {
            super(view);
            symbol = (TextView)view.findViewById(R.id.searchStockSymbol);
            name = (TextView)view.findViewById(R.id.searchStockName);
            region = (TextView)view.findViewById(R.id.searchStockRegion);
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

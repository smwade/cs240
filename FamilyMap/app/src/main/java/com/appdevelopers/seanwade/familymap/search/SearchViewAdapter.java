package com.appdevelopers.seanwade.familymap.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appdevelopers.seanwade.familymap.R;
import com.appdevelopers.seanwade.familymap.filter.RowData;
import com.appdevelopers.seanwade.familymap.filter.RowViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by seanwade on 3/28/16.
 */
public class SearchViewAdapter extends RecyclerView.Adapter<SearchRowViewHolder> {

    private List<SearchRowData> list = Collections.emptyList();
    private Context context;

    public SearchViewAdapter(List<SearchRowData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SearchRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        SearchRowViewHolder holder = new SearchRowViewHolder(v, context);
        return holder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(SearchRowViewHolder holder, int position) {
        SearchRowData rowData = list.get(position);
        holder.bind(rowData);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}

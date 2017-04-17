package com.example.ryaan.wikipediasearch;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONException;

/**
 * Created by Ryaan on 17/04/2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder > {
    String res;
    ResultFormatter searchResult;
    public ResultAdapter(String res) {
        this.res=res;
        try {
            searchResult = new ResultFormatter(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item,parent,false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        holder.title.setText(searchResult.getTitles().get(position));
        holder.content.setText(searchResult.getContents().get(position));
    }
    @Override
    public int getItemCount() {
        return 10;
    }
}

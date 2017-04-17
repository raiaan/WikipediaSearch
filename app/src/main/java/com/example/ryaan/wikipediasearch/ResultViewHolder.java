package com.example.ryaan.wikipediasearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Ryaan on 17/04/2017.
 */

public class ResultViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView content;
    public ResultViewHolder(View itemView) {
        super(itemView);
        title= (TextView) itemView.findViewById(R.id.title_item);
        content=(TextView)itemView.findViewById(R.id.content_item);
    }
}

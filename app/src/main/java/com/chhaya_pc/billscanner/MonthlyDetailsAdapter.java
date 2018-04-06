package com.chhaya_pc.billscanner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Daniel-pc on 3/20/2018.
 */

public class MonthlyDetailsAdapter extends RecyclerView.Adapter<MonthlyDetailsAdapter.MyViewHolder> {
    List<Integer>images;
    List<String>catName;
    List<String>amount;
    List<String>date;
    Context context;

    public MonthlyDetailsAdapter(Context context, List<Integer>images, List<String>catName, List<String>amount, List<String>date){
        this.images=images;
        this.catName=catName;
        this.amount=amount;
        this.date=date;
        this.context=context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView catName, amount, date;
        public ImageView catImg;

        public MyViewHolder(View view) {
            super(view);
            catName = (TextView) view.findViewById(R.id.cat_name);
            amount = (TextView) view.findViewById(R.id.entered_amount);
            date = (TextView) view.findViewById(R.id.entered_date);
            catImg = (ImageView) view.findViewById(R.id.category_label);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.monthly_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d("TAG onbind", amount.size()+"");
        holder.amount.setText(amount.get(position));
        holder.catImg.setImageResource(images.get(position));
        holder.catName.setText(catName.get(position));
        holder.date.setText(date.get(position));

    }

    @Override
    public int getItemCount() {
        return amount.size();
    }
}

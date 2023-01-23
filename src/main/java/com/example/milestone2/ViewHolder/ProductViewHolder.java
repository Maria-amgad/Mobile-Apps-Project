package com.example.milestone2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.milestone2.R;

import com.example.milestone2.Interface.ItemClickListner;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView product_name, product_description, product_price;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        product_name = (TextView) itemView.findViewById(R.id.product_name);
        product_description = (TextView) itemView.findViewById(R.id.product_desc);
        product_price = (TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListner(ItemClickListner listner){
        this.listner= listner;
    }

    @Override
    public void onClick(View view) {

        listner.onClick(view, getAdapterPosition(), false);
    }
}

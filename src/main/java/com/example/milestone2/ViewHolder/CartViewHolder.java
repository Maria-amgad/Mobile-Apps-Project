package com.example.milestone2.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.milestone2.Interface.ItemClickListner;
import com.example.milestone2.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView product_name, product_price, product_quantity;
    private ItemClickListner itemClickListener;

    public CartViewHolder(View itemView){
        super(itemView);

        product_name = itemView.findViewById(R.id.cart_product_name);
        product_price = itemView.findViewById(R.id.cart_product_price);
        product_quantity = itemView.findViewById(R.id.cart_product_quantity);

    }

    @Override
    public void onClick(View view)
    {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListener(ItemClickListner itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}

package com.example.agroflo.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agroflo.Interface.ItemClickListener;
import com.example.agroflo.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;



    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);


        imageView = itemView.findViewById(R.id.farmer_product_image);
        txtProductName = itemView.findViewById(R.id.farmer_product_name);
        txtProductDescription = itemView.findViewById(R.id.farmer_product_description);
        txtProductPrice = itemView.findViewById(R.id.farmer_product_price);
    }

    public void setItemClickListener(ItemClickListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view)
    {
        listener.onClick(view, getAdapterPosition(), false);
    }
}

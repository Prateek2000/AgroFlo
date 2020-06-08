package com.example.agroflo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.agroflo.Model.Products;
import com.example.agroflo.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FarmerHomeActivity extends AppCompatActivity {


    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_home);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Orders");
        recyclerView = findViewById(R.id.farmer_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class)
                .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getItem());
                        holder.txtProductDescription.setText("Amount = "+ model.getAmount() + "kg");
                        holder.txtProductPrice.setText("Price = Rs." + model.getPrice());
                        if(holder.txtProductName.getText().equals("arhar"))
                        {
                            holder.imageView.setImageResource(R.drawable.arhar);
                        }
                        else if(holder.txtProductName.getText().toString().equals("carrot"))
                        {
                            holder.imageView.setImageResource(R.drawable.carrot);
                        }
                        else if(holder.txtProductName.getText().equals("tomato"))
                        {
                            holder.imageView.setImageResource(R.drawable.tomato);
                        }
                        else if(holder.txtProductName.getText().equals("onion"))
                        {
                            holder.imageView.setImageResource(R.drawable.onion);
                        }
                        else if(holder.txtProductName.getText().equals("capsicum"))
                        {
                            holder.imageView.setImageResource(R.drawable.capsicum);
                        }
                        else if(holder.txtProductName.getText().equals("corn"))
                        {
                            holder.imageView.setImageResource(R.drawable.corn);
                        }
                        else if(holder.txtProductName.getText().equals("mushroom"))
                        {
                            holder.imageView.setImageResource(R.drawable.mushroom);
                        }
                        else if(holder.txtProductName.getText().equals("potato"))
                        {
                            holder.imageView.setImageResource(R.drawable.potato);
                        }
                        else if(holder.txtProductName.getText().equals("wheat"))
                        {
                            holder.imageView.setImageResource(R.drawable.wheat);
                        }

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                Intent intent = new Intent(FarmerHomeActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("name", model.getItem());
                                intent.putExtra("desc",model.getAmount());
                                intent.putExtra("price",model.getPrice());
                                intent.putExtra("phone", model.getPhone());
                                startActivity(intent);

                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.farmer_product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}

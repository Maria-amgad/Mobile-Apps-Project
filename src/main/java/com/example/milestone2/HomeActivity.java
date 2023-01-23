package com.example.milestone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.milestone2.Model.Products;
import com.example.milestone2.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class HomeActivity extends AppCompatActivity {

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        Button home = (Button) findViewById(R.id.Home_page);
        Button search = (Button) findViewById(R.id.Search_page);
        Button cart = (Button) findViewById(R.id.Cart_page);
        Button profile = (Button) findViewById(R.id.Profile_page);

        recyclerView= findViewById(R.id.menu);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager((this));
        recyclerView.setLayoutManager(layoutManager);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(my_intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(my_intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent my_intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(my_intent);
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();

        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef, Products.class)
                        .build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                        holder.product_name.setText(model.getPname());
                        holder.product_description.setText(model.getDescription());
                        holder.product_price.setText(model.getPrice() + "LE");
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent my_intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                my_intent.putExtra("pid", model.getPid());
                                my_intent.putExtra("pname", model.getPname());
                                startActivity(my_intent);
                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
                        ProductViewHolder holder= new ProductViewHolder(view);
                        return holder;
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}
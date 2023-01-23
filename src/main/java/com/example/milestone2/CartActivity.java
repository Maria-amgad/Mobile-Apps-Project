package com.example.milestone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milestone2.Model.Cart;
import com.example.milestone2.Model.Products;
import com.example.milestone2.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button next_process;
    private TextView total_price_txt;
    private int Total_Price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        next_process = (Button) findViewById(R.id.next_process);
        total_price_txt = (TextView) findViewById(R.id.total_price);

        next_process.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_price_txt.setText("Total Price = " + String.valueOf(Total_Price) + " LE");

                if(Total_Price > 0) {
                    Intent my_intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                    my_intent.putExtra("Total Price", String.valueOf(Total_Price));
                    startActivity(my_intent);
                    finish();
                }else{
                    Toast.makeText(CartActivity.this, "No items in cart, Please add items to cart to go to next page.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef.child("Products"), Cart.class)
                        .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                holder.product_quantity.setText("Quantity = " + Integer.toString(model.getQuantity()));
                holder.product_price.setText(model.getPrice() + " LE");
                holder.product_name.setText(model.getPname());

                int price_per_product = ((Integer.valueOf(model.getPrice()))) * (model.getQuantity());
                Total_Price += price_per_product;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options:");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0){
                                    Intent my_intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                    my_intent.putExtra("pname", model.getPname());
                                    startActivity(my_intent);
                                }
                                if(i == 1){
                                    cartListRef.child("Products")
                                            .child(model.getPname())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(CartActivity.this, "Item removed successfully.", Toast.LENGTH_SHORT).show();
//                                                        Intent my_intent = new Intent(CartActivity.this, HomeActivity.class);
//                                                        startActivity(my_intent);
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout_items, parent, false);
               CartViewHolder holder = new CartViewHolder(view);
               return holder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}
package com.example.milestone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.milestone2.Model.Products;
import com.example.milestone2.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addtocart;
//    private FloatingActionButton addToCartBtn;
    private ImageView productImage;
//    private ElegantNumberButton numberButton;
    private NumberPicker numberButton;
    private TextView product_name, product_price, product_description;
    private String Product_id, Product_name, product_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Product_id = getIntent().getStringExtra("pid");
        Product_name = getIntent().getStringExtra("pname");

        addtocart = (Button) findViewById(R.id.addtocart);
//        addToCartBtn = (FloatingActionButton) findViewById(R.id.add_product_to_cart_btn);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        numberButton = (NumberPicker) findViewById(R.id.number_btn);
        product_name = (TextView) findViewById(R.id.product_name_details);
        product_price = (TextView) findViewById(R.id.product_price_details);
        product_description = (TextView) findViewById(R.id.product_description_details);

        numberButton.setMaxValue(10);
        numberButton.setMinValue(1);
        numberButton.setWrapSelectorWheel(true);
        numberButton.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }
        });

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(Product_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);

                    product_name.setText(products.getPname());
                    product_price.setText(products.getPrice() + " LE");
                    product_description.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Cart List");
                final HashMap<String, Object> cartMap= new HashMap<>();
                cartMap.put("Pid", Product_id);
                cartMap.put("Pname", Product_name);
                cartMap.put("Price", product_price.getText().toString().replace(" LE", ""));
                cartMap.put("Quantity", numberButton.getValue());

                cartListRef.child("Products").child(Product_name).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ProductDetailsActivity.this, "Item is added to the cart", Toast.LENGTH_SHORT).show();

                            Intent my_intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                            startActivity(my_intent);
                        }
                    }
                });
            }
        });
    }

    public void choose_color(View view){
        switch(view.getId()){
            case R.id.blue_color:
                product_color = "blue";
                Toast.makeText(ProductDetailsActivity.this, "Item color is " + product_color, Toast.LENGTH_SHORT).show();
                break;
            case R.id.purple_color:
                product_color = "purple";
                Toast.makeText(ProductDetailsActivity.this, "Item color is " + product_color, Toast.LENGTH_SHORT).show();
                break;
            case R.id.red_color:
                product_color = "red";
                Toast.makeText(ProductDetailsActivity.this, "Item color is " + product_color, Toast.LENGTH_SHORT).show();
                break;
            case R.id.black_color:
                product_color = "black";
                Toast.makeText(ProductDetailsActivity.this, "Item color is " + product_color, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
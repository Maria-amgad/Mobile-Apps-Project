package com.example.milestone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {

    private EditText name, phone, address, city, username;
    private Button confirm_btn;
    private String Total_Price = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        Total_Price = getIntent().getStringExtra("Total Price");
        Toast.makeText(ConfirmFinalOrderActivity.this, "Total Price = " + Total_Price + " LE", Toast.LENGTH_SHORT).show();

        confirm_btn = (Button) findViewById(R.id.confirm_btn);
        name = (EditText) findViewById(R.id.Name);
        phone = (EditText) findViewById(R.id.Phone);
        address = (EditText) findViewById(R.id.Address);
        city = (EditText) findViewById(R.id.City);
        username = (EditText) findViewById(R.id.username);

        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(name.getText().toString())){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(phone.getText().toString())){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(address.getText().toString())){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please provide your address.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(city.getText().toString())){
                    Toast.makeText(ConfirmFinalOrderActivity.this, "Please provide your City.", Toast.LENGTH_SHORT).show();
                }else{
                    final DatabaseReference orderListRef= FirebaseDatabase.getInstance().getReference().child("Orders");
                    final HashMap<String, Object> orderMap= new HashMap<>();
                    orderMap.put("total_price", Total_Price);
                    orderMap.put("name", name.getText().toString());
                    orderMap.put("phone", phone.getText().toString());
                    orderMap.put("address", address.getText().toString());
                    orderMap.put("city", city.getText().toString());
                    orderMap.put("state", "not shipped");

                    orderListRef.child(name.getText().toString()).updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                FirebaseDatabase.getInstance().getReference()
                                        .child("Cart List")
                                        .child("Products")
                                        .removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(ConfirmFinalOrderActivity.this, "Your order has been placed successfully.", Toast.LENGTH_SHORT).show();
                                                final DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
                                                usersRef.child(username.getText().toString()).child("points").setValue(ServerValue.increment(50));
                                                Intent my_intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                                my_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(my_intent);
                                                finish();
                                            }
                                        });
                            }
                        }
                    });
                }
            }
        });
    }
}
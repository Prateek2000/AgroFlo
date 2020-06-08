package com.example.agroflo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroflo.Prevalent.Prevalent;
import com.google.android.gms.common.util.NumberUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class CustomerPlaceOrder extends AppCompatActivity {

    private String ItemName, ItemAmount, ItemPrice;
    private TextView itemName, itemAmount, itemPrice;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_place_order);

        ItemName = getIntent().getExtras().get("item").toString();

        Toast.makeText(this, "You want to order "+ItemName, Toast.LENGTH_SHORT).show();

        itemName = findViewById(R.id.place_order_screen_text_2);

        itemName.setText(ItemName);

        loadingBar = new ProgressDialog(this);

        Paper.init(this);

    }

    public void order(View view)
    {

        itemAmount = findViewById(R.id.place_order_screen_amount);
        itemPrice = findViewById(R.id.place_order_screen_money);

        ItemAmount = itemAmount.getText().toString();
        ItemPrice = itemPrice.getText().toString();

        String PhoneNumber = Paper.book().read(Prevalent.UserPhoneKey);

        if (TextUtils.isEmpty(ItemAmount))
        {
            Toast.makeText(this, "Please enter how much "+ItemName+" you want to buy", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(ItemPrice))
        {
            Toast.makeText(this, "Please enter your offer price", Toast.LENGTH_SHORT).show();
        }
        else if (!(isInteger(ItemPrice)))
        {
            Toast.makeText(this, "Please enter price in whole numbers", Toast.LENGTH_SHORT).show();
        }
        else if (!(isInteger(ItemAmount)))
        {
            Toast.makeText(this, "Please enter amount in whole numbers", Toast.LENGTH_SHORT).show();
        }
        else {
            
            loadingBar.setTitle("Placing order");
            loadingBar.setMessage("Please wait while order is placed");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            ValidateOrder(PhoneNumber, ItemName, ItemAmount, ItemPrice);
        }

    }

    private void ValidateOrder(final String phone, final String item, final String amount, final String price)
    {
        final DatabaseReference CustomerPlaceOrderRef;
        CustomerPlaceOrderRef = FirebaseDatabase.getInstance().getReference();

        CustomerPlaceOrderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String, Object> orderMap = new HashMap<>();
                orderMap.put("phone", phone);
                orderMap.put("item", item);
                orderMap.put("amount", amount);
                orderMap.put("price", price);

                CustomerPlaceOrderRef.child("Orders").push().setValue(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(CustomerPlaceOrder.this, "Order successfully placed!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            
                            Intent intent = new Intent(CustomerPlaceOrder.this, CustomerHomeActivity.class);
                            startActivity(intent);
                        }
                        else {

                            loadingBar.dismiss();
                            Toast.makeText(CustomerPlaceOrder.this, "Network Error: Please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }
}


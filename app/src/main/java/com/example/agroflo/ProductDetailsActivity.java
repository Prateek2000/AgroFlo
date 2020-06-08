package com.example.agroflo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agroflo.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class ProductDetailsActivity extends AppCompatActivity {


    private TextView prodNameDetails, prodDescDetails, prodPriceDetails;
    private ImageView imageView;
    private Button acceptButton;
    private String item, amount, price, customerPhone;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        item = getIntent().getStringExtra("name");
        amount = getIntent().getStringExtra("desc");
        price = getIntent().getStringExtra("price");
        customerPhone = getIntent().getStringExtra("phone");


        prodNameDetails = findViewById(R.id.farmer_product_name_details);
        prodDescDetails = findViewById(R.id.farmer_product_description_details);
        prodPriceDetails = findViewById(R.id.farmer_product_price_details);

        imageView = findViewById(R.id.farmer_product_image_details);

        acceptButton = findViewById(R.id.accept_order_btn);
        
        prodNameDetails.setText(item);
        prodDescDetails.setText(amount+"kgs");
        prodPriceDetails.setText("Rs."+price);
        

        if(item.equals("arhar"))
        {
            imageView.setImageResource(R.drawable.arhar);
        }
        else if(item.equals("carrot"))
        {
            imageView.setImageResource(R.drawable.carrot);
        }
        else if(item.equals("tomato"))
        {
            imageView.setImageResource(R.drawable.tomato);
        }
        else if(item.equals("onion"))
        {
            imageView.setImageResource(R.drawable.onion);
        }
        else if(item.equals("capsicum"))
        {
            imageView.setImageResource(R.drawable.capsicum);
        }
        else if(item.equals("corn"))
        {
            imageView.setImageResource(R.drawable.corn);
        }
        else if(item.equals("mushroom"))
        {
            imageView.setImageResource(R.drawable.mushroom);
        }
        else if(item.equals("potato"))
        {
            imageView.setImageResource(R.drawable.potato);
        }
        else if(item.equals("wheat"))
        {
            imageView.setImageResource(R.drawable.wheat);
        }

        loadingBar = new ProgressDialog(this);

        Paper.init(this);

    }

    public void acceptOrder(View view)
    {
        String farmerPhone = Paper.book().read(Prevalent.UserPhoneKey);

        loadingBar.setTitle("Accepting order");
        loadingBar.setMessage("Please wait while order is accepted");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        ValidateOrder( customerPhone, farmerPhone, item, amount, price);
    }

    private void ValidateOrder(final String customerPhone, final String farmerPhone, final String item, final String amount, final String price) {

        final DatabaseReference FarmerAcceptOrderRef = FirebaseDatabase.getInstance().getReference();

        FarmerAcceptOrderRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HashMap<String, Object> orderMap = new HashMap<>();
                orderMap.put("customer_phone", customerPhone);
                orderMap.put("farmer_phone", farmerPhone);
                orderMap.put("item", item);
                orderMap.put("amount", amount);
                orderMap.put("price", price);

                FarmerAcceptOrderRef.child("Accepted_Orders").push().setValue(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(ProductDetailsActivity.this, "Order successfully accepted!", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            //Remove order from db
                            Intent intent = new Intent(ProductDetailsActivity.this, FarmerHomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(ProductDetailsActivity.this, "Network Error: Please try again", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

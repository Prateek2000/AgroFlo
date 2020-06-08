package com.example.agroflo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class CustomerHomeActivity extends AppCompatActivity {

    private ImageView arhar, carrot, tomato, onion, capsicum, corn, mushroom, potato, wheat;
    PopupMenu popupMenu;
    boolean click = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        arhar = findViewById(R.id.arhar);
        carrot = findViewById(R.id.carrot);
        tomato = findViewById(R.id.tomato);
        onion = findViewById(R.id.onion);
        capsicum = findViewById(R.id.capsicum);
        corn = findViewById(R.id.corn);
        mushroom = findViewById(R.id.mushroom);
        potato = findViewById(R.id.potato);
        wheat = findViewById(R.id.wheat);


        arhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "arhar");
                startActivity(intent);
            }
        });

        carrot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "carrot");
                startActivity(intent);
            }
        });

        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "tomato");
                startActivity(intent);
            }
        });

        onion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "onion");
                startActivity(intent);
            }
        });

        capsicum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "capsicum");
                startActivity(intent);
            }
        });

        corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "corn");
                startActivity(intent);
            }
        });

        mushroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "mushroom");
                startActivity(intent);
            }
        });

        potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "potato");
                startActivity(intent);
            }
        });

        wheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerHomeActivity.this, CustomerPlaceOrder.class);
                intent.putExtra("item", "wheat");
                startActivity(intent);
            }
        });



    }
}

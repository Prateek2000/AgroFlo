package com.example.agroflo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button farmerJoinNowButton, customerJoinNowButton, customerLoginButton, farmerLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        farmerJoinNowButton = findViewById(R.id.main_farmer_join_now_btn);
        customerJoinNowButton = findViewById(R.id.main_customer_join_now_btn);
        customerLoginButton = findViewById(R.id.main_customer_login_btn);
        farmerLoginButton = findViewById(R.id.main_farmer_login_btn);

        customerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
            }
        });

        farmerJoinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FarmerRegisterActivity.class);
                startActivity(intent);
            }
        });

        farmerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FarmerLoginActivity.class);
                startActivity(intent);
            }
        });

        customerJoinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CustomerRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

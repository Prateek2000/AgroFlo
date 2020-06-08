package com.example.agroflo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agroflo.Model.Farmer;
import com.example.agroflo.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class FarmerLoginActivity extends AppCompatActivity {

    private EditText FarmerInputPhoneNumber, FarmerInputPassword;
    private Button FarmerLoginButton;
    private ProgressDialog farmerLoadingBar;

    private String parentDbName = "Farmers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_login);



        FarmerLoginButton = findViewById(R.id.farmer_login_btn);
        FarmerInputPassword = findViewById(R.id.farmer_login_password_input);
        FarmerInputPhoneNumber = findViewById(R.id.farmer_login_phone_number_input);
        farmerLoadingBar = new ProgressDialog(this);


        FarmerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFarmer();
            }
        });

        Paper.init(this);
    }

    private void LoginFarmer() {
        String farmer_phone = FarmerInputPhoneNumber.getText().toString();
        String farmer_password = FarmerInputPassword.getText().toString();

        if (TextUtils.isEmpty(farmer_phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(farmer_password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            farmerLoadingBar.setTitle("Logging in");
            farmerLoadingBar.setMessage("Please wait while we are checking the credentials.");
            farmerLoadingBar.setCanceledOnTouchOutside(false);
            farmerLoadingBar.show();

            AllowAccessToFarmerAccount(farmer_phone, farmer_password);
        }
    }

    private void AllowAccessToFarmerAccount(final String phone, final String password)
    {

        Paper.book().write(Prevalent.UserPhoneKey, phone);
        Paper.book().write(Prevalent.UserPasswordKey, password);


        final DatabaseReference FarmerRootRef;
        FarmerRootRef = FirebaseDatabase.getInstance().getReference();

        FarmerRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists()){
                    Farmer farmersData = dataSnapshot.child(parentDbName).child(phone).getValue(Farmer.class);

                    if (farmersData.getPhone().equals(phone))
                    {
                        if (farmersData.getPassword().equals(password))
                        {
                            Toast.makeText(FarmerLoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            farmerLoadingBar.dismiss();

                            Intent intent = new Intent(FarmerLoginActivity.this, FarmerHomeActivity.class);
                            startActivity(intent);
                        }else
                        {
                            Toast.makeText(FarmerLoginActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                            farmerLoadingBar.dismiss();
                        }

                    }
                }

                else{
                    Toast.makeText(FarmerLoginActivity.this, "Account with phone number "+phone+" does not exist", Toast.LENGTH_SHORT).show();
                    farmerLoadingBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

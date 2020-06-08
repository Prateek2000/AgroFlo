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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FarmerRegisterActivity extends AppCompatActivity {

    private Button FarmerCreateAccountButton;
    private EditText FarmerInputName, FarmerInputPhoneNumber, FarmerInputPassword;
    private ProgressDialog farmerLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_register);


        FarmerCreateAccountButton = findViewById(R.id.farmer_register_btn);
        FarmerInputName = findViewById(R.id.farmer_register_username_input);
        FarmerInputPassword = findViewById(R.id.farmer_register_password_input);
        FarmerInputPhoneNumber = findViewById(R.id.farmer_register_phone_number_input);
        farmerLoadingBar = new ProgressDialog(this);

        FarmerCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FarmerCreateAccount();
            }
        });
    }

    private void FarmerCreateAccount()
    {
        String farmer_name = FarmerInputName.getText().toString();
        String farmer_phone = FarmerInputPhoneNumber.getText().toString();
        String farmer_password = FarmerInputPassword.getText().toString();

        if (TextUtils.isEmpty(farmer_name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(farmer_phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(farmer_password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            farmerLoadingBar.setTitle("Create Account");
            farmerLoadingBar.setMessage("Please wait while we are checking the credentials.");
            farmerLoadingBar.setCanceledOnTouchOutside(false);
            farmerLoadingBar.show();

            ValidateFarmerPhoneNumber(farmer_name, farmer_phone, farmer_password);

        }
    }

    private void ValidateFarmerPhoneNumber(final String name, final String phone, final String password)
    {
        final DatabaseReference FarmerRootRef;
        FarmerRootRef = FirebaseDatabase.getInstance().getReference();

        FarmerRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Farmers").child(phone).exists()))
                {
                    HashMap<String, Object> farmerdataMap = new HashMap<>();
                    farmerdataMap.put("phone", phone);
                    farmerdataMap.put("password", password);
                    farmerdataMap.put("name", name);

                    FarmerRootRef.child("Farmers").child(phone).updateChildren(farmerdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(FarmerRegisterActivity.this, "Congrats, your account has been created.", Toast.LENGTH_SHORT).show();
                                farmerLoadingBar.dismiss();

                                Intent intent = new Intent(FarmerRegisterActivity.this, FarmerLoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                farmerLoadingBar.dismiss();
                                Toast.makeText(FarmerRegisterActivity.this, "Network Error: Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(FarmerRegisterActivity.this, "" + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    farmerLoadingBar.dismiss();
                    Toast.makeText(FarmerRegisterActivity.this, "Please try again using another phone number" , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(FarmerRegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

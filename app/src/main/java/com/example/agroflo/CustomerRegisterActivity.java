package com.example.agroflo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class CustomerRegisterActivity extends AppCompatActivity {

    private Button CustomerCreateAccountButton;
    private EditText CustomerInputName, CustomerInputPhoneNumber, CustomerInputPassword;
    private ProgressDialog CustomerLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);


        CustomerCreateAccountButton = findViewById(R.id.customer_register_btn);
        CustomerInputName = findViewById(R.id.customer_register_username_input);
        CustomerInputPassword = findViewById(R.id.customer_register_password_input);
        CustomerInputPhoneNumber = findViewById(R.id.customer_register_phone_number_input);
        CustomerLoadingBar = new ProgressDialog(this);

        CustomerCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerCreateAccount();
            }
        });
    }

    private void CustomerCreateAccount()
    {
        String Customer_name = CustomerInputName.getText().toString();
        String Customer_phone = CustomerInputPhoneNumber.getText().toString();
        String Customer_password = CustomerInputPassword.getText().toString();

        if (TextUtils.isEmpty(Customer_name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Customer_phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Customer_password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CustomerLoadingBar.setTitle("Create Account");
            CustomerLoadingBar.setMessage("Please wait while we are checking the credentials.");
            CustomerLoadingBar.setCanceledOnTouchOutside(false);
            CustomerLoadingBar.show();

            ValidateCustomerPhoneNumber(Customer_name, Customer_phone, Customer_password);

        }
    }

    private void ValidateCustomerPhoneNumber(final String name, final String phone, final String password)
    {
        final DatabaseReference CustomerRootRef;
        CustomerRootRef = FirebaseDatabase.getInstance().getReference();

        CustomerRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Customers").child(phone).exists()))
                {
                    HashMap<String, Object> CustomerdataMap = new HashMap<>();
                    CustomerdataMap.put("phone", phone);
                    CustomerdataMap.put("password", password);
                    CustomerdataMap.put("name", name);

                    CustomerRootRef.child("Customers").child(phone).updateChildren(CustomerdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(CustomerRegisterActivity.this, "Congrats, your account has been created.", Toast.LENGTH_SHORT).show();
                                CustomerLoadingBar.dismiss();

                                Intent intent = new Intent(CustomerRegisterActivity.this, CustomerLoginActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                CustomerLoadingBar.dismiss();
                                Toast.makeText(CustomerRegisterActivity.this, "Network Error: Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(CustomerRegisterActivity.this, "" + phone + " already exists.", Toast.LENGTH_SHORT).show();
                    CustomerLoadingBar.dismiss();
                    Toast.makeText(CustomerRegisterActivity.this, "Please try again using another phone number" , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CustomerRegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

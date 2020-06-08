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

import com.example.agroflo.Model.Customer;
import com.example.agroflo.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class CustomerLoginActivity extends AppCompatActivity {

    private EditText CustomerInputPhoneNumber, CustomerInputPassword;
    private Button CustomerLoginButton;
    private ProgressDialog CustomerLoadingBar;

    private String parentDbName = "Customers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);



        CustomerLoginButton = findViewById(R.id.customer_login_btn);
        CustomerInputPassword = findViewById(R.id.customer_login_password_input);
        CustomerInputPhoneNumber = findViewById(R.id.customer_login_phone_number_input);
        CustomerLoadingBar = new ProgressDialog(this);


        CustomerLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginCustomer();
            }
        });

        Paper.init(this);

    }

    private void LoginCustomer() {
        String Customer_phone = CustomerInputPhoneNumber.getText().toString();
        String Customer_password = CustomerInputPassword.getText().toString();

        if (TextUtils.isEmpty(Customer_phone))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Customer_password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CustomerLoadingBar.setTitle("Logging in");
            CustomerLoadingBar.setMessage("Please wait while we are checking the credentials.");
            CustomerLoadingBar.setCanceledOnTouchOutside(false);
            CustomerLoadingBar.show();

            AllowAccessToCustomerAccount(Customer_phone, Customer_password);
        }
    }

    private void AllowAccessToCustomerAccount(final String phone, final String password)
    {

        Paper.book().write(Prevalent.UserPhoneKey, phone);
        Paper.book().write(Prevalent.UserPasswordKey, password);


        final DatabaseReference CustomerRootRef;
        CustomerRootRef = FirebaseDatabase.getInstance().getReference();

        CustomerRootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(phone).exists()){
                    Customer CustomersData = dataSnapshot.child(parentDbName).child(phone).getValue(Customer.class);

                    if (CustomersData.getPhone().equals(phone))
                    {
                        if (CustomersData.getPassword().equals(password))
                        {
                            Toast.makeText(CustomerLoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();
                            CustomerLoadingBar.dismiss();
                            Intent intent = new Intent(CustomerLoginActivity.this, CustomerHomeActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(CustomerLoginActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                            CustomerLoadingBar.dismiss();
                        }
                    }
                }

                else{
                    Toast.makeText(CustomerLoginActivity.this, "Account with phone number "+phone+" does not exist", Toast.LENGTH_SHORT).show();
                    CustomerLoadingBar.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

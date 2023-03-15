package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Account;
import com.example.myapplication.Model.Cart;
import com.example.myapplication.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private List<Account> accountList;

    private EditText usernameText;
    private EditText passwordText;
    private TextView loginBtn;
    private TextView createAccount;
    private TextView forgotPassword;
    private int numberAccount;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();

        initUi();
        getAccountList();
        handleLogin();
    }

    private void handleLogin() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameText.setError("Username cannot be empty");
                    usernameText.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    passwordText.setError("Password cannot be empty");
                    passwordText.requestFocus();
                } else {
                    String err = "";
                    boolean check = false;
                    for (Account account : accountList) {
                        // giong username
                        if (username.equals(account.getUsername())) {
                            // giong password
                            check = true;
                            if (password.equals(account.getPassword())) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                break;
                            } else {
                                passwordText.setError("Password Error!");
                                passwordText.requestFocus();
                                break;
                            }
                        } else {
                            check = false;
                            err = "Username isn't existed!";
                        }
                    }
                    if (!check) {
                        usernameText.setError(err);
                        usernameText.requestFocus();
                    }
                }
            }
        });

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }

    private void getAccountList() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Accounts");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numberAccount = 0;
                accountList = new ArrayList<Account>();
                accountList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    numberAccount++;
                    Account account = dataSnapshot.getValue(Account.class);
                    accountList.add(account);
                }

                //Toast.makeText(LoginActivity.this, "Get List Account Success!", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(LoginActivity.this, "Get List Account Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initUi() {
        usernameText = findViewById(R.id.etUsernameNew);
        passwordText = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.btnSignUp);
        createAccount = findViewById(R.id.tvCreateAccount);
        forgotPassword = findViewById(R.id.tvForgotPassword);
    }


}
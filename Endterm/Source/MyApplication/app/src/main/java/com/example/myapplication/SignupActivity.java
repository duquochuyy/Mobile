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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SignupActivity extends AppCompatActivity {

    private List<Account> accountList;
    private EditText usernameText;
    private EditText passwordText;
    private EditText rePasswordText;
    private TextView signBtn;
    private int numberAccount;
    
    private String username;
    private String password;
    private String rePassword;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("Accounts");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUI();
        getAccountList();
        handleLogin();
    }

    private void handleLogin() {
        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameText.getText().toString();
                password = passwordText.getText().toString();
                rePassword = rePasswordText.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameText.setError("Username cannot be empty");
                    usernameText.requestFocus();
                }

                else if (TextUtils.isEmpty(password)) {
                    passwordText.setError("Password cannot be empty");
                    passwordText.requestFocus();
                }

                else if (TextUtils.isEmpty(rePassword)) {
                    rePasswordText.setError("Re Password cannot be empty");
                    rePasswordText.requestFocus();
                }

                else if (!rePassword.equals(password) && !TextUtils.isEmpty(rePassword)) {
                    rePasswordText.setError("RePassword different Password");
                    rePasswordText.requestFocus();
                }
                else {
                    String err = "";
                    boolean check = true;

                    for (Account account : accountList) {
                        // giong username
                        if (username.equals(account.getUsername())) {
                            err = "Username is existed!";
                            check = false;
                            break;
                        }
                    }

                    if (!check) {
                        usernameText.setError(err);
                        usernameText.requestFocus();
                    }
                    else {
                        Toast.makeText(SignupActivity.this, "Sign up Success!", Toast.LENGTH_SHORT).show();
                        String id = "AC" + Integer.toString(numberAccount);
                        Account account = new Account(id, username, password);
                        ref.child(id).setValue(account);

                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }
                }
            }
        });
    }

    private void getAccountList() {

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

                //Toast.makeText(SignupActivity.this, "Get List Account Success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(SignupActivity.this, "Get List Account Error!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initUI() {
        usernameText = findViewById(R.id.etUsernameNew);
        passwordText = findViewById(R.id.etPasswordNew);
        rePasswordText = findViewById(R.id.etRePasswordNew);
        
        signBtn = findViewById(R.id.btnSignUp);
    }
}
package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    EditText name_product, category, description, price_normal, price_sale;
    TextView choose_img, addBtn;
    ImageView image, homeBtn;

    List<Product> productList = new ArrayList<Product>();
    Integer numOfProduct;
    private String name, cate, desc;
    private Integer price_nm = 0, price_sl = 0;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference("Products");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        initUI();
        handleImage();
        getProductList();
        handleAdd();
    }

    private void getProductList() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numOfProduct = 0;
                productList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    numOfProduct++;
                    Product product = dataSnapshot.getValue(Product.class);
                    productList.add(product);
                }

                //Toast.makeText(SignupActivity.this, "Get List Account Success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void handleAdd() {
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = name_product.getText().toString();
                cate = category.getText().toString();
                desc = description.getText().toString();
                price_nm = Integer.valueOf(price_normal.getText().toString());
                price_sl = Integer.valueOf(price_sale.getText().toString());

                if (TextUtils.isEmpty(name)){
                    name_product.setError("Name Product cannot be empty");
                    name_product.requestFocus();
                }
                else if (TextUtils.isEmpty(cate)){
                    category.setError("Category cannot be empty");
                    category.requestFocus();
                }
                else if (TextUtils.isEmpty(desc)){
                    description.setError("Description cannot be empty");
                    description.requestFocus();
                }
                else if (price_nm <= 0){
                    price_normal.setError("Price must be greater than 0");
                    price_normal.requestFocus();
                }
                else if (price_sl < 0){
                    price_sale.setError("Price must be greater than or equal to 0");
                    price_sale.requestFocus();
                }

                else {
                    String err = "";
                    boolean check = true;
                    for(Product product : productList){
                        if (name.equals(product.getName())){
                            err = "Product existed!";
                            check = false;
                            break;
                        }
                    }
                    if (!check) {
                        name_product.setError(err);
                        name_product.requestFocus();
                    }
                    else {
                        Toast.makeText(AddProductActivity.this, "Add product Success!", Toast.LENGTH_SHORT).show();
                        String id = "PD" + Integer.toString(numOfProduct);
                        Product product = new Product(id, name, desc, cate, price_nm, price_sl, 0);
                        ref.child(id).setValue(product);

                        //startActivity(new Intent(AddProductActivity.this, MainActivity.class));
                    }
                }
            }
        });
    }

    private void initUI() {
        name_product = findViewById(R.id.id_name_product);
        category = findViewById(R.id.id_category);
        description = findViewById(R.id.id_description);
        price_normal = findViewById(R.id.id_price_normal);
        price_sale = findViewById(R.id.id_price_sale);

        choose_img = findViewById(R.id.choose_image);
        image = findViewById(R.id.image_product);

        homeBtn = findViewById(R.id.homeButton);

        addBtn = findViewById(R.id.addBtn);
    }

    private void handleImage() {
        choose_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddProductActivity.this, MainActivity.class));
            }
        });
    }
}
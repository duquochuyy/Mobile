package com.example.myapplication.Model;

import android.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Cart implements Serializable {
    private String id, accoundId;
    private List<Pair<String, Integer>> productId = new ArrayList<Pair<String, Integer>>();

    public Cart(String id, String accoundId, List<Pair<String, Integer>> productId) {
        this.id = id;
        this.accoundId = accoundId;
        this.productId = productId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccoundId() {
        return accoundId;
    }

    public void setAccoundId(String accoundId) {
        this.accoundId = accoundId;
    }

    public List<Pair<String, Integer>> getProductId() {
        return productId;
    }

    public void setProductId(List<Pair<String, Integer>> productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id='" + id + '\'' +
                ", accoundId='" + accoundId + '\'' +
                ", productId=" + productId +
                '}';
    }
}

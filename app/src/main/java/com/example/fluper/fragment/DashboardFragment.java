package com.example.fluper.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.fluper.R;
import com.example.fluper.model.ProductListModel;
import com.example.fluper.storage.DbRepository;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    DbRepository dbRepository;
    ProductListModel productListModel;
    ArrayList<ProductListModel> productList = new ArrayList<>();
    int product_count = 0;
    private static DashboardFragment instance = null;

    Button bCreate, bShow;
    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment getInstance() {
        return instance;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DashboardFragment.
     */
    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initializing db
        dbRepository = new DbRepository(getContext());
        //delete all values from db to avoid redundancy
        dbRepository.deleteAllProducts();

        bCreate = view.findViewById(R.id.btn_create);
        bShow = view.findViewById(R.id.btn_show_products);

        bCreate.setOnClickListener(v -> {
            getProduct();
        });

        bShow.setOnClickListener(v -> {
            //load ProductListFragment
            ProductListFragment productListFragment = new ProductListFragment();
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, productListFragment).addToBackStack(null).commit();
        });
    }

    //converting json file into string
    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    //get product from json file
    private void getProduct() {
        String productString = inputStreamToString(getActivity().getResources().openRawResource(R.raw.product_list));
        try {
            //convert string into Json Object
            JSONObject jsonObj = new JSONObject(productString);
            JSONArray jsonArray = jsonObj.getJSONArray("product");

            //product_count is a static variable, initially which is 0,
            //product_count will increment after storing each product into db
            //if product_count went lesser than array size then show a message to the user
            if (product_count < jsonArray.length()) {
                try {
                    //get product_count'th product in array and store it into productListModel object
                    ProductListModel productListModel = new Gson().fromJson(jsonArray.getJSONObject(product_count).toString(), ProductListModel.class);
                    storeData(productListModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(getContext(), getString(R.string.all_products_added), Toast.LENGTH_LONG).show();
            }
            Log.e("JSON2", String.valueOf(jsonArray.length()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void storeData(ProductListModel productListModel) {
        Log.e("StoreData", productListModel.getProduct_photo());
        //passing values to db and increase product_count variable
            dbRepository.insertProduct(productListModel);
            product_count++;
            //update button
            int count = product_count + 1;
            bCreate.setText(getString(R.string.create_product)+" "+count);
    }

    public void updateProductCount(){
        product_count--;
        int count = product_count + 1;
        bCreate.setText(getString(R.string.create_product)+" "+count);
    }
}
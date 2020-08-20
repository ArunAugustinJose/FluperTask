package com.example.fluper.activity;

import android.os.Bundle;

import com.example.fluper.R;
import com.example.fluper.fragment.DashboardFragment;
import com.example.fluper.model.ProductListModel;
import com.example.fluper.util.AppController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ProductListModel> productList = new ArrayList<>();
    ProductListModel productListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppController.setActivity(this);

        initRootView();
    }

    //loading root fragment
    private void initRootView() {
        DashboardFragment dashboardFragment = new DashboardFragment();
        this.getSupportFragmentManager().beginTransaction().replace(R.id.container, dashboardFragment).addToBackStack(null).commit();

    }

    @Override
    public void onBackPressed() {
        //checking the count of backStack
        //if more than one fragment is in backStack, then call popBackStack
        //or exit from activity
        int count = getSupportFragmentManager().getBackStackEntryCount();
        getSupportFragmentManager().popBackStack();
        if (count <= 1)
            super.onBackPressed();
    }
}
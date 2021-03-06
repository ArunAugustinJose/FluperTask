package com.example.fluper.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fluper.R;
import com.example.fluper.adapter.ProductAdapter;
import com.example.fluper.model.ColorModel;
import com.example.fluper.model.ProductListModel;
import com.example.fluper.storage.DbRepository;
import com.example.fluper.storage.entity.ProductEntity;
import com.example.fluper.util.GridSpacingItemDecoration;
import com.google.gson.Gson;
import com.mancj.materialsearchbar.MaterialSearchBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.fluper.constant.BundleConstant.PRODUCT;
import static com.example.fluper.constant.DataConstant.PRODUCT_IMAGE_ARRAY;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment implements ProductAdapter.ProductClickListener {

    RecyclerView rProducts;
    MaterialSearchBar searchBar;
    ImageView mBack;
    LinearLayout lEmpty;

    private List<ProductListModel> productList = new ArrayList<>();
    ProductAdapter mAdapter;
    DbRepository dbRepository;
    private static ProductListFragment instance = null;

    public ProductListFragment() {
        // Required empty public constructor
    }

    //getter of instance
    public static ProductListFragment getInstance() {
        return instance;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductListFragment.
     */
    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
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
        return inflater.inflate(R.layout.fragment_product_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rProducts = view.findViewById(R.id.rv_product);
        searchBar = view.findViewById(R.id.searchBar);
        mBack = view.findViewById(R.id.img_back);
        lEmpty = view.findViewById(R.id.ll_empty);

        //set elevation to 0
        searchBar.setCardViewElevation(0);

        mBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        getProducts(false);
    }

    private void init() {
        mAdapter = new ProductAdapter(getContext(), productList, this);

        rProducts.setLayoutManager(new GridLayoutManager(rProducts.getContext(), 1));
        rProducts.addItemDecoration(new GridSpacingItemDecoration(1, 20, false));
        rProducts.setAdapter(mAdapter);

        //material search view functionality
        searchBar.setCardViewElevation(0);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.e("ON_TEXT_CHANGE", charSequence.toString());
                mAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });
        searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //pass search string to adapter
                mAdapter.getFilter().filter(text.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
                switch (buttonCode) {
                    case MaterialSearchBar.BUTTON_BACK:
                        searchBar.disableSearch();
                        break;
                }
            }
        });
    }

    public void getProducts(boolean isUpdate) {
        //initializing db
        dbRepository = new DbRepository(getContext());
        dbRepository.getAllProducts().observe(getActivity(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                productList.clear();
                if (productEntities.size() > 0) {
                    lEmpty.setVisibility(View.GONE);
                    int i = 0;
                    for (ProductEntity productEntity : productEntities) {
                        ProductListModel productListModel = new ProductListModel();
                        productListModel.setId(productEntity.getProduct_id());
                        productListModel.setName(productEntity.getName());
                        productListModel.setDescription(productEntity.getDescription());
                        productListModel.setRegular_price(productEntity.getRegular_price());
                        productListModel.setSale_price(productEntity.getSale_price());
                        productListModel.setProduct_photo(productEntity.getProduct_photo());
                        productListModel.setImage(PRODUCT_IMAGE_ARRAY[i]);
                        productListModel.setColorList(getColors(productEntity.getColors()));
                        getColors(productEntity.getColors());
                        productList.add(productListModel);
                        i++;
                    }
                    if (isUpdate){
                        mAdapter.notifyDataSetChanged();
                    }else {
                        init();
                    }
                }else {
                    lEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private ArrayList<ColorModel> getColors(String colors){
        ArrayList<ColorModel> colorList = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(colors);
            for (int i = 0; i<jsonArray.length(); i++){
                //convert string into Json Object
//                JSONObject jsonObj = new JSONObject(jsonArray.getJSONObject(i).toString());
                //get product_count'th product in array and store it into productListModel object
//                ColorModel colorModel = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), ColorModel.class);
//                colorList.add(colorModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return colorList;
    }

    @Override
    public void onItemClick(int position, boolean isDelete) {
        if (!isDelete) {
            //load ProductDetailFragment
            ProductDetailFragment productDetailFragment = new ProductDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PRODUCT, productList.get(position));

            productDetailFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, productDetailFragment).addToBackStack(null).commit();
        }else {
            //delete corresponding product from db
            dbRepository.deleteProduct(productList.get(position).getId());
            //update product_count variable when delete the product.
            DashboardFragment.getInstance().updateProductCount();
        }
    }
}

/**
 * Created: Arun Jose
 * 21/08/2020
 */
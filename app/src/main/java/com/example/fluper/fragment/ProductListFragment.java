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

import com.example.fluper.R;
import com.example.fluper.adapter.ProductAdapter;
import com.example.fluper.model.ProductListModel;
import com.example.fluper.storage.DbRepository;
import com.example.fluper.storage.entity.ProductEntity;
import com.example.fluper.util.GridSpacingItemDecoration;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import static com.example.fluper.constant.DataConstant.PRODUCT_IMAGE_ARRAY;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductListFragment extends Fragment implements ProductAdapter.ProductClickListener {

    RecyclerView rProducts;
    MaterialSearchBar searchBar;

    private List<ProductListModel> productList = new ArrayList<>();
    ProductAdapter mAdapter;
    DbRepository dbRepository;

    public ProductListFragment() {
        // Required empty public constructor
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

        getProducts();
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
                Log.e("SEARCH", text.toString());
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

    private void getProducts() {
        //initializing db
        dbRepository = new DbRepository(getContext());
        dbRepository.getAllProducts().observe(getActivity(), new Observer<List<ProductEntity>>() {
            @Override
            public void onChanged(List<ProductEntity> productEntities) {
                productList.clear();
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
                    Log.e("COLORS", productEntity.getProduct_photo());
                    productList.add(productListModel);
                    i++;
                }
                init();
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }
}
package com.example.fluper.fragment;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fluper.R;
import com.example.fluper.model.ProductListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoomConfig;

import static com.example.fluper.constant.BundleConstant.PRODUCT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {

    ImageView mBack;
    ImageViewZoom mProduct;
    TextView tName, tDescription, tPrice, tSalePrice;
    FloatingActionButton fEdit;

    ProductListModel productListModel;
    private static ProductDetailFragment instance = null;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public static ProductDetailFragment getInstance() {
        return instance;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProductDetailFragment.
     */
    public static ProductDetailFragment newInstance() {
        ProductDetailFragment fragment = new ProductDetailFragment();
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
        //check whether the arguments are null or not
        if (getArguments() != null) {
            productListModel = getArguments().getParcelable(PRODUCT);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProduct = view.findViewById(R.id.img_product);
        tName = view.findViewById(R.id.tv_name);
        tDescription = view.findViewById(R.id.tv_description);
        tPrice = view.findViewById(R.id.tv_price);
        tSalePrice = view.findViewById(R.id.tv_sale_price);
        mBack = view.findViewById(R.id.img_back);
        fEdit = view.findViewById(R.id.fab_edit);

        mBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });

        fEdit.setOnClickListener(v -> {
            //load edit fragment
            EditFragment editFragment = new EditFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PRODUCT, productListModel);
            editFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container, editFragment).
                    addToBackStack(null).commit();

        });

        fillData();
    }

    private void fillData() {
        tName.setText(productListModel.getName());
        tDescription.setText(productListModel.getDescription());
        tSalePrice.setText("₹ "+String.valueOf((int)productListModel.getSale_price()));
        tPrice.setText("₹ "+String.valueOf((int)productListModel.getRegular_price()));
        tPrice.setPaintFlags(tPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        ImageViewZoomConfig imageViewZoomConfig =new ImageViewZoomConfig.Builder().saveProperty(true).saveMethod(ImageViewZoomConfig.ImageViewZoomConfigSaveMethod.onlyOnDialog).build();
        mProduct.setConfig(imageViewZoomConfig);

        Glide.with(getActivity()).load(productListModel.getImage())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mProduct);
    }

    public void updateData(ProductListModel productModel){
        productListModel = productModel;
        fillData();
        ProductListFragment.getInstance().getProducts(true);
    }
}

/**
 * Created: Arun Jose
 * 21/08/2020
 */
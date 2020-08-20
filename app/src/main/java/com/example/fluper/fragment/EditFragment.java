package com.example.fluper.fragment;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fluper.R;
import com.example.fluper.model.ProductListModel;
import com.example.fluper.storage.DbRepository;
import com.example.fluper.util.ValidateInputs;

import ozaydin.serkan.com.image_zoom_view.ImageViewZoom;
import ozaydin.serkan.com.image_zoom_view.ImageViewZoomConfig;

import static com.example.fluper.constant.BundleConstant.PRODUCT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

    ImageView mBack;
    EditText tName, tDescription, tPrice, tSalePrice;
    Button bUpdate;

    ProductListModel productListModel;
    DbRepository dbRepository;

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditFragment.
     */
    public static EditFragment newInstance() {
        EditFragment fragment = new EditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //check whether the arguments are null or not
        if (getArguments() != null) {
            productListModel = getArguments().getParcelable(PRODUCT);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tName = view.findViewById(R.id.et_name);
        tDescription = view.findViewById(R.id.et_description);
        tPrice = view.findViewById(R.id.et_price);
        tSalePrice = view.findViewById(R.id.et_sale_price);
        bUpdate = view.findViewById(R.id.btn_update);
        mBack = view.findViewById(R.id.img_back);

        mBack.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().popBackStack();
        });
        bUpdate.setOnClickListener(v -> {
            // Validate Login Form Inputs
            boolean isValidData = validateForm();

            if (isValidData) {
                //initializing db
                ProductListModel pModal = new ProductListModel();
                pModal.setId(productListModel.getId());
                pModal.setName(tName.getText().toString());
                pModal.setDescription(tDescription.getText().toString());
                pModal.setRegular_price(productListModel.getRegular_price());
                pModal.setSale_price(Double.parseDouble(tSalePrice.getText().toString()));
                pModal.setImage(productListModel.getImage());
                pModal.setProduct_photo(productListModel.getProduct_photo());

                dbRepository = new DbRepository(getContext());
                dbRepository.updateProduct(tName.getText().toString(),
                        tDescription.getText().toString(),
                        Double.parseDouble(tSalePrice.getText().toString()),
                        productListModel.getId());

                ProductDetailFragment.getInstance().updateData(pModal);
            }
        });

        fillData();
    }

    private void fillData() {
        tName.setText(productListModel.getName());
        tDescription.setText(productListModel.getDescription());
        tSalePrice.setText(String.valueOf((int) productListModel.getSale_price()));
        tPrice.setText("₹ " + String.valueOf((int) productListModel.getRegular_price()));
        tPrice.setPaintFlags(tPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    //validate input form
    private boolean validateForm() {
        String regexStr = "^[A-Za-z]{4}\\d{7}$";
        if (!ValidateInputs.isValidInput(tName.getText().toString().trim())) {
            tName.setError(getString(R.string.invalid_name));
            return false;
        } else if (!ValidateInputs.isValidInput(tDescription.getText().toString().trim())) {
            tDescription.setError(getString(R.string.invalid_description));
            return false;
        } else if (!ValidateInputs.isValidNumber(tSalePrice.getText().toString().trim())) {
            tSalePrice.setError(getString(R.string.invalid_price));
            return false;
        } else {
            return true;
        }
    }
}
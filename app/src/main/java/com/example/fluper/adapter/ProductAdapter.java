package com.example.fluper.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fluper.R;
import com.example.fluper.activity.MainActivity;
import com.example.fluper.fragment.ProductListFragment;
import com.example.fluper.model.ProductListModel;
import com.example.fluper.util.AppController;

import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.DashBoardVH> implements Filterable {

    private List<ProductListModel> mProductList;
    private List<ProductListModel> productListFiltered;
    private Context mContext;
    private LayoutInflater inflater;
    private ProductClickListener mClickListener;
    private boolean mStatus;

    //constructor
    public ProductAdapter(Context context, List<ProductListModel> itemList, ProductClickListener clickListener) {
        mProductList = itemList;
        productListFiltered = itemList;
        inflater = LayoutInflater.from(context);
        this.mContext = context;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public DashBoardVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_product, null, false);
        return new DashBoardVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardVH holder, int position) {
        ProductListModel pList = productListFiltered.get(position);

        Glide.with(mContext).load(pList.getImage())
                .thumbnail(0.5f)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productImage);

        holder.mTitle.setText(pList.getName());
        holder.mPrice.setText("₹ "+String.valueOf(pList.getSale_price()));

    }

    @Override
    public int getItemCount() {
        return productListFiltered.size();
    }

    class DashBoardVH extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView mTitle, mPrice;

        DashBoardVH(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.img_product);
            mTitle = itemView.findViewById(R.id.tv_product);
            mPrice = itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(view -> {
                if (mClickListener != null) {
                    mClickListener.onItemClick(productListFiltered.get(getAdapterPosition()).getId());
                }
            });
        }
    }

    //filter using for search
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    productListFiltered = mProductList;
                } else {
                    List<ProductListModel> filteredList = new ArrayList<>();
                    for (ProductListModel row : mProductList) {

                        // name match condition.
                        // here we are looking for product name match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                            Log.e("ADAPTER!", row.getName());
                        }
                    }

                    productListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = productListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productListFiltered = (ArrayList<ProductListModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //initialize a listener to handle click event
    public interface ProductClickListener {
        void onItemClick(int position);
    }
}

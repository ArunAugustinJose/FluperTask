package com.example.fluper.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductListModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("regular price")
    @Expose
    private double regular_price;
    @SerializedName("sale price")
    @Expose
    private double sale_price;
    @SerializedName("product_photo")
    @Expose
    private String product_photo;
    @SerializedName("colors")
    @Expose
    private ArrayList<ColorModel> colorList = new ArrayList<>();
    @SerializedName("image")
    @Expose
    private int image;

    public ProductListModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        regular_price = in.readDouble();
        sale_price = in.readDouble();
        product_photo = in.readString();
        image = in.readInt();
    }

    public static final Creator<ProductListModel> CREATOR = new Creator<ProductListModel>() {
        @Override
        public ProductListModel createFromParcel(Parcel in) {
            return new ProductListModel(in);
        }

        @Override
        public ProductListModel[] newArray(int size) {
            return new ProductListModel[size];
        }
    };

    public ProductListModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(double regular_price) {
        this.regular_price = regular_price;
    }

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public String getProduct_photo() {
        return product_photo;
    }

    public void setProduct_photo(String product_photo) {
        this.product_photo = product_photo;
    }

    public ArrayList<ColorModel> getColorList() {
        return colorList;
    }

    public void setColorList(ArrayList<ColorModel> colorList) {
        this.colorList = colorList;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeDouble(regular_price);
        dest.writeDouble(sale_price);
        dest.writeString(product_photo);
        dest.writeInt(image);
    }
}

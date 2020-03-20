package com.example.smartiptv.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Live implements Serializable {
	String cover;
	public String category_name, category_id,name,stream_icon,stream_id,stream_type;
	int count;
	public int productImage;
	public String productName;

	public Live(String cover, String category_name, String category_id) {
		this.cover = cover;
		this.category_name = category_name;
		this.category_id = category_id;
	}

	public Live(String productName) {

		this.productName = productName;
	}

	public Live() {


	}

	public String getImage() {
		return cover;
	}

	public String getCategory_name() {
		return category_name;
	}

	public String getCategory_id() {
		return category_id;
	}

	public String getStreamname() {
		return name;
	}

	public String getStream_icon() {
		return stream_icon;
	}

	public String getStream_id() {
		return stream_id;
	}
	public int getCount() {
		return count;
	}
	public String getStream_type() {
		return stream_type;
	}

	@Override
	public String toString() {
		return category_name;
	}

	public int getProductImage() {
		return productImage;
	}

	public void setProductImage(int productImage) {
		this.productImage = productImage;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}

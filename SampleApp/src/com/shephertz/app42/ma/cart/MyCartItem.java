package com.shephertz.app42.ma.cart;

import org.json.JSONException;
import org.json.JSONObject;

public class MyCartItem {

	private String id;
	private int quantity;

	private String description;
	private double prize;
    private String name;
    private double discountedPrize;
    
	
	public double getDiscountedPrize() {
		return discountedPrize;
	}

	public void setDiscountedPrize(double discountedPrize) {
		this.discountedPrize = discountedPrize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrize() {
		return prize;
	}

	public void setPrize(double prize) {
		this.prize = prize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}


	public JSONObject getItemJson(String product) throws JSONException{
		JSONObject json=new JSONObject();
		json.put("itemId", this.id);
		json.put("product",product);
		json.put("itemName", this.name);
		json.put("itemMRP", this.prize);
		json.put("itemPrize", this.discountedPrize);
		json.put("itemQuantity", this.quantity);
		return json;
	}

	
}

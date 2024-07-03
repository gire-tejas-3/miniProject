package com.ecommerce.model;

import java.util.Date;

public class Order {
	private int id;
	private int user_id;
	private double totalAmount;
	private Date date;

	public Order() {

	}

	public Order(int id, int user_id, double totalAmount, Date date) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.totalAmount = totalAmount;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user_id=" + user_id + ", totalAmount=" + totalAmount + ", date=" + date + "]";
	}
}

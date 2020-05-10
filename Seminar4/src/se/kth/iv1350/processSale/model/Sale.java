package se.kth.iv1350.processSale.model;

import java.time.LocalDate;
import java.time.LocalTime;

import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemResponseDTO;

/**
 * Represents one particular sale transaction, where one particular collection of goods
 * is bought by one particular customer. 
 */
public class Sale {
	
	private LocalDate date;
	private LocalTime time;
	private double runningTotal;
	private Goods goods;
	
	/**
	 * Creates a new instance, representing a sale of a set of <code>Goods</code> for a particular customer at a particular time.
	 */
	public Sale() {
		setDateAndTime();
		this.goods=new Goods();
	}
	
	private void setDateAndTime() {
		this.date=LocalDate.now();
		this.time=LocalTime.now();
	}
	
	/**
	 * Registers an item to the sale, increases the running total, and returns information.
	 * 
	 * @param itemToRegister	The item to add to this sale.
	 * @return	information about this item, the price for the entire set of this item in this sale, as well as the running total.
	 */
	public ItemResponseDTO addItem(ItemRegistrationDTO itemToRegister) {
		double priceXQuantity=goods.addItem(itemToRegister);
		runningTotal=goods.calculateRunningTotal();
		ItemResponseDTO itemInfo=new ItemResponseDTO(itemToRegister, priceXQuantity, runningTotal);
		return itemInfo;
	}
	
	/**
	 * Reduces the total price after the discount has been applied.
	 * 
	 * @param discountedTotalPrice The new total price for the sale.
	 */
	public void reducePrice(double discountedTotalPrice) {
		runningTotal=discountedTotalPrice;
	}
	
	/**
	 * Associates a payment to the sale. Pays for the total price of the sale and returns the excess as change.
	 * 
	 * @param payment	The payment to pay for this sale.
	 * @return	The change for the sale.
	 */
	public double pay(Payment payment) {
		double change=payment.calculateChange(this);
		return change;
	}

	/**
	 * Get the value of date.
	 * 
	 * @return the value of date.
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Get the value of time.
	 * 
	 * @return the value of time.
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * Get the value of runningTotal.
	 * 
	 * @return the value of runningTotal.
	 */
	public double getRunningTotal() {
		return runningTotal;
	}

	/**
	 * Get the value of goods.
	 * 
	 * @return the value of goods.
	 */
	public Goods getGoods() {
		return goods;
	}

}

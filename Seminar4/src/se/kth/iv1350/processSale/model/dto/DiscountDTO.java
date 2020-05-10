package se.kth.iv1350.processSale.model.dto;

import se.kth.iv1350.processSale.model.Sale;

/**
 * Contains the necessary data to apply discount
 */
public class DiscountDTO {
	
	private final Sale sale;
	private final int customerId;
	
	/**
	 * creates a new instance.
	 * 
	 * @param sale The sale that the discount will apply to.
	 * @param customerId The identity of the customer.
	 */
	public DiscountDTO(Sale sale,int customerId) {
		this.sale=sale;
		this.customerId=customerId;
	}
	
	/**
	 * Get the value of sale.
	 * 
	 * @return the value of sale.
	 */
	public Sale getSale() {
		return sale;
	}
	
	/**
	 * Get the value of customerId.
	 * 
	 * @return the value of customerId.
	 */
	public int getCustomerId() {
		return customerId;
	}

}

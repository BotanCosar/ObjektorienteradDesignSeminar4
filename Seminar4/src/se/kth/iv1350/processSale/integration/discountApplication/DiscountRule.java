package se.kth.iv1350.processSale.integration.discountApplication;

import se.kth.iv1350.processSale.model.dto.DiscountDTO;

/**
 *	An interface for the ability to apply a discount given different criteria. This interface shall be
 *	implemented by a class providing the ability to calculate a discounted price for the sale.
 */
public interface DiscountRule {
	
	/**
	 * Calculates a new price by looking at sale information and/or the customer id.
	 * 
	 * @param discount The information needed to retrieve a discounted amount.
	 * @return	The discounted amount.
	 */
	public double calculateNewPrice(DiscountDTO discount);
}

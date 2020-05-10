package se.kth.iv1350.processSale.integration;

import java.util.List;

import se.kth.iv1350.processSale.integration.discountApplication.DiscountFactory;
import se.kth.iv1350.processSale.integration.discountApplication.DiscountRule;
import se.kth.iv1350.processSale.model.dto.DiscountDTO;

/**
 *  Responsible for handling discount requests.
 */
public class DiscountSystem {
	
	private double findBestDiscount(DiscountDTO discount,List<DiscountRule> discountRules) {
		double price=discount.getSale().getRunningTotal();
		for(DiscountRule discountRule:discountRules) {
			double priceAfterDiscount=discountRule.calculateNewPrice(discount);
			if(priceAfterDiscount<price) {
				price=priceAfterDiscount;
			}
		}
		return price;
	}
	
	/**
	 * Uses the information from the sale to retrieve the best discounted price.
	 * 
	 * @param discount The information needed to calculate the discount.
	 * @return The discounted amount.
	 */
	public double applyDiscount(DiscountDTO discount) {
		DiscountFactory discountFactory=DiscountFactory.getDiscountFactory();
		List<DiscountRule> discountRules=discountFactory.getDiscountRules();
		double discountedAmount=findBestDiscount(discount, discountRules);
		return discountedAmount;
	}

}

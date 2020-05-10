package se.kth.iv1350.processSale.integration.discountApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * A singleton that creates instances of the discount rules used to calculate the discount.
 */
public class DiscountFactory {
	
	private static final DiscountFactory DISCOUNT_FACTORY=new DiscountFactory();
	private List<DiscountRule> discountRules=new ArrayList<>();

	private DiscountFactory() {
		discountRules.add(new MembershipDiscount());
		discountRules.add(new BulkDiscount());
	}
	
	/**
	 * Gets the only instance of this singleton.
	 * 
	 * @return The only instance of this singleton.
	 */
	public static DiscountFactory getDiscountFactory() {
		return DISCOUNT_FACTORY;
	}
	
	/**
	 * Get the value of discountRules.
	 * 
	 * @return the value of discountRules.
	 */
	public List<DiscountRule> getDiscountRules() {
		return discountRules;
	}

}

package se.kth.iv1350.processSale.integration.discountApplication;

import se.kth.iv1350.processSale.model.dto.DiscountDTO;

/**
 * A type of discount rule that applies discounts to sales where the total quantity of goods bought exceeds a
 * certain amount. Hard coded to 50 for this program.
 */
class BulkDiscount implements DiscountRule {
	
	/**
	 * Creates a new instance.
	 */
	BulkDiscount() {
	}
	
	private int QuantityOfItems(DiscountDTO discount) {
		int totalQuantity=0;
		for(int i=0;i<discount.getSale().getGoods().getItems().size();i++) {
			totalQuantity+=discount.getSale().getGoods().getItems().get(i).getQuantity();
		}
		return totalQuantity;
	}
	
	private double applyPercentageDiscount(DiscountDTO discount) {
		return discount.getSale().getRunningTotal()*0.8;
	}

	@Override
	public double calculateNewPrice(DiscountDTO discount) {
		if(QuantityOfItems(discount)>=50) {
			return applyPercentageDiscount(discount);
		}
		return discount.getSale().getRunningTotal();
	}
	
}

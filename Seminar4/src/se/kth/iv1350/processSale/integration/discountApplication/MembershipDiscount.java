package se.kth.iv1350.processSale.integration.discountApplication;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.processSale.model.dto.DiscountDTO;

/**
 * A type of discount rule that applies discounts to sales where the customer has a membership. The membership
 * list has been hard coded in this program. 
 */
class MembershipDiscount implements DiscountRule{
	
	private List<Integer> memberIds=new ArrayList<>();
	
	/**
	 * creates a new instance.
	 */
	MembershipDiscount() {
		addMembers();
	}
	
	private void addMembers() {
		memberIds.add(47);
		memberIds.add(264);
		memberIds.add(345);
		memberIds.add(2);
		memberIds.add(735);
	}
	
	private double applyPercentageDiscount(DiscountDTO discount) {
		return discount.getSale().getRunningTotal()*0.9;
	}

	@Override
	public double calculateNewPrice(DiscountDTO discount) {
		for(int member:memberIds) {
			if(discount.getCustomerId()==member) {
				return applyPercentageDiscount(discount);
			}
		}
		return discount.getSale().getRunningTotal();
	}

}

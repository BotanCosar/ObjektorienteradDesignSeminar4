package se.kth.iv1350.processSale.view;

import se.kth.iv1350.processSale.integration.RevenueObserver;

/**
 *	Represents a display that shows the total revenue since the program has started.
 */
class TotalRevenueView implements RevenueObserver {
	
	/**
	 * Prints the total revenue to the screen.
	 */
	@Override
	public void revenueHasChanged(double balance) {
		System.out.println("---Total Revenue---\n"+String.format("%.2f",balance)+"\n");		
	}

}

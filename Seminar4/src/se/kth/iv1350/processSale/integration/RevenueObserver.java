package se.kth.iv1350.processSale.integration;

/**
 * An interface for receiving notifications for changes to the total revenue. The class that is interested
 * in such notifications implements this interface, and the object created with that class is
 * registered with {@link se.kth.iv1350.integration.CashRegister#addRevenueObserver(RevenueObserver)}.
 * When a sale has been paid for, that object's {@link #revenueHasChanged revenueHasChanged} method is invoked.
 */
public interface RevenueObserver {
	/**
	 * Invoked when a payment has been added to the cash register.
	 * 
	 * @param balance The total revenue.
	 */
	public void revenueHasChanged(double balance);
}

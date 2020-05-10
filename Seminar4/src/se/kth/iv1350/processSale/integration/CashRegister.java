package se.kth.iv1350.processSale.integration;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.processSale.model.Payment;

/**
 * Represents a cash register. There shall be one instance of this class for
 * each register.
 */
public class CashRegister {
	
	private double balance;
	private List<RevenueObserver> revenueObservers=new ArrayList<>();
	
	private void notifyObservers() {
		for(RevenueObserver obs:revenueObservers) {
			obs.revenueHasChanged(balance);
		}
	}
	
	/**
	 * Increases the amount of money stored in the register by the amount entered in the payment. Notifies
	 * observers that the total revenue has changed.
	 * 
	 * @param payment The amount to add to the balance.
	 */
	public void addPayment(Payment payment) {
		balance+=payment.getAmountPaid()-payment.getChange();
		notifyObservers();
	}
	
	/**
	 * Adds a new observer to the list of observers for this class.
	 * 
	 * @param revenueObserver the observer to notify.
	 */
	public void addRevenueObserver(RevenueObserver revenueObserver) {
		revenueObservers.add(revenueObserver);
	}
	
	
	/**
	 * Get the value of balance.
	 * 
	 * @return the value of balance.
	 */
	public double getBalance() {
		return balance;
	}

}

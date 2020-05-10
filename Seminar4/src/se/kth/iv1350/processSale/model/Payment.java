package se.kth.iv1350.processSale.model;

import se.kth.iv1350.processSale.model.dto.ReceiptDTO;
import se.kth.iv1350.processSale.model.dto.StoreDTO;

/**
 * Represents a payment for one particular sale.
 */
public class Payment {
	
	private double amountPaid;
	private double change;
	private Sale sale;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param amountPaid The amount of money paid by the customer.
	 */
	public Payment(double amountPaid) {
		this.amountPaid=amountPaid;
	}
	
	/**
	 * Saves information of the transaction in the form of a receipt.
	 * 
	 * @param store Used to enter store data into the receipt.
	 * @return	The receipt, proving the payment.
	 */
	public ReceiptDTO createReceipt(StoreDTO store) {
		ReceiptDTO receipt=new ReceiptDTO(store,this);
		return receipt;
	}
	
	/**
	 * Calculates the amount of change the customer should receive at the end of this transaction.
	 * 
	 * @param sale The sale that is being paid for.
	 * @return	The amount of change to receive.
	 */
	double calculateChange(Sale sale) {
		this.sale=sale;
		this.change=amountPaid-sale.getRunningTotal();
		return change;
	}
	
	/**
	 * Get the value of amountPaid.
	 * 
	 * @return the value of amountPaid.
	 */
	public double getAmountPaid() {
		return amountPaid;
	}
	
	/**
	 * Get the value of change.
	 * 
	 * @return the value of change.
	 */
	public double getChange() {
		return change;
	}
	
	/**
	 * Get the value of sale.
	 * 
	 * @return the value of sale.
	 */
	public Sale getSale() {
		return sale;
	}

}

package se.kth.iv1350.processSale.integration;

import se.kth.iv1350.processSale.model.dto.ReceiptDTO;

/**
 *	Handles all class calls to the external accounting system.
 */
public class AccountingSystem {
	
	/**
	 * Creates a new instance.
	 */
	AccountingSystem() {	
	}
	
	/**
	 * Logs sale information to the accounting system.
	 * @param receipt Holds all necessary data related to the sale.
	 */
	public void updateAccounting(ReceiptDTO receipt) {
		// code that updates the external accounting system goes here.
	}

}

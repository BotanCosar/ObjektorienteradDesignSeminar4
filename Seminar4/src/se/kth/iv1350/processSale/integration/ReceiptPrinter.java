package se.kth.iv1350.processSale.integration;

import se.kth.iv1350.processSale.model.dto.ReceiptDTO;

/**
 * The interface to the printer, used for all printouts initiated by this
 * program.
 */
public class ReceiptPrinter {
	
    /**
     * Prints the specified receipt. Implemented with dummy code that prints to <code>System.out</code>.
     *
     * @param receipt Holds all necessary data related to the sale.
     */
	public void print(ReceiptDTO receipt) {
		System.out.println(receipt.createReceiptString());
	}

}

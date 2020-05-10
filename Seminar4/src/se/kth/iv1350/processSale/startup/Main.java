package se.kth.iv1350.processSale.startup;

import se.kth.iv1350.processSale.controller.Controller;
import se.kth.iv1350.processSale.integration.ReceiptPrinter;
import se.kth.iv1350.processSale.integration.SystemCreator;
import se.kth.iv1350.processSale.view.View;

/**
 * Contains the <code>main</code> method. Performs all startup of the
 * application.
 */
public class Main {

	/**
     * Starts the application.
     *
     * @param args The application does not take any command line parameters.
     */
	public static void main(String[] args){
		SystemCreator systemCreator=SystemCreator.getSystemCreator();
		ReceiptPrinter receiptPrinter=new ReceiptPrinter();
		Controller controller=new Controller(systemCreator,receiptPrinter);
		View view=new View(controller);
		view.executeExampleSale();
	}

}

package se.kth.iv1350.processSale.controller;

import se.kth.iv1350.processSale.integration.AccountingSystem;
import se.kth.iv1350.processSale.integration.CashRegister;
import se.kth.iv1350.processSale.integration.DBConnectionFailureException;
import se.kth.iv1350.processSale.integration.DiscountSystem;
import se.kth.iv1350.processSale.integration.InvalidIdentifierException;
import se.kth.iv1350.processSale.integration.InventorySystem;
import se.kth.iv1350.processSale.integration.ReceiptPrinter;
import se.kth.iv1350.processSale.integration.RevenueObserver;
import se.kth.iv1350.processSale.integration.SystemCreator;
import se.kth.iv1350.processSale.model.Payment;
import se.kth.iv1350.processSale.model.Sale;
import se.kth.iv1350.processSale.model.dto.DiscountDTO;
import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemResponseDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;
import se.kth.iv1350.processSale.model.dto.ReceiptDTO;
import se.kth.iv1350.processSale.model.dto.StoreDTO;
import se.kth.iv1350.processSale.util.LogHandler;

/**
 * Handles all communication between view, model, and integration layer classes.
 */
public class Controller {
	
	private ReceiptPrinter receiptPrinter;
	private InventorySystem inventorySystem;
	private AccountingSystem accountingSystem;
	private DiscountSystem discountSystem;
	private CashRegister cashRegister;
	private StoreDTO store;
	private Sale sale;
	private LogHandler logHandler;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param systemCreator responsible for getting classes that handle calls to external systems
	 * @param receiptPrinter responsible for handling calls to the external printer
	 */
	public Controller(SystemCreator systemCreator,ReceiptPrinter receiptPrinter) {
		this.receiptPrinter=receiptPrinter;
		this.inventorySystem=systemCreator.getInventorySystem();
		this.accountingSystem=systemCreator.getAccountingSystem();
		this.discountSystem=systemCreator.getDiscountSystem();
		this.cashRegister=new CashRegister();
		this.store=new StoreDTO();
		this.logHandler=LogHandler.getLogHandler();
	}
	
	/**
	 * Creates a new sale with related initialization steps. 
	 */
	public void startSale() {
		this.sale=new Sale();
	}
	
	/**
	 * Uses an {@link ItemRetrievalDTO} to find an item from the inventory system, add a specific quantity of it 
	 * to the current sale, and return complete information of the retrieval.
	 *  
	 * @param itemRetrieval An object containing the item's identifier as well as the quantity to add to the sale.
	 * @return information of this item and its retrieval. Includes price, VAT rate, description, 
	 * as well as running total of the sale.
	 * @throws OperationFailedException if any exception has been thrown in the lower layers.
	 */
	public ItemResponseDTO addItem(ItemRetrievalDTO itemRetrieval) throws OperationFailedException {
		try {
			ItemDescriptionDTO itemDescription=inventorySystem.getItem(itemRetrieval);
			ItemRegistrationDTO itemToRegister=new ItemRegistrationDTO(itemRetrieval,itemDescription);
			ItemResponseDTO itemInfo=sale.addItem(itemToRegister);
			return itemInfo;
		}
		catch(InvalidIdentifierException e) {
			throw new OperationFailedException("Could not add specified item with identifier: "
												+e.getItemRetrieval().getItemIdentifier()
												+". Make sure you have entered"
												+" the correct identifier.",e);
		}
		catch(DBConnectionFailureException e) {
			logHandler.logException(e);
			throw new OperationFailedException("Could not add specified item, try again.",e);
		}
		
	}
	
	
	/**
	 * Ends the current sale and returns the total price.
	 * @return the total price of the sale.
	 */
	public double endSale() {
		double totalPrice=sale.getRunningTotal();
		return totalPrice;
	}
	
	/**
	 * Applies a discount to the current sale, corresponding to the customer id and the sale itself.
	 * 
	 * @param customerId The identity of the customer
	 * @return The price for the sale after the discount has been applied.
	 */
	public double applyDiscount(int customerId) {
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double discountedTotalPrice=discountSystem.applyDiscount(discount);
		sale.reducePrice(discountedTotalPrice);
		return discountedTotalPrice;
	}
	
	
	/**
	 * Enters an amount of money to pay for the sale. The payment is added to the cash register. A log of the transaction is sent to
	 * the inventory and accounting systems, and a receipt is printed. The excess is returned as change.
	 * @param amountPaid The amount of money paid for the sale.
	 * @return The amount of change to receive back.
	 */
	public double enterPayment(double amountPaid) {
		Payment payment=new Payment(amountPaid);
		double change=sale.pay(payment);
		ReceiptDTO receipt=payment.createReceipt(store);
		inventorySystem.updateInventory(receipt);
		accountingSystem.updateAccounting(receipt);
		receiptPrinter.print(receipt);
		cashRegister.addPayment(payment);
		return change;
	}
	
	/**
	 * Adds a new observer to the list of observers for the <code>CashRegister</code> class.
	 * 
	 * @param revenueObserver the observer to notify
	 */
	public void addRevenueObserver(RevenueObserver revenueObserver) {
		cashRegister.addRevenueObserver(revenueObserver);
	}

}

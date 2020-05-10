package se.kth.iv1350.processSale.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import se.kth.iv1350.processSale.model.Item;
import se.kth.iv1350.processSale.model.Payment;

/**
 * Contains complete information of the sale.
 */
public class ReceiptDTO {
	
	private final StoreDTO store;
	private final Payment payment;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param store	The store that the sale took place in.
	 * @param payment	The payment of the sale.
	 */
	public ReceiptDTO(StoreDTO store,Payment payment) {
		this.store=store;
		this.payment=payment;
	}
	
	/**
     * Creates a well-formatted string with the entire content of the receipt.
     *
     * @return The well-formatted receipt string.
     */
	public String createReceiptString() {
	    	
	    	LocalDate date=payment.getSale().getDate();
			LocalTime time=payment.getSale().getTime();
			List<Item> items=payment.getSale().getGoods().getItems();
			double totalPrice=payment.getSale().getRunningTotal();
			double amountPaid=payment.getAmountPaid();
			double change=payment.getChange();
			String storeName=store.getName();
			String storeAddress=store.getAddress();
	    	
	    	StringBuilder builder = new StringBuilder();
	        appendLine(builder, "---Receipt---");
	        endSection(builder);
	
	        builder.append("Sale date: ");
	        appendLine(builder, date.toString());
	        builder.append("Sale time: ");
	        appendLine(builder, time.toString());
	        endSection(builder);
	        
	        builder.append("Store name: ");
	        appendLine(builder, storeName);
	        builder.append("Store address: ");
	        appendLine(builder, storeAddress);
	        endSection(builder);
	        
	        builder.append("Items: ");
	        endSection(builder);
	        for(Item item:items) {
	        	appendLine(builder, "Name: "+item.getItemDescription().getName()
	        						+", Quantity: "+item.getQuantity()
	        						+", Price: "+item.getItemDescription().getPrice()
	        						+", Total this item: "+item.getItemDescription().getPrice()*item.getQuantity()
	        						+", VAT rate: "+item.getItemDescription().getVatRate());
	        }
	        endSection(builder);
	        
	        builder.append("Total price: ");
	        appendLine(builder, String.format("%.2f", totalPrice));
	        builder.append("Amount paid: ");
	        appendLine(builder, String.format("%.2f",amountPaid));
	        builder.append("Change: ");
	        appendLine(builder, String.format("%.2f",change));
	        endSection(builder);
	        
	        return builder.toString();
	    }
	
	    private void appendLine(StringBuilder builder, String line) {
	        builder.append(line);
	        builder.append("\n");
	    }
	
	    private void endSection(StringBuilder builder) {
	        builder.append("\n");
	    }
	    
	    /**
		 * Get the value of store.
		 * 
		 * @return the value of store.
		 */
	    public StoreDTO getStore() {
	    	return store;
	    }
	    
	    /**
		 * Get the value of payment.
		 * 
		 * @return the value of payment.
		 */
	    public Payment getPayment() {
	    	return payment;
	    }
	
		
}

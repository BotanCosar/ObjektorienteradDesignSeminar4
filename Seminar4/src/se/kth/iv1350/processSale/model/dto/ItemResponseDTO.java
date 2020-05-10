package se.kth.iv1350.processSale.model.dto;

/**
 * Contains information of the item that was registered, the price of the entire set of this item in this sale, as well as the running
 * total of the sale.
 */
public class ItemResponseDTO {
	
	private final ItemRegistrationDTO itemToRegister;
	private final double priceXQuantity;
	private final double runningTotal;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param itemToRegister The item that was registered in the sale.
	 * @param priceXQuantity The price of this item multiplied by its quantity for this sale. 
	 * @param runningTotal	The running total of the sale.
	 */
	public ItemResponseDTO(ItemRegistrationDTO itemToRegister,double priceXQuantity,double runningTotal) {
		this.itemToRegister=itemToRegister;
		this.priceXQuantity=priceXQuantity;
		this.runningTotal=runningTotal;
	}
	
	/**
	 * Get the value of itemToRegister.
	 * 
	 * @return the value of itemToRegister.
	 */
	public ItemRegistrationDTO getItemToRegister() {
		return itemToRegister;
	}
	
	/**
	 * Get the value of priceXQuantity.
	 * 
	 * @return the value of priceXQuantity.
	 */
	public double getPriceXQuantity() {
		return priceXQuantity;
	}
	
	/**
	 * Get the value of runningTotal.
	 * 
	 * @return the value of runningTotal.
	 */
	public double getRunningTotal() {
		return runningTotal;
	}

}

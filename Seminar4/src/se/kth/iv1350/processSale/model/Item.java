package se.kth.iv1350.processSale.model;

import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;

/**
 * Represents an item that has been added to the sale.
 */
public class Item {
	
	private ItemDescriptionDTO itemDescription;
	private int quantity;
	
	/**
	 * Creates a new instance. Each instance holds the item's description and the quantity added to this sale.
	 * 
	 * @param itemToRegister The item to be registered.
	 */
	Item(ItemRegistrationDTO itemToRegister) {
		this.itemDescription=itemToRegister.getItemDescription();
		this.quantity=itemToRegister.getItemRetrieval().getQuantity();
	}
	
	/**
	 * Updates the quantity of the item by adding the new quantity to the existing one.
	 * 
	 * @param itemToRegister Holds the new quantity of the item.
	 */
	void updateQuantity(ItemRegistrationDTO itemToRegister) {
		this.quantity+=itemToRegister.getItemRetrieval().getQuantity();
	}
	
	/**
	 * Get the value of itemDescription.
	 * 
	 * @return the value of itemDescription.
	 */
	public ItemDescriptionDTO getItemDescription() {
		return itemDescription;
	}
	
	/**
	 * Get the value of quantity
	 * @return the value of quantity
	 */
	public int getQuantity() {
		return quantity;
	}

}

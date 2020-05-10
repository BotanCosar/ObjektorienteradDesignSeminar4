package se.kth.iv1350.processSale.model;

import java.util.ArrayList;
import java.util.List;

import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;

/**
 * Represents a collection of items registered in this sale.
 */
public class Goods {
	
	private List<Item> items;
	
	/**
	 * Creates a new instance.
	 */
	Goods() {
		this.items=new ArrayList<>();
	}
	
	private Item matchItem(ItemRegistrationDTO itemToRegister) {
		for(Item enteredItem:items) {
			if(itemToRegister.getItemDescription().getId()==enteredItem.getItemDescription().getId()){
				return enteredItem;
			}
		}
		return null;
	}
	
	private double calculateActualVatRate(Item enteredItem) {
		return (enteredItem.getItemDescription().getVatRate()/100)+1;
	}
	
	private double costAfterVatRate(Item enteredItem) {
		double actualVatRate=calculateActualVatRate(enteredItem);
		return calculatePriceXQuantity(enteredItem)*actualVatRate;
	}
	
	private double calculatePriceXQuantity(Item item) {
		double price=item.getItemDescription().getPrice();
		int quantity=item.getQuantity();
		return price*quantity;
	}
	
	/**
	 * calculates the total price of the current sale at a particular point in time.
	 * 
	 * @return The running total of the sale.
	 */
	double calculateRunningTotal() {
		double runningTotal=0;
		for(Item enteredItem:items) {
			runningTotal+=costAfterVatRate(enteredItem);
		}
		return runningTotal;
	}
	
	
	/**
	 * Stores the new item. If the item had already been entered, the existing item's quantity is updated instead. Returns the price
	 * for the entire set of this item.
	 * 
	 * @param itemToRegister The item to store in the sale.
	 * @return	the price multiplied by the quantity for this item.
	 */
	double addItem(ItemRegistrationDTO itemToRegister) {
		Item matchedItem=matchItem(itemToRegister);
		if(matchedItem!=null) {
			matchedItem.updateQuantity(itemToRegister);
			return calculatePriceXQuantity(matchedItem);
		}
		else {
			Item item=new Item(itemToRegister);
			items.add(item);
			return calculatePriceXQuantity(item);
		}		
	}
	
	/**
	 * Get the value of items.
	 * 
	 * @return the value of items.
	 */
	public List<Item> getItems() {
		return items;
	}

}

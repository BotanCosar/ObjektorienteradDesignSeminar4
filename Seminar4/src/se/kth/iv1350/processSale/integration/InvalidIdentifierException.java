package se.kth.iv1350.processSale.integration;

import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

/**
 * Thrown when the item identifier doesn't match an item in the inventory.
 */
public class InvalidIdentifierException extends Exception{
	
	private ItemRetrievalDTO itemRetrieval;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param itemRetrieval The retrieval carrying the invalid item identifier.
	 */
	InvalidIdentifierException(ItemRetrievalDTO itemRetrieval) {
		super("Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found.");
		this.itemRetrieval=itemRetrieval;
	}
	
	/**
	 * Get the value of itemRetrieval.
	 * 
	 * @return the value of itemRetrieval.
	 */
	public ItemRetrievalDTO getItemRetrieval() {
		return itemRetrieval;
	}

}

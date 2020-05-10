package se.kth.iv1350.processSale.model.dto;

/**
 * Contains the identifier of the item that is to be queried, as well as the quantity that shall be added to this sale. 
 */
public class ItemRetrievalDTO {
	
	private final int itemIdentifier;
	private final int quantity;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param itemIdentifier The identifier of the item.
	 * @param quantity	The quantity to add to this sale.
	 */
	public ItemRetrievalDTO(int itemIdentifier,int quantity) {
		this.itemIdentifier=itemIdentifier;
		this.quantity=quantity;
	}
	
	/**
	 * Get the value of itemIdentifier.
	 * 
	 * @return the value of itemIdentifier.
	 */
	public int getItemIdentifier() {
		return itemIdentifier;
	}
	
	/**
	 * Get the value of quantity.
	 * 
	 * @return the value of quantity.
	 */
	public int getQuantity() {
		return quantity;
	}

}

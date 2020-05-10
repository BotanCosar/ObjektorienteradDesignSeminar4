package se.kth.iv1350.processSale.model.dto;

/**
 * Contains information about the store.
 */
public class StoreDTO {
	
	private final String name;
	private final String address;
	
	/**
	 * Creates a new instance. The store's name and address are hard coded.
	 */
	public StoreDTO() {
		this.name="Hemköp";
		this.address="Kistavägen 23";
	}
	
	/**
	 * Get the value of name.
	 * 
	 * @return the value of name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get the value of address.
	 * 
	 * @return the value of address.
	 */
	public String getAddress() {
		return address;
	}
	
}

package se.kth.iv1350.processSale.model.dto;

/**
 * Contains the item description of one particular item
 */
public class ItemDescriptionDTO {
	
	private final double vatRate;
	private final String name;
	private final int id;
	private final double price;
	
	/**
	 * Creates a new instance.
	 * 
	 * @param vatRate The item's VAT rate.
	 * @param name The name of the item.
	 * @param id The id of the item.
	 * @param price The price of the item.
	 */
	public ItemDescriptionDTO(double vatRate,String name,int id, double price) {
		this.vatRate=vatRate;
		this.name=name;
		this.id=id;
		this.price=price;
	}
	
	/**
	 * Get the value of vatRate.
	 * 
	 * @return the value of vatRate.
	 */
	public double getVatRate() {
		return vatRate;
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
	 * Get the value of id.
	 * 
	 * @return the value of id.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the value of price.
	 * 
	 * @return the value of price.
	 */
	public double getPrice() {
		return price;
	}

}

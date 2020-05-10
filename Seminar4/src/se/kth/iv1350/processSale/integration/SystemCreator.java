package se.kth.iv1350.processSale.integration;

/**
 * This singleton class is responsible for instantiating all external systems.
 */
public class SystemCreator {
	
	private static final SystemCreator SYSTEM_CREATOR=new SystemCreator();	
	private InventorySystem inventorySystem;
	private AccountingSystem accountingSystem;
	private DiscountSystem discountSystem;
	
	private SystemCreator() {
		this.inventorySystem=new InventorySystem();
		this.accountingSystem=new AccountingSystem();
		this.discountSystem=new DiscountSystem();
	}
	
	/**
	 * Gets the only instance of this singleton.
	 * 
	 * @return The only instance of this singleton.
	 */
	public static SystemCreator getSystemCreator() {
		return SYSTEM_CREATOR;
	}
	
	/**
	 * Get the value of inventorySystem.
	 * 
	 * @return the value of inventorySystem.
	 */
	public InventorySystem getInventorySystem() {
		return inventorySystem;
	}
	
	/**
	 * Get the value of accountingSystem.
	 * 
	 * @return the value of accountingSystem.
	 */
	public AccountingSystem getAccountingSystem() {
		return accountingSystem;
	}
	
	/**
	 * Get the value of discountSystem.
	 * 
	 * @return the value of discountSystem.
	 */
	public DiscountSystem getDiscountSystem() {
		return discountSystem;
	}

}

package se.kth.iv1350.processSale.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.integration.ReceiptPrinter;
import se.kth.iv1350.processSale.integration.SystemCreator;
import se.kth.iv1350.processSale.model.dto.ItemResponseDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

class ControllerTest {
	
	private SystemCreator systemCreator;
	private ReceiptPrinter receiptPrinter;
	private Controller instance;
	
	@BeforeEach
	void setUp() throws Exception {
		systemCreator=SystemCreator.getSystemCreator();
		receiptPrinter=new ReceiptPrinter();
		instance=new Controller(systemCreator,receiptPrinter);
	}

	@AfterEach
	void tearDown() throws Exception {
		systemCreator=null;
		receiptPrinter=null;
		instance=null;
	}
	
	@Test
	void testAddItem() {
		instance.startSale();
		int itemIdentifier=45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		int expResult=45;
		
		try {
			ItemResponseDTO itemInfo=instance.addItem(itemRetrieval);
			int result=itemInfo.getItemToRegister().getItemDescription().getId();
			assertEquals(expResult,result,"Correct item could not be retrieved");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testAddItemBeforeStartingSale() {
		int itemIdentifier=45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			instance.addItem(itemRetrieval);
			fail("Successfully added an item without starting a new sale");
		}
		catch(OperationFailedException e) {
			fail("Reached incorrect exception");
		}
		catch(NullPointerException e) {
		}
	}
	
	@Test
	void testAddItemInvalidIdentifier() {
		instance.startSale();
		int itemIdentifier=777;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			instance.addItem(itemRetrieval);
			fail("Successfully added invalid item");
		}
		catch(OperationFailedException e) {
			assertTrue(e.getCause().getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testAddItemZeroIdentifier() {
		instance.startSale();
		int itemIdentifier=0;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			instance.addItem(itemRetrieval);
			fail("Successfully added item with identifier 0");
		}
		catch(OperationFailedException e) {
			assertTrue(e.getCause().getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
		
	}
	
	@Test
	void testAddItemNegativeIdentifier() {
		instance.startSale();
		int itemIdentifier=-45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			instance.addItem(itemRetrieval);
			fail("Successfully added item with negative identifier");
		}
		catch(OperationFailedException e) {
			assertTrue(e.getCause().getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testAddItemIntegerMax() {
		instance.startSale();
		int itemIdentifier=Integer.MAX_VALUE;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			instance.addItem(itemRetrieval);
			fail("Successfully added invalid item");
		}
		catch(OperationFailedException e) {
			assertTrue(e.getCause().getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testAddItemIntegerMin() {
		instance.startSale();
		int itemIdentifier=Integer.MIN_VALUE;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			instance.addItem(itemRetrieval);
			fail("Successfully added invalid item");
		}
		catch(OperationFailedException e) {
			assertTrue(e.getCause().getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testAddItemDatabaseFailure() {
		instance.startSale();
		int itemIdentifier=666;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			instance.addItem(itemRetrieval);
			fail("Successfully retrieved item without database connection");
		}
		catch(OperationFailedException e) {
			assertTrue(e.getCause().getMessage().contains(
					"Failed to connect to database"),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testEndSaleNoItemsAdded() {
		instance.startSale();
		double expResult=0;
		double result=instance.endSale();
		assertEquals(expResult,result,"Wrong total price");
	}
	
	@Test
	void testEndSaleOneItemAdded() {
		instance.startSale();
		int itemIdentifier=45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			ItemResponseDTO itemInfo=instance.addItem(itemRetrieval);
			double expResult=itemInfo.getRunningTotal();
			double result=instance.endSale();
			assertEquals(expResult,result,"Wrong total price");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testEndSaleMultipleItemsAdded() {
		instance.startSale();
		int itemIdentifier=45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		ItemResponseDTO itemInfo=null;
		
		try {
			for(int i=0;i<3;i++) {
				itemInfo=instance.addItem(itemRetrieval);
			}
			double expResult=itemInfo.getRunningTotal();
			double result=instance.endSale();
			assertEquals(expResult,result,"Wrong total price");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
		
	}

	@Test
	void testEnterPaymentNoItemNoPayment() {
		double amountPaid=0;
		instance.startSale();
		double totalPrice=instance.endSale();
		
		double expResult=amountPaid-totalPrice;
		double result=instance.enterPayment(amountPaid);

		assertEquals(expResult, result,"Wrong amount");
	}
	
	@Test
	void testEnterPaymentNoItemPayment() {
		double amountPaid=500;
		instance.startSale();
		double totalPrice=instance.endSale();
		
		double expResult=amountPaid-totalPrice;
		double result=instance.enterPayment(amountPaid);

		assertEquals(expResult, result,"Wrong amount");
	}
	
	@Test
	void testEnterPaymentOneItem() {
		double amountPaid=500;
		
		instance.startSale();
		int itemIdentifier=45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			instance.addItem(itemRetrieval);
			double totalPrice=instance.endSale();
			double expResult=amountPaid-totalPrice;
			double result=instance.enterPayment(amountPaid);
			assertEquals(expResult, result,"Wrong amount");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testEnterPaymentMultipleItems() {
		double amountPaid=500;
		
		instance.startSale();
		int itemIdentifier=45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		
		try {
			for(int i=0;i<3;i++) {
				instance.addItem(itemRetrieval);
			}
			double totalPrice=instance.endSale();
			double expResult=amountPaid-totalPrice;
			double result=instance.enterPayment(amountPaid);
			assertEquals(expResult, result,"Wrong amount");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testApplyDiscountOneItemNoDiscount() {
		int customerId=945;
		instance.startSale();
		int itemIdentifier=45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		try {
			instance.addItem(itemRetrieval);
			double expResult=instance.endSale();
			double result=instance.applyDiscount(customerId);
			assertEquals(expResult, result,"Discount failed to apply correctly");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testApplyDiscountMultipleItemsNoDiscount() {
		int customerId=945;
		instance.startSale();
		int itemIdentifier=45;
		int quantity=16;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		try {
			for(int i=0;i<3;i++) {
				instance.addItem(itemRetrieval);
			}
			double expResult=instance.endSale();
			double result=instance.applyDiscount(customerId);
			assertEquals(expResult, result,"Discount failed to apply correctly");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testApplyDiscountOneItemMember() {
		int customerId=735;
		instance.startSale();
		int itemIdentifier=45;
		int quantity=20;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		try {
			instance.addItem(itemRetrieval);
			double expResult=instance.endSale()*0.9;
			double result=instance.applyDiscount(customerId);
			assertEquals(expResult, result,"Discount failed to apply correctly");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testApplyDiscountMultipleItemsMember() {
		int customerId=735;
		instance.startSale();
		int itemIdentifier=45;
		int quantity=16;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		try {
			for(int i=0;i<3;i++) {
				instance.addItem(itemRetrieval);
			}
			double expResult=instance.endSale()*0.9;
			double result=instance.applyDiscount(customerId);
			assertEquals(expResult, result,"Discount failed to apply correctly");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testApplyDiscountOneItemBulk() {
		int customerId=945;
		instance.startSale();
		int itemIdentifier=45;
		int quantity=50;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		try {
			instance.addItem(itemRetrieval);
			double expResult=instance.endSale()*0.8;
			double result=instance.applyDiscount(customerId);
			assertEquals(expResult, result,"Discount failed to apply correctly");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testApplyDiscountMultipleItemsBulk() {
		int customerId=945;
		instance.startSale();
		int itemIdentifier=45;
		int quantity=50;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		try {
			for(int i=0;i<3;i++) {
				instance.addItem(itemRetrieval);
			}
			double expResult=instance.endSale()*0.8;
			double result=instance.applyDiscount(customerId);
			assertEquals(expResult, result,"Discount failed to apply correctly");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testApplyDiscountOneItemMemberAndBulk() {
		int customerId=735;
		instance.startSale();
		int itemIdentifier=45;
		int quantity=50;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		try {
			instance.addItem(itemRetrieval);
			double expResult=instance.endSale()*0.8;
			double result=instance.applyDiscount(customerId);
			assertEquals(expResult, result,"Discount failed to apply correctly");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
	
	@Test
	void testApplyDiscountMultipleItemsMemberAndBulk() {
		int customerId=735;
		instance.startSale();
		int itemIdentifier=45;
		int quantity=50;
		ItemRetrievalDTO itemRetrieval=new ItemRetrievalDTO(itemIdentifier, quantity);
		try {
			for(int i=0;i<3;i++) {
				instance.addItem(itemRetrieval);
			}
			double expResult=instance.endSale()*0.8;
			double result=instance.applyDiscount(customerId);
			assertEquals(expResult, result,"Discount failed to apply correctly");
		}
		catch(OperationFailedException e) {
			fail("Could not add valid item");
		}
	}
}

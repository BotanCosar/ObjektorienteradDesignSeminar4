package se.kth.iv1350.processSale.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemResponseDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

class SaleTest {
	
	private ItemRetrievalDTO itemRetrieval;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;
	private Sale instance;
	
	@BeforeEach
	void setUp() throws Exception {
		itemRetrieval=new ItemRetrievalDTO(45, 5);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		instance=new Sale();
	}

	@AfterEach
	void tearDown() throws Exception {
		itemRetrieval=null;
		itemDescription=null;
		itemToRegister=null;
		instance=null;
	}

	@Test
	void testAddItemOnce() {
		double price=itemDescription.getPrice();
		int quantity=itemRetrieval.getQuantity();
		double expectedPriceXQuantity=price*quantity;
		double actualVatRate=(itemDescription.getVatRate()/100)+1;
		double expectedRunningTotal=expectedPriceXQuantity*actualVatRate;
		ItemResponseDTO expResult=new ItemResponseDTO(itemToRegister, expectedPriceXQuantity, expectedRunningTotal);
		
		ItemResponseDTO result=instance.addItem(itemToRegister);
		assertEquals(expResult.getItemToRegister(),result.getItemToRegister(),"Wrong itemToRegister");
		assertEquals(expResult.getPriceXQuantity(),result.getPriceXQuantity(),"Wrong priceXQuantity");
		assertEquals(expResult.getRunningTotal(),result.getRunningTotal(),"Wrong runningTotal");
	}
	
	@Test
	void testAddItemTwice() {
		double price=itemDescription.getPrice();
		int quantity=itemRetrieval.getQuantity()*2;
		double expectedPriceXQuantity=price*quantity;
		double actualVatRate=(itemDescription.getVatRate()/100)+1;
		double expectedRunningTotal=expectedPriceXQuantity*actualVatRate;
		ItemResponseDTO expResult=new ItemResponseDTO(itemToRegister, expectedPriceXQuantity, expectedRunningTotal);
		
		instance.addItem(itemToRegister);
		ItemResponseDTO result=instance.addItem(itemToRegister);
		assertEquals(expResult.getItemToRegister(),result.getItemToRegister(),"Wrong itemToRegister");
		assertEquals(expResult.getPriceXQuantity(),result.getPriceXQuantity(),"Wrong priceXQuantity");
		assertEquals(expResult.getRunningTotal(),result.getRunningTotal(),"Wrong runningTotal");
	}

	@Test
	void testPayNoItems() {
		double amountPaid=500;
		Payment payment=new Payment(amountPaid);
		double expResult=amountPaid;
		double result=instance.pay(payment);
		assertEquals(expResult,result,"Wrong change");
	}
	
	@Test
	void testPayOneItem() {
		ItemResponseDTO itemInfo=instance.addItem(itemToRegister);
		double amountPaid=500;
		Payment payment=new Payment(amountPaid);
		double expResult=amountPaid-itemInfo.getRunningTotal();
		double result=instance.pay(payment);
		assertEquals(expResult,result,"Wrong change");
	}
	
	@Test
	void testPayMultipleItems() {
		ItemResponseDTO itemInfo=null;
		for(int i=0;i<3;i++) {
			itemInfo=instance.addItem(itemToRegister);
		}
		double amountPaid=500;
		Payment payment=new Payment(amountPaid);
		double expResult=amountPaid-itemInfo.getRunningTotal();
		double result=instance.pay(payment);
		assertEquals(expResult,result,"Wrong change");
	}
	
	@Test
	void testPayExactAmount() {
		ItemResponseDTO itemInfo=instance.addItem(itemToRegister);
		double amountPaid=itemInfo.getRunningTotal();
		Payment payment=new Payment(amountPaid);
		double expResult=0;
		double result=instance.pay(payment);
		assertEquals(expResult,result,"Wrong change");
	}
	
	@Test
	void testPayIntegerMax() {
		ItemResponseDTO itemInfo=instance.addItem(itemToRegister);
		double amountPaid=Integer.MAX_VALUE;
		Payment payment=new Payment(amountPaid);
		double expResult=amountPaid-itemInfo.getRunningTotal();
		double result=instance.pay(payment);
		assertEquals(expResult,result,"Wrong change");
	}

}

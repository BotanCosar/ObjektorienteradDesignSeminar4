package se.kth.iv1350.processSale.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.Sale;
import se.kth.iv1350.processSale.model.dto.DiscountDTO;
import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

class DiscountSystemTest {
	
	private ItemRetrievalDTO itemRetrieval;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;
	private DiscountSystem instance;
	private Sale sale;
	private int customerId;
	
	@BeforeEach
	void setUp() throws Exception {
		customerId=735;
		itemRetrieval=new ItemRetrievalDTO(45, 5);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		instance=new DiscountSystem();
		sale=new Sale();
	}

	@AfterEach
	void tearDown() throws Exception {
		customerId=0;
		itemRetrieval=null;
		itemDescription=null;
		itemToRegister=null;
		instance=null;
		sale=null;
	}

	@Test
	void testApplyDiscountNoItems() {
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal();
		double result=instance.applyDiscount(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testApplyDiscountOneItemMember() {
		sale.addItem(itemToRegister);
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.9;
		double result=instance.applyDiscount(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testApplyDiscountMultipleItemsMember() {
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.9;
		double result=instance.applyDiscount(discount);
		System.out.println(expResult+" "+result);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testApplyDiscountOneItemBulk() {
		customerId=945;
		itemRetrieval=new ItemRetrievalDTO(45, 50);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		sale.addItem(itemToRegister);
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.8;
		double result=instance.applyDiscount(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testApplyDiscountMultipleItemsBulk() {
		customerId=945;
		itemRetrieval=new ItemRetrievalDTO(45, 50);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.8;
		double result=instance.applyDiscount(discount);
		System.out.println(expResult+" "+result);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testApplyDiscountOneItemMemberAndBulk() {
		itemRetrieval=new ItemRetrievalDTO(45, 50);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		sale.addItem(itemToRegister);
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.8;
		double result=instance.applyDiscount(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testApplyDiscountMultipleItemsMemberAndBulk() {
		itemRetrieval=new ItemRetrievalDTO(45, 50);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.8;
		double result=instance.applyDiscount(discount);
		System.out.println(expResult+" "+result);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}

}

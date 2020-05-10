package se.kth.iv1350.processSale.integration.discountApplication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.Sale;
import se.kth.iv1350.processSale.model.dto.DiscountDTO;
import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

class MembershipDiscountTest {
	
	private ItemRetrievalDTO itemRetrieval;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;
	private MembershipDiscount instance;
	private Sale sale;
	
	@BeforeEach
	void setUp() throws Exception {
		itemRetrieval=new ItemRetrievalDTO(45, 5);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		instance=new MembershipDiscount();
		sale=new Sale();
	}

	@AfterEach
	void tearDown() throws Exception {
		itemRetrieval=null;
		itemDescription=null;
		itemToRegister=null;
		instance=null;
		sale=null;
	}

	@Test
	void testCalculateNewPriceNoItems() {
		int customerId=735;
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.9;
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testCalculateNewPriceOneItem() {
		int customerId=735;
		sale.addItem(itemToRegister);
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.9;
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testCalculateNewPriceMultipleItems() {
		int customerId=735;
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.9;
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testCalculateNewPriceNoItemsNotAMember() {
		int customerId=945;
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal();
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount applied for non-member");
	}
	
	@Test
	void testCalculateNewPriceOneItemNotAMember() {
		int customerId=945;
		sale.addItem(itemToRegister);
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal();
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount applied for non-member");
	}
	
	@Test
	void testCalculateNewPriceMultipleItemsNotAMember() {
		int customerId=945;
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal();
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount applied for non-member");
	}

}

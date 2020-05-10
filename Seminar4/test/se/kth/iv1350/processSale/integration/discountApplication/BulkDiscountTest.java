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

class BulkDiscountTest {
	
	private ItemRetrievalDTO itemRetrieval;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;
	private BulkDiscount instance;
	private Sale sale;
	private int customerId;

	@BeforeEach
	void setUp() throws Exception {
		customerId=735;
		itemRetrieval=new ItemRetrievalDTO(45, 50);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		instance=new BulkDiscount();
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
	void testCalculateNewPriceNoItems() {
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.8;
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testCalculateNewPriceOneItem() {
		sale.addItem(itemToRegister);
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.8;
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testCalculateNewPriceMultipleItems() {
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal()*0.8;
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount failed to apply correctly");
	}
	
	@Test
	void testCalculateNewPriceNoItemsNotEnoughGoods() {
		itemRetrieval=new ItemRetrievalDTO(45, 49);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal();
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount applied with too few goods");
	}
	
	@Test
	void testCalculateNewPriceOneItemNotEnoughGoods() {
		itemRetrieval=new ItemRetrievalDTO(45, 49);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		sale.addItem(itemToRegister);
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal();
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount applied with too few goods");
	}
	
	@Test
	void testCalculateNewPriceMultipleItemsNotEnoughGoods() {
		itemRetrieval=new ItemRetrievalDTO(45, 16);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		DiscountDTO discount=new DiscountDTO(sale, customerId);
		double expResult=sale.getRunningTotal();
		double result=instance.calculateNewPrice(discount);
		assertEquals(expResult, result,"Discount applied with too few goods");
	}

}

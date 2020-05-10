package se.kth.iv1350.processSale.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

class GoodsTest {
	
	private ItemRetrievalDTO itemRetrieval;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;
	private Goods instance;
	
	@BeforeEach
	void setUp() throws Exception {
		itemRetrieval=new ItemRetrievalDTO(45, 20);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		instance=new Goods();
	}

	@AfterEach
	void tearDown() throws Exception {
		itemRetrieval=null;
		itemDescription=null;
		itemToRegister=null;
		instance=null;
	}

	@Test
	void testAddItemNew() {
		double price=itemDescription.getPrice();
		int quantity=itemRetrieval.getQuantity();
		double expResult=price*quantity;
		double result=instance.addItem(itemToRegister);
		assertEquals(expResult,result,"Wrong priceXQuantity");
		assertFalse(instance.getItems().isEmpty(),"The item was not saved in the list");
	}
	
	@Test
	void testAddItemAlreadyEntered() {
		instance.addItem(itemToRegister);
		
		double price=itemDescription.getPrice();
		int quantity=itemRetrieval.getQuantity();
		double expResult=price*(quantity*2);
		double result=instance.addItem(itemToRegister);
		assertEquals(expResult,result,"Wrong priceXQuantity");
		assertEquals(instance.getItems().size(),1,"Incorrect amount of items in the list");
	}
	
	@Test
	void testCalculateRunningTotalNoItems() {
		double expResult=0;
		double result=instance.calculateRunningTotal();
		assertEquals(expResult, result,"Running total not 0 when no items");
	}
	
	@Test
	void testCalculateRunningTotalOneItem() {
		instance.addItem(itemToRegister);
		
		Item item=instance.getItems().get(0);
		double actualVatRate=(item.getItemDescription().getVatRate()/100)+1;
		double price=item.getItemDescription().getPrice();
		int quantity=item.getQuantity();
		
		double expResult=price*quantity*actualVatRate;
		double result=instance.calculateRunningTotal();
		assertEquals(expResult, result,"Incorrect running total");
	}
	
	@Test
	void testCalculateRunningTotalMultipleItems() {
		instance.addItem(itemToRegister);
		
		itemRetrieval=new ItemRetrievalDTO(7, 10);
		itemDescription=new ItemDescriptionDTO(6, "spaghetti", 4532, 10);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		
		instance.addItem(itemToRegister);
		Item item=instance.getItems().get(0);
		double actualVatRate=(item.getItemDescription().getVatRate()/100)+1;
		double price=item.getItemDescription().getPrice();
		int quantity=item.getQuantity();
		double firstItemFinalCost=price*quantity*actualVatRate;
		
		item=instance.getItems().get(1);
		actualVatRate=(item.getItemDescription().getVatRate()/100)+1;
		price=item.getItemDescription().getPrice();
		quantity=item.getQuantity();
		double secondItemFinalCost=price*quantity*actualVatRate;
		
		double expResult=firstItemFinalCost+secondItemFinalCost;
		
		double result=instance.calculateRunningTotal();
		assertEquals(expResult, result,"Incorrect running total");
	}

}

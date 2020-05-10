package se.kth.iv1350.processSale.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

class ItemTest {
	
	private ItemRetrievalDTO itemRetrieval;
	private ItemRetrievalDTO itemRetrievalDifferent;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;
	private ItemRegistrationDTO itemToRegisterDifferent;
	private Item instance;
	
	@BeforeEach
	void setUp() throws Exception  {
		itemRetrieval=new ItemRetrievalDTO(45, 20);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		instance=new Item(itemToRegister);
	}
	
	@AfterEach
	void tearDown() throws Exception  {
		itemRetrieval=null;
		itemRetrievalDifferent=null;
		itemDescription=null;
		itemToRegister=null;
		itemToRegisterDifferent=null;
		instance=null;
	}
	
	@Test
	void testUpdateSameQuantity() {
		int beforeUpdate=instance.getQuantity();
		instance.updateQuantity(itemToRegister);
		int expResult=beforeUpdate*2;
		int result=instance.getQuantity();
		assertEquals(expResult,result,"Wrong quantity");
	}
	
	@Test
	void testUpdateDifferentQuantity() {
		int addedQuantity=40;
		itemRetrievalDifferent=new ItemRetrievalDTO(45, addedQuantity);
		itemToRegisterDifferent=new ItemRegistrationDTO(itemRetrievalDifferent, itemDescription);
		
		int beforeUpdate=instance.getQuantity();
		instance.updateQuantity(itemToRegisterDifferent);
		int expResult=beforeUpdate+addedQuantity;
		int result=instance.getQuantity();
		assertEquals(expResult,result,"Wrong quantity");
	}
	
	@Test
	void testUpdateQuantityZero() {
		int addedQuantity=0;
		itemRetrievalDifferent=new ItemRetrievalDTO(45, addedQuantity);
		itemToRegisterDifferent=new ItemRegistrationDTO(itemRetrievalDifferent, itemDescription);
		
		int beforeUpdate=instance.getQuantity();
		instance.updateQuantity(itemToRegisterDifferent);
		int expResult=beforeUpdate+addedQuantity;
		int result=instance.getQuantity();
		assertEquals(expResult,result,"Wrong quantity");
	}

}

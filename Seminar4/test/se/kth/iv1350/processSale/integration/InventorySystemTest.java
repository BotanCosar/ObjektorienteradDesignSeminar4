package se.kth.iv1350.processSale.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

class InventorySystemTest {
	
	private ItemRetrievalDTO itemRetrieval;
	private InventorySystem instance;
	
	@BeforeEach
	void setUp() throws Exception {
		instance=new InventorySystem();
	}

	@AfterEach
	void tearDown() throws Exception {
		instance=null;
	}



	@Test
	void testGetItem() {
		int itemIdentifier=245;
		String name="Mellanmjölk";
		double vatRate=6;
		double price=15;
		itemRetrieval=new ItemRetrievalDTO(itemIdentifier, 10);
		
		try {
			ItemDescriptionDTO result=instance.getItem(itemRetrieval);
			assertEquals(itemIdentifier, result.getId(),"Wrong id");
			assertEquals(name, result.getName(),"Wrong name");
			assertEquals(vatRate, result.getVatRate(),"Wrong vatRate");
			assertEquals(price, result.getPrice(),"Wrong price");
		}
		catch(InvalidIdentifierException e) {
			fail("Could not retrieve valid item");
		}
	}
	
	@Test
	void testGetItemInvalid() {
		int itemIdentifier=777;
		itemRetrieval=new ItemRetrievalDTO(itemIdentifier, 10);
		
		try {
			instance.getItem(itemRetrieval);
			fail("Successfully retrieved invalid item");
		}
		catch(InvalidIdentifierException e) {
			assertTrue(e.getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testGetItemNegative() {
		int itemIdentifier=-777;
		itemRetrieval=new ItemRetrievalDTO(itemIdentifier, 10);
		
		try {
			instance.getItem(itemRetrieval);
			fail("Successfully retrieved invalid item");
		}
		catch(InvalidIdentifierException e) {
			assertTrue(e.getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testGetItemZero() {
		int itemIdentifier=0;
		itemRetrieval=new ItemRetrievalDTO(itemIdentifier, 10);
		
		try {
			instance.getItem(itemRetrieval);
			fail("Successfully retrieved invalid item");
		}
		catch(InvalidIdentifierException e) {
			assertTrue(e.getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testGetItemIntegerMax() {
		int itemIdentifier=Integer.MAX_VALUE;
		itemRetrieval=new ItemRetrievalDTO(itemIdentifier, 10);
		
		try {
			instance.getItem(itemRetrieval);
			fail("Successfully retrieved invalid item");
		}
		catch(InvalidIdentifierException e) {
			assertTrue(e.getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testGetItemIntegerMin() {
		int itemIdentifier=Integer.MIN_VALUE;
		itemRetrieval=new ItemRetrievalDTO(itemIdentifier, 10);
		
		try {
			instance.getItem(itemRetrieval);
			fail("Successfully retrieved invalid item");
		}
		catch(InvalidIdentifierException e) {
			assertTrue(e.getMessage().contains(
					"Item with identifier "+itemRetrieval.getItemIdentifier()+" could not be found."),
					"Wrong exception message.");
		}
	}
	
	@Test
	void testGetItemDatabaseFailure() {
		int itemIdentifier=666;
		itemRetrieval=new ItemRetrievalDTO(itemIdentifier, 10);
		
		try {
			instance.getItem(itemRetrieval);
			fail("Successfully retrieved item without database connection");
		}
		catch(DBConnectionFailureException e) {
			assertTrue(e.getMessage().contains(
					"Failed to connect to database"),
					"Wrong exception message.");
		}
		catch(InvalidIdentifierException e) {
			fail("Reached incorrect exception");
		}
	}

}

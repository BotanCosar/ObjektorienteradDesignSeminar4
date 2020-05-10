package se.kth.iv1350.processSale.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;
import se.kth.iv1350.processSale.model.dto.ReceiptDTO;
import se.kth.iv1350.processSale.model.dto.StoreDTO;

class PaymentTest {
	
	private ItemRetrievalDTO itemRetrieval;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;
	private Payment instance;
	private Sale sale;

	@BeforeEach
	void setUp() throws Exception {
		itemRetrieval=new ItemRetrievalDTO(45, 5);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		sale=new Sale();
	}

	@AfterEach
	void tearDown() throws Exception {
		itemRetrieval=null;
		itemDescription=null;
		itemToRegister=null;
		sale=null;
	}

	@Test
	void testCreateReceipt() {
		double amountPaid=500;
		instance=new Payment(amountPaid);
		
		LocalDate date=sale.getDate();
		LocalTime time=sale.getTime();
		
		sale.addItem(itemToRegister);
		
		itemRetrieval=new ItemRetrievalDTO(7, 4);
		itemDescription=new ItemDescriptionDTO(6, "spaghetti", 342, 30);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		
		sale.addItem(itemToRegister);
		List<Item> items=sale.getGoods().getItems();
		double totalPrice=sale.getRunningTotal();
		double change=instance.calculateChange(sale);
		StoreDTO store=new StoreDTO();
		String storeName="Hemköp";
		String storeAddress="Kistavägen 23";
		
		String expResult="---Receipt---\n\nSale date: "+date
				+"\nSale time: "+time
				+"\n\nStore name: "+storeName
				+"\nStore address: "+storeAddress
				+"\n\nItems: "
				+"\nName: "+items.get(0).getItemDescription().getName()
				+", Quantity: "+items.get(0).getQuantity()
				+", Price: "+items.get(0).getItemDescription().getPrice()
				+", Total this item: "+items.get(0).getItemDescription().getPrice()*items.get(0).getQuantity()
				+", VAT rate: "+items.get(0).getItemDescription().getVatRate()
				+"\nName: "+items.get(1).getItemDescription().getName()
				+", Quantity: "+items.get(1).getQuantity()
				+", Price: "+items.get(1).getItemDescription().getPrice()
				+", Total this item: "+items.get(1).getItemDescription().getPrice()*items.get(1).getQuantity()
				+", VAT rate: "+items.get(1).getItemDescription().getVatRate()
				+"\n\nTotal price: "+String.format("%.2f", totalPrice)
				+"\nAmount paid: "+String.format("%.2f", amountPaid)
				+"\nChange: "+String.format("%.2f", change);
		ReceiptDTO receipt=instance.createReceipt(store);
		String result=receipt.createReceiptString();
		assertTrue(result.contains(expResult),"Wrong printout");
		assertTrue(result.contains(Integer.toString(date.getYear())),"Wrong sale year");
		assertTrue(result.contains(Integer.toString(date.getMonthValue())),"Wrong sale month");
		assertTrue(result.contains(Integer.toString(date.getDayOfMonth())),"Wrong sale day");
		assertTrue(result.contains(Integer.toString(time.getHour())),"Wrong sale hour");
		assertTrue(result.contains(Integer.toString(time.getMinute())),"Wrong sale minute");
	}

	@Test
	void testCalculateChangeNoItemsAdded() {
		double amountPaid=500;
		instance=new Payment(amountPaid);
		double expResult=amountPaid;
		double result=instance.calculateChange(sale);
		assertEquals(expResult,result,"Wrong change");
	}
	
	@Test
	void testCalculateChangeOneItemAdded() {
		sale.addItem(itemToRegister);
		
		double amountPaid=500;
		instance=new Payment(amountPaid);
		
		double actualVatRate=(itemDescription.getVatRate()/100)+1;
		double price=itemDescription.getPrice();
		int quantity=itemRetrieval.getQuantity();
		double finalCost=price*quantity*actualVatRate;
		
		double expResult=amountPaid-finalCost;
		double result=instance.calculateChange(sale);
		assertEquals(expResult,result,"Wrong change");
	}
	
	@Test
	void testCalculateChangeMultipleItemsAdded() {
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		
		double amountPaid=500;
		instance=new Payment(amountPaid);
		
		double actualVatRate=(itemDescription.getVatRate()/100)+1;
		double price=itemDescription.getPrice();
		int quantity=itemRetrieval.getQuantity();
		double finalCost=price*quantity*actualVatRate*3;
		
		double expResult=amountPaid-finalCost;
		double result=instance.calculateChange(sale);
		assertEquals(expResult,result,"Wrong change");
	}

}

package se.kth.iv1350.processSale.model.dto;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.Item;
import se.kth.iv1350.processSale.model.Payment;
import se.kth.iv1350.processSale.model.Sale;

class ReceiptDTOTest {
	
	private StoreDTO store;
	private Sale sale;
	private ItemRetrievalDTO itemRetrieval;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;
	private double amountPaid;
	private Payment payment;
	private ReceiptDTO instance;
	
	@BeforeEach
	void setUp() throws Exception {
		store=new StoreDTO();
		sale=new Sale();
		itemRetrieval=new ItemRetrievalDTO(45, 5);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		amountPaid=500;
		payment=new Payment(amountPaid);
	}

	@AfterEach
	void tearDown() throws Exception {
		amountPaid=0;
		store=null;
		payment=null;
		instance=null;
		itemRetrieval=null;
		itemDescription=null;
		itemToRegister=null;
		sale=null;
	}

	@Test
	void testCreateReceiptString() {	
		LocalDate date=sale.getDate();
		LocalTime time=sale.getTime();
		
		sale.addItem(itemToRegister);
		
		itemRetrieval=new ItemRetrievalDTO(7, 4);
		itemDescription=new ItemDescriptionDTO(6, "spaghetti", 342, 30);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		
		sale.addItem(itemToRegister);
		List<Item> items=sale.getGoods().getItems();
		double totalPrice=sale.getRunningTotal();
		double change=sale.pay(payment);

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
		
		instance=new ReceiptDTO(store, payment);
		String result=instance.createReceiptString();
		assertTrue(result.contains(expResult),"Wrong printout");
		assertTrue(result.contains(Integer.toString(date.getYear())),"Wrong sale year");
		assertTrue(result.contains(Integer.toString(date.getMonthValue())),"Wrong sale month");
		assertTrue(result.contains(Integer.toString(date.getDayOfMonth())),"Wrong sale day");
		assertTrue(result.contains(Integer.toString(time.getHour())),"Wrong sale hour");
		assertTrue(result.contains(Integer.toString(time.getMinute())),"Wrong sale minute");
	}

}

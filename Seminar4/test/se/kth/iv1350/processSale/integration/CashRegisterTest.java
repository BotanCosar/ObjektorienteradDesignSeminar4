package se.kth.iv1350.processSale.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import se.kth.iv1350.processSale.model.Payment;
import se.kth.iv1350.processSale.model.Sale;
import se.kth.iv1350.processSale.model.dto.ItemDescriptionDTO;
import se.kth.iv1350.processSale.model.dto.ItemRegistrationDTO;
import se.kth.iv1350.processSale.model.dto.ItemRetrievalDTO;

class CashRegisterTest {
	
	private CashRegister instance;
	private Payment payment;
	private ItemRetrievalDTO itemRetrieval;
	private ItemDescriptionDTO itemDescription;
	private ItemRegistrationDTO itemToRegister;

	@BeforeEach
	void setUp() throws Exception {
		itemRetrieval=new ItemRetrievalDTO(45, 20);
		itemDescription=new ItemDescriptionDTO(25, "tomatsoppa", 999, 25);
		itemToRegister=new ItemRegistrationDTO(itemRetrieval, itemDescription);
		instance=new CashRegister();
	}

	@AfterEach
	void tearDown() throws Exception {
		itemRetrieval=null;
		itemDescription=null;
		itemToRegister=null;
		instance=null;
	}

	@Test
	void testAddPaymentNoItemNoPayment() {
		double amountPaid=0;
		payment=new Payment(amountPaid);
		instance.addPayment(payment);
		double result=instance.getBalance();
		assertEquals(amountPaid, result);
	}
	
	@Test
	void testAddPaymentNoItemPayment() {
		double amountPaid=100;
		payment=new Payment(amountPaid);
		instance.addPayment(payment);
		double result=instance.getBalance();
		assertEquals(amountPaid, result);
	}
	
	@Test
	void testAddPaymentOneItem() {
		double amountPaid=100;
		payment=new Payment(amountPaid);
		Sale sale=new Sale();
		sale.addItem(itemToRegister);
		double change=sale.pay(payment);
		double expResult=amountPaid-change;
		
		instance.addPayment(payment);
		double result=instance.getBalance();
		assertEquals(expResult, result);
	}
	
	@Test
	void testAddPaymentMultipleItems() {
		double amountPaid=500;
		payment=new Payment(amountPaid);
		Sale sale=new Sale();
		for(int i=0;i<3;i++) {
			sale.addItem(itemToRegister);
		}
		double change=sale.pay(payment);
		double expResult=amountPaid-change;
		
		instance.addPayment(payment);
		double result=instance.getBalance();
		assertEquals(expResult, result);
	}

}

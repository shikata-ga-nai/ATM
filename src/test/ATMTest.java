package myatm;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.InOrder;

public class ATMTest {
    
    public ATMTest() {
    }
       
    @Test
    public void testATM_money() throws Exception {
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        double expResult = moneyInATM;
        double result = atm.moneyInATM;
        assertEquals(expResult, result, 0.0);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testATM_check()throws Exception  {
        System.out.println("testATM_check");
        double moneyInATM = -100;
        ATM atm = new ATM(moneyInATM);
        Card card = null;
    }
    
    @Test
    public void testGetMoneyInATM ()throws Exception {
        System.out.println("testGetMoneyInATM");
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        double expResult = moneyInATM;
        double result = atm.getMoneyInATM();
        assertEquals(expResult, result, 0.0);
    }
    
    
    @Test (expected = NoCardInserted.class)
    public void testValidateCard_NullCard()throws Exception{
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        Card card = null;
        int pinCode = 1234;
        boolean result = atm.validateCard(card, pinCode);
        assertFalse(result);
    }
    
    @Test (expected = NoCardInserted.class)
    public void testValidateCard_blockedCard_validePin()throws Exception{
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(1234)).thenReturn(true);
        when(card.checkPin(anyInt())).thenReturn(false);
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        int pinCode = 1234;
        boolean result = atm.validateCard(card, pinCode);
        verify(card).checkPin(pinCode);
        verify(card).isBlocked();
        assertFalse(result);  
    }
    
    @Test (expected = NoCardInserted.class)
    public void testValidateCard_ValideCard_InvalidePin() throws Exception{
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        int pinCode = 1111;
        boolean result = atm.validateCard(card, pinCode);
        verify(card).checkPin(pinCode);
        verify(card).isBlocked();
        assertFalse(result);  
    }
    
    @Test 
    public void testValidateCard_valideCard_validePin() throws Exception {
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        int pinCode = 1234;
        boolean result = atm.validateCard(card, pinCode);
        verify(card).checkPin(pinCode);
        verify(card).isBlocked();
        assertTrue(result);  
    }
     
    @Test (expected = NoCardInserted.class)
    public void testCheckBalance_NoCard() throws Exception{
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        int pinCode = 1234;
        atm.checkBalance();
    }
    
    @Test (expected = NoCardInserted.class)
    public void testCheckBalance_blockedCard_validePin()throws Exception {
        Card card = mock(Card.class);
       // Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(1234)).thenReturn(true);
        when(card.checkPin(anyInt())).thenReturn(false);
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        int pinCode = 1234;
        atm.checkBalance();
    }
    
    @Test (expected = NoCardInserted.class)
    public void testCheckBalance_ValidCard_InvalidePin()throws Exception {
        double moneyInATM = 1000;
        double balance = 110;
        int pinCode = 0;
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        when(card.getAccount()).thenReturn(acc);
        when(acc.getBalance()).thenReturn(balance);
        ATM atm = new ATM(moneyInATM);
        atm.validateCard(card, pinCode);
        InOrder inOrd = inOrder(card, acc);
        inOrd.verify(card).checkPin(pinCode);
        inOrd.verify(card).isBlocked();
        inOrd.verify(acc).getBalance();
        atm.checkBalance();
    }
    
    @Test 
    public void testCheckBalance_ValidCard_ValidePin()throws Exception {
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        double balance = 110;
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        when(card.getAccount()).thenReturn(acc);
        when(acc.getBalance()).thenReturn(balance);
        double moneyInATM = 1000;
        ATM atm = new ATM(moneyInATM);
        int pinCode = 1234;
        atm.validateCard(card, pinCode);
        double result = atm.checkBalance();
        InOrder inOrd = inOrder(card, acc);
        inOrd.verify(card).isBlocked();
        inOrd.verify(card).checkPin(pinCode);
        inOrd.verify(card).getAccount();
        inOrd.verify(acc).getBalance();
        assertEquals(balance, result, 0.0);
    }
    
    @Test (expected = NoCardInserted.class)
    public void testGetCashe_NoCard_ValidePin()throws Exception {
        double moneyInATM = 1000;
        int pinCode = 1234;
        double amount = 90;
        ATM atm = new ATM(moneyInATM);
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        card = null;
        atm.getCash(amount);
        verify(card).isBlocked();
        verify(card).checkPin(pinCode);
    }
    
    @Test (expected = NoCardInserted.class)
    public void testGetCashe_BlockedCard_ValidePin() throws Exception {
        double moneyInATM = 1000;
        int pinCode = 1234;
        double amount = 90;
        ATM atm = new ATM(moneyInATM);
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(true);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        atm.validateCard(card, pinCode);
        atm.getCash(amount);
        verify(card).isBlocked();
        verify(card).checkPin(pinCode);
    }
    
    @Test (expected = NoCardInserted.class)
    public void testGetCashe_ValideCard_InvalidePin() throws Exception {
        double moneyInATM = 1000;
        int pinCode = 124;
        double amount = 90;
        ATM atm = new ATM(moneyInATM);
        Card card = mock(Card.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        atm.validateCard(card, pinCode);
        verify(card).isBlocked();
        verify(card).checkPin(pinCode);
        atm.getCash(amount);
        
    }
    
    @Test (expected = NotEnoughMoneyInATM.class)
    public void testGetCashe_ValidCard_ValidePin_InvalideMoneyInATM() throws Exception {
        double moneyInATM = 10;
        int pinCode = 1234;
        double balance = 110;
        double amount = 90;
        ATM atm = new ATM(moneyInATM);
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        when(card.getAccount()).thenReturn(acc);
        when(acc.getBalance()).thenReturn(balance);
        when(acc.withdrow(amount)).thenReturn(balance - amount);
        InOrder inOrd = inOrder(card, acc);
        atm.validateCard(card, pinCode);
        atm.checkBalance();
        atm.getCash(amount);
        inOrd.verify(card).isBlocked();
        inOrd.verify(card).checkPin(pinCode);
        inOrd.verify(card).getAccount();
        inOrd.verify(acc, atLeastOnce()).getBalance();
        inOrd.verify(acc).withdrow(amount);
    }
    
    @Test (expected = NotEnoughMoneyInAccount.class)
    public void testGetCashe_ValidCard_ValidePin_InvalideMoneyInAccount()throws Exception {
        double moneyInATM = 1000;
        int pinCode = 1234;
        double balance = 30;
        double amount = 90;
        ATM atm = new ATM(moneyInATM);
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        when(card.getAccount()).thenReturn(acc);
        when(acc.getBalance()).thenReturn(balance);
        when(acc.withdrow(amount)).thenReturn(balance - amount);
        atm.validateCard(card, pinCode);
        atm.checkBalance();
        atm.getCash(amount);
        InOrder inOrd = inOrder(card, acc);
        inOrd.verify(card).isBlocked();
        inOrd.verify(card).checkPin(pinCode);
        inOrd.verify(card).getAccount();
        inOrd.verify(acc, atLeastOnce()).getBalance();
        inOrd.verify(acc).withdrow(amount);
    }
    
    @Test 
    public void testGetCashe_ValidCard_ValidePin_ValideMoneyInATM_valideMoneyInAccount() throws Exception{
        double moneyInATM = 1000;
        int pinCode = 1234;
        double balance = 110;
        double amount = 90;
        ATM atm = new ATM(moneyInATM);
        Card card = mock(Card.class);
        Account acc = mock(Account.class);
        when(card.isBlocked()).thenReturn(false);
        when(card.checkPin(anyInt())).thenReturn(false);
        when(card.checkPin(1234)).thenReturn(true);
        when(card.getAccount()).thenReturn(acc);
        when(acc.getBalance()).thenReturn(balance);
        when(acc.withdrow(amount)).thenReturn(balance - amount);
        atm.validateCard(card, pinCode);
        atm.checkBalance();
        double expResult = balance - amount;
        double result = atm.getCash(amount);
        InOrder inOrd = inOrder(card, acc);
        inOrd.verify(card).isBlocked();
        inOrd.verify(card).checkPin(pinCode);
        inOrd.verify(card).getAccount();
        inOrd.verify(acc, atLeastOnce()).getBalance();
        inOrd.verify(acc).withdrow(amount);
        assertEquals(expResult, result, 0.0);
        assertEquals(moneyInATM - amount, atm.getMoneyInATM(), 0.0);
    }
    
}

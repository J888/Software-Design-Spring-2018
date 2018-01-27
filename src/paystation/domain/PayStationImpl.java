package paystation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 *
 * Responsibilities:
 *
 * 1) Accept payment; 
 * 2) Calculate parking time based on payment; 
 * 3) Know earning, parking time bought; 
 * 4) Issue receipts; 
 * 5) Handle buy and cancel events.
 *
 * This source code is from the book "Flexible, Reliable Software: Using
 * Patterns and Agile Development" published 2010 by CRC Press. Author: Henrik B
 * Christensen Computer Science Department Aarhus University
 *
 * This source code is provided WITHOUT ANY WARRANTY either expressed or
 * implied. You may study, use, modify, and distribute it for non-commercial
 * purposes. For any commercial use, see http://www.baerbak.com/
 */
public class PayStationImpl implements PayStation {
    
    private int insertedSoFar;
    
    private int timeBought;
    
    Map<Integer, Integer> putInByCustomer = new HashMap<>();
    Map<Integer, Integer> machineTotal = new HashMap<>();
    

    //constructor
    public PayStationImpl(){
        
        machineTotal.put(5, 0);
        machineTotal.put(10, 0);
        machineTotal.put(25, 0);
        
        putInByCustomer.put(5, 0);
        putInByCustomer.put(10, 0);
        putInByCustomer.put(25, 0);
        
    }

    @Override
    public void addPayment(int coinValue)
            throws IllegalCoinException {
        
        switch (coinValue) {
            case 5: break;
            case 10: break;
            case 25: break;
            default:
                throw new IllegalCoinException("Invalid coin: " + coinValue);
        }
        
        machineTotal.put(coinValue, machineTotal.get(coinValue)+1);
        putInByCustomer.put(coinValue, putInByCustomer.get(coinValue)+1);
        
        insertedSoFar += coinValue;
        
        timeBought = insertedSoFar / 5 * 2;
    }

    @Override
    public int readDisplay() {
        return timeBought;
    }

    @Override
    public Receipt buy() {
        Receipt r = new ReceiptImpl(timeBought);
        reset();
        return r;
    }

    
    @Override
    public Map<Integer, Integer> cancel() {
        Map<Integer, Integer> giveBackToCustomer = new HashMap<>();
       
        giveBackToCustomer = putInByCustomer;
        
        reset();
        
        return giveBackToCustomer;
    }
    
    private void reset() {
        timeBought = insertedSoFar = 0;
        putInByCustomer.put(5, 0);
        putInByCustomer.put(10, 0);
        putInByCustomer.put(25, 0);
    
    }
    
    //check how much money has already been collected
    @Override
    public int empty() {
           
        int amountBeingEmptied = machineTotal.get(5) + machineTotal.get(10) + machineTotal.get(25);
        
        //clear the map
        machineTotal.put(5, 0);
        machineTotal.put(10, 0);
        machineTotal.put(25, 0);
        
        
        return amountBeingEmptied;
    }
    
    @Override
    public int getMachineTotal() {
        return machineTotal.get(5) + machineTotal.get(10) + machineTotal.get(25);
    }
    
    @Override
    public Map<Integer, Integer> getPutInByCustomerMap(){
        return putInByCustomer;
    }
    
    
}

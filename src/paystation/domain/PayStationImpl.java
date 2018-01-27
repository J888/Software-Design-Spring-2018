package paystation.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the pay station.
 *

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
        reset(false);
        return r;
    }

    
    @Override
    public Map<Integer, Integer> cancel() {
        Map<Integer, Integer> giveBackToCustomer = new HashMap<>();
       
        giveBackToCustomer = putInByCustomer;
        
        reset(true);
        
        return giveBackToCustomer;
    }
    
    
    //happens after the customer cancels their transaction
    // or buys
    private void reset(boolean cancelled) {
        
        timeBought = insertedSoFar = 0;
        
        if(cancelled){
            //take away from the machine total
            machineTotal.put(5, machineTotal.get(5) - putInByCustomer.get(5));
            machineTotal.put(10, machineTotal.get(10) - putInByCustomer.get(10));
            machineTotal.put(25, machineTotal.get(25) - putInByCustomer.get(25));
        }
        
        //reset how much the customer has put in
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

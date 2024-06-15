package org.research.kadda.labinventory;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.research.kadda.labinventory.SynthesisService;
import org.research.kadda.labinventory.data.SynthesisLibraryOrderDto;

/**
 * Author: Kadda
 */
public class SynthesisServiceTest {
    
    @Test
    public void testGetAllSynthesisOrders() {
    	List<SynthesisLibraryOrderDto> orders = SynthesisService.getAllSynthesisOrders();
        Assert.assertNotNull(orders);
        Assert.assertTrue(!orders.isEmpty());
    }
    
    @Test
    public void testGetSynthesisOrderById() {
        int orderId = 1;
        SynthesisLibraryOrderDto order = SynthesisService.getSynthesisOrderById(orderId);
        Assert.assertNotNull(order);
        Assert.assertTrue(order.getId() == orderId);
    }
    
    @Test
    public void testAddSynthesisOrder() {
    	SynthesisLibraryOrderDto order = new SynthesisLibraryOrderDto();
    	order.setTitle("MyTitle-zZZzz");
    	order.setLink("http://www.ares.com");
    	order.setCreationTime(new Date());
    	boolean saved = SynthesisService.addSynthesisOrder(order);
    	Assert.assertTrue(saved);
    }
    
    @Test
    public void testDeleteSynthesisOrder() {
    	List<SynthesisLibraryOrderDto> orders = SynthesisService.getAllSynthesisOrders();
    	SynthesisLibraryOrderDto last = orders.get(orders.size() - 1);
    	int orderId = last.getId();
        SynthesisLibraryOrderDto order = SynthesisService.getSynthesisOrderById(orderId);
        boolean removed = SynthesisService.deleteSynthesisOrder(String.valueOf(order.getId()));
        Assert.assertTrue(removed);
    }
    
}

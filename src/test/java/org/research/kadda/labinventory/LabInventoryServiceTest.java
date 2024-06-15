package org.research.kadda.labinventory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.research.kadda.labinventory.LabInventoryService;
import org.research.kadda.labinventory.data.InstrumentDto;
import org.research.kadda.labinventory.data.ReservationDto;
import org.research.kadda.labinventory.data.ReservationUsageDto;

/**
 * Author: Kadda
 */

public class LabInventoryServiceTest {
    @Test
    public void testGetAllInstruments() {
        System.out.println("End of testGetAllInstruments");
    }

    @Test
    public void testGetInstrument() {
        InstrumentDto instrumentDto = LabInventoryService.getInstrumentById(21);
        Assert.assertNotNull(instrumentDto);
    }

    @Test
    public void testGetGroupNames() {
        List<String> groupNames = LabInventoryService.getGroupNames();
        Assert.assertTrue(groupNames.size() > 0);

        System.out.println(groupNames);
    }

    @Test
    public void testGetInstrumentsByGroupId() {
        int gid = 1;

        List<java.lang.Integer> ids = LabInventoryService.getInstrumentIdsByGroupId(gid);
        Assert.assertNotNull(ids);
        Assert.assertTrue(ids.size() > 0);
    }

	@Test
	@SuppressWarnings("resource")
    public void testAddInstrument() {
        File csvFile = new File(this.getClass().getResource("/instruments.csv").getFile());
        Assert.assertTrue(csvFile.exists());
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(csvFile));
            String line;
            boolean header = true;
            while ( (line = fileReader.readLine()) != null) {
                if (line.startsWith("#") || header) {
                    header = false;
                    continue;
                }

                InstrumentDto instrumentDto = new InstrumentDto();
                String info[] = line.split(",");
                Assert.assertTrue(info.length > 4);
                instrumentDto.setName(info[0] + " (" + info[4] + " / " + info[5] + ")");
                instrumentDto.setStatus("Working");
                instrumentDto.setLocation(info[1]);
                instrumentDto.setGroupname(info[2]);
                instrumentDto.setUsername(info[3]);
                instrumentDto.setSelectOverlap(1);

                Assert.assertTrue(LabInventoryService.addInstrument(instrumentDto)>0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllReservations() {
        List<ReservationDto> reservations = LabInventoryService.getAllReservations();

        Assert.assertNotNull(reservations);
    }

    @Test
    public void testGetReservationsByInstrId() {
        int instrId = 940;

        List<ReservationDto> reservations = LabInventoryService.getReservationsByInstrId(instrId);

        Assert.assertNotNull(reservations);
    }

    @Test
    public void testgetReservationsByInstrIdsAndDateRange() {
        // create list of instruments ids
        List<Integer> instrIds = new ArrayList<>();
        instrIds.add(1); instrIds.add(3); instrIds.add(47); instrIds.add(340); instrIds.add(482); instrIds.add(510);

        /*DateTimeFormatter dateTimeFormatter = DateTimeFormatter.RFC_1123_DATE_TIME;
        //Timestamp.valueOf(LocalDateTime.parse(startDateStr, dateTimeFormatter))*/
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth() + 2, 23, 59);

        List<ReservationDto> reservations = LabInventoryService.getReservationsByInstrIdsAndDateRange(instrIds, null, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate), false);
        Assert.assertTrue(reservations.size() > 0);
    }

    @Test
    public void testGetResoptionIdsByInstrumentId() {
        String instrId = "381";

        List<Integer> resoptionIds = LabInventoryService.getResoptionIdsByInstrumentId(instrId);
        Assert.assertTrue(resoptionIds.size() > 0);
    }
    
    
    @Test
    public void testAddReservationUsage() {
    	ReservationUsageDto reservationusage = new ReservationUsageDto();
    	int reservationId = 992;
    	reservationusage.setReservationId(reservationId);
    	reservationusage.setCompound("ACT-000111");
    	reservationusage.setProject("ProjectXXX");
    	reservationusage.setBatch("ELN-018544421");
    	reservationusage.setSample("EXT-018544421");
    	
    	LabInventoryService.addReservationUsage(reservationusage);
    	List<ReservationUsageDto> savedReservationusages = LabInventoryService.getReservationUsageByReservationId(reservationId);
    	Assert.assertNotNull(savedReservationusages);
    	Assert.assertTrue(!savedReservationusages.isEmpty());
    	ReservationUsageDto savedReservationusage = savedReservationusages.get(0);
    	Assert.assertTrue(savedReservationusage.getCompound().equals("ACT-000111"));
    }
    
     @Test
     public void testUpdateReservationUsage() {
    	 int reservationId = 992;
    	 List<ReservationUsageDto> savedReservationusages = LabInventoryService.getReservationUsageByReservationId(reservationId);
    	 ReservationUsageDto reservationUsageToUpdate = savedReservationusages.get(0);
    	 reservationUsageToUpdate.setCompound("ACT-000222");
    	 LabInventoryService.updateReservationUsage(reservationUsageToUpdate);
    	 ReservationUsageDto ru = LabInventoryService.getReservationUsageByReservationId(reservationId).get(0);
    	 Assert.assertTrue(ru.getCompound().equals("ACT-000222"));
     }
    
     @Test
     public void testDeleteReservationUsage() {
    	 int reservationId = 992;
    	 ReservationUsageDto reservationUsageToDelete = LabInventoryService.getReservationUsageByReservationId(reservationId).get(0);
    	 LabInventoryService.deleteReservationUsage(String.valueOf(reservationUsageToDelete.getId()));
    	 List<ReservationUsageDto> ruList = LabInventoryService.getReservationUsageByReservationId(reservationId);
    	 Assert.assertTrue(ruList.isEmpty());
     }
     
    @Test
    public void testGetAllReservationUsages() {
        List<ReservationUsageDto> reservationUsages = LabInventoryService.getAllReservationUsages();
        Assert.assertNotNull(reservationUsages);
    }

    @Test
    public void testGetReservationsByReservationId() {
        int reservationUsageId = 11;
        List<ReservationUsageDto> reservationUsages = LabInventoryService.getReservationUsageByReservationId(reservationUsageId);
        Assert.assertNotNull(reservationUsages);
    }
    
    
    @Test
    public void testDeleteInstrumentDeputies() {
   	 int instrumentId = 1225;
   	 LabInventoryService.deleteInstrumentDeputies(String.valueOf(instrumentId));
   	 List<String> deputies = LabInventoryService.getDeputiesByInstrumentId(instrumentId);
   	 Assert.assertTrue(deputies.isEmpty());
    }
    
    @Test
    public void testGetEmployeeGroupsByInstrumentId() {
        String instId = "1201";
        List<String> employeeGroups = LabInventoryService.getEmployeeGroupsByInstrumentId(instId);
        Assert.assertNotNull(employeeGroups);
        Assert.assertTrue(!employeeGroups.isEmpty());
    }
    
    @Test
    public void testGetRestrictedInstrumentId() {
        int instId = 1321;
        List<Integer> restrictedInstrumentIds = LabInventoryService.getRestrictedInstrumentIdsByInstId(instId);
        Assert.assertNotNull(restrictedInstrumentIds);
        Assert.assertTrue(!restrictedInstrumentIds.isEmpty() && restrictedInstrumentIds.get(0) == 1322);
    }
    
}

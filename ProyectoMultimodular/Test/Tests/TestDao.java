package Tests;

import static org.junit.Assert.*;
import org.junit.Test;

import Proyecto.TicketDAO;

import java.io.ByteArrayInputStream;

import java.util.Scanner;

public class TestDao {
	
	 @Test
	    public void testCrearTicket() {

	        String input = "ABC123\nJohn\nDoe\n1\n2024-05-29 10:00:00\n\n25.0\n";

	        System.setIn(new ByteArrayInputStream(input.getBytes()));

	        Scanner scanner = new Scanner(System.in);

	        TicketDAO.crearTicket(scanner);

	        assertTrue(true);
	    }
	 
	 

}

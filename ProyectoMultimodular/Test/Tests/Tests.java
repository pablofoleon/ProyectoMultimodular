package Tests;




import java.sql.Connection;
import java.util.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import Proyecto.App;
public class Tests {

	private static Connection conexion;

    @BeforeAll
    public static void setUp() throws Exception {

        conexion = App.obtenerConexion();
    }

    @AfterAll
    public static void tearDown() throws Exception {

        if (conexion != null) {
            conexion.close();
        }
    }
	@Test
    public void testRegistrarCliente() {

        Scanner scanner = new Scanner("John\nDoe\n123456\njohn@example.com\n");
        App.registrarCliente(conexion, scanner);

    }

    @Test
    public void testBorrarCliente() {

        Scanner scanner = new Scanner("John\nDoe\n");
        App.borrarCliente(conexion, scanner);

    }

    @Test
    public void testRegistrarVehiculo() {

        Scanner scanner = new Scanner("coche\nABC123\nToyota\nCorolla\nRojo\nSedán\n");
        App.registrarVehiculo(conexion, scanner);

    }

    @Test
    public void testDarBajaVehiculo() {

        Scanner scanner = new Scanner("coche\nABC123\n");
        App.darBajaVehiculo(conexion, scanner);

    }


}

package Tests;





import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import Proyecto.App;
public class TestConexion {
	

	@Test
    public void testObtenerConexion() {
        Connection conexion = App.obtenerConexion();
        assertNotNull(conexion, "La conexión no debería ser nula");
    }


	
	@Test
    public void testCerrarConexion() {
        Connection conexion = App.obtenerConexion();
        assertNotNull(conexion, "La conexión no debería ser nula antes de cerrarla");

        App.cerrarConexion(conexion);
        try {
            assertTrue(conexion.isClosed(), "La conexión debería estar cerrada");
        } catch (SQLException e) {
            fail("No se pudo verificar si la conexión está cerrada");
        }
    }
	
}

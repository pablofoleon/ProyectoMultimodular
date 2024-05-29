package Proyecto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ejemplo {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/empresa","root","root");
							System.out.println("Conexión ok");
							// Crea una declaracion
							Statement statement = connection.createStatement();
							// Ejecutar una declaracion
							ResultSet resultSet = statement.executeQuery("SELECT * FROM clientes");
							// Repetir el resultado e imprimir los nombres y email de los empleados.
							while (resultSet.next())
								System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2));
							// Cerrar la conexion
							connection.close();
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
					e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error en la conexion");
		}
	}
}
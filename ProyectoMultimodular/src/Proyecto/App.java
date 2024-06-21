package Proyecto;

import java.sql.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Clase principal que gestiona el sistema de parking.
 */
public class App {
	
	public static void main(String[] args) {
		
		// Lista para almacenar los vehículos en el garaje
				List<Vehiculo> vehiculosGaraje = new ArrayList<>();
				// Lista para almacenar los clientes
				List<Cliente> clientes = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		boolean salir = false;
			Connection conexion = obtenerConexion();
							System.out.println("Conexión ok");
							while (!salir) {
				                System.out.println("********** Menú de Gestión de Parking **********");
				                System.out.println("*  0. Registrar Cliente                        *");
				                System.out.println("*  1. Dar de baja cliente                      *");
				                System.out.println("*  2. Registrar  Vehículo                      *");
				                System.out.println("*  3. Dar de baja Vehículo                     *");
				                System.out.println("*  4. Crear ticket                             *");
				                System.out.println("*  5. Borrar ticket                            *");
				                System.out.println("*  6. Modificar ticket                         *");
				                System.out.println("*  7. Encontrar vehiculo por matrícula         *");
				                System.out.println("*  8. Consultar plaza                          *");
				                System.out.println("*  9. Mostrar todos del garaje                 *");
				                System.out.println("*  10. Listar todos los clientes               *");
				    			System.out.println("*  11. Filtrar vehículos por marca             *");
				    			System.out.println("*  12. Salir                                   *");
				                System.out.println("************************************************");
				                System.out.print("Ingrese su opción: ");
				                
				                int opcion = scanner.nextInt();

				                switch (opcion) {
				                    case 0:
				                    	registrarCliente(conexion,scanner,clientes);
				                    	 
				                        break;
				                    case 1:
				                    	borrarCliente(conexion,scanner,clientes);
				                    	break;
				                    case 2:
				                    	registrarVehiculo(conexion,scanner);
				                        break;
				                    case 3:
				                    	darBajaVehiculo(conexion,scanner);
				                        break;
				                    case 4:
				                    	TicketDAO.crearTicket(scanner);
				                    	break;
				                    case 5:
				                    	TicketDAO.borrarTicket(conexion,scanner);
				                    	break;
				                    case 6:
				                    	TicketDAO.modificarTicket(conexion,scanner);
				                    	scanner.nextLine();
				                    	break;
				                    case 7:
				                    	buscarVehiculoMatricula(conexion,scanner);
				                    	break;
				                    case 8:
				                    	consultarPlaza(conexion,scanner);
				                    	break;
				                    case 9:
				                    	mostrarVehiculosGaraje(conexion,vehiculosGaraje);
				                    	break;
				                    case 10:
				    					listarTodosLosClientes(clientes);
				    					break;
				    				case 11:
				    					filtrarVehiculosPorMarca(vehiculosGaraje, scanner);
				    					break;
				    				case 12:
				    					salir = true;
				    					System.out.println("**************************************");
				    					System.out.println("*                                    *");
				    					System.out.println("*        PROGRAMA FINALIZADO         *");
				    					System.out.println("*                                    *");
				    					System.out.println("**************************************");
				    					break;
				    				default:
				    					System.out.println("Opción no válida.");
				    					break;
				                }
							}
			cerrarConexion(conexion);
			scanner.close();
		}
		
	
	
	
	/**
     * Establece una conexión a la base de datos.
     * @return La conexión establecida.
     */
	public static Connection obtenerConexion() {
		Connection conexion = null;
        try {
            
        	 conexion = DriverManager.getConnection("jdbc:mysql://localhost/parking","root","root");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }

        return conexion;
    }
	
	 /**
     * Cierra la conexión a la base de datos.
     * @param conexion La conexión a cerrar.
     */
	
	public static void  cerrarConexion(Connection conexion) {
		if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
	}
	
	/**
     * Registra un nuevo cliente en la base de datos.
     * @param conexion La conexión a la base de datos.
     * @param scanner El objeto Scanner para entrada de usuario.
     */
	public static void registrarCliente(Connection conexion,Scanner scanner,List<Cliente> clientes) {
		scanner.nextLine(); // Limpiar buffer
        System.out.print("Ingrese el nombre del cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el apellido del cliente: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese el teléfono del cliente: ");
        String telefono = scanner.nextLine();
        System.out.print("Ingrese el email del cliente: ");
        String email = scanner.nextLine();

        Cliente cliente = new Cliente(nombre, apellido, telefono, email);
        clientes.add(cliente);

        // Registrar en la base de datos
        try {
            String sql = "INSERT INTO Cliente (nombre, apellido, telefono, email) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getApellido());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getEmail());
            pstmt.executeUpdate();
            System.out.println("Cliente registrado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
	}
	
	
	 /**
     * Borra un cliente de la base de datos junto con sus tickets asociados.
     * @param conexion La conexión a la base de datos.
     */
	public static void borrarCliente(Connection conexion, Scanner scanner,List<Cliente> clientes) {
		 scanner.nextLine(); // Limpiar buffer
	        System.out.print("Ingrese el email del cliente a borrar: ");
	        String email = scanner.nextLine();

	        Cliente cliente = clientes.stream()
	                .filter(c -> c.getEmail().equals(email))
	                .findFirst()
	                .orElse(null);

	        if (cliente != null) {
	            clientes.remove(cliente);

	            // Borrar de la base de datos
	            try {
	                String sql = "DELETE FROM clientes WHERE email = ?";
	                PreparedStatement pstmt = conexion.prepareStatement(sql);
	                pstmt.setString(1, email);
	                pstmt.executeUpdate();
	                System.out.println("Cliente borrado exitosamente.");
	            } catch (SQLException e) {
	                System.out.println("Error al borrar cliente: " + e.getMessage());
	            }
	        } else {
	            System.out.println("Cliente no encontrado.");
	        }
	    }

	

	
	 /**
     * Registra un nuevo vehículo en la base de datos.
     * @param conexion La conexión a la base de datos.
     * @param scanner El objeto Scanner para entrada de usuario.
     */
	
	public static void registrarVehiculo(Connection conexion, Scanner scanner) {
	    scanner.nextLine(); // Limpiar el buffer del scanner

	    System.out.println("¿Es un coche o una moto? (coche/moto): ");
	    String tipoVehiculo = scanner.nextLine().toLowerCase();

	    System.out.println("Introduce la matrícula del vehículo: ");
	    String matricula = scanner.nextLine();
	    System.out.println("Introduce la marca del vehículo: ");
	    String marca = scanner.nextLine();
	    System.out.println("Introduce el modelo del vehículo: ");
	    String modelo = scanner.nextLine();
	    System.out.println("Introduce el color del vehículo: ");
	    String color = scanner.nextLine();

	    String tipoEspecifico = "";
	    if (tipoVehiculo.equals("coche")) {
	        System.out.println("Introduce el tipo de coche (ej. Sedán, SUV, etc.): ");
	        tipoEspecifico = scanner.nextLine();
	    } else if (tipoVehiculo.equals("moto")) {
	        System.out.println("Introduce el tipo de moto (ej. Deportiva, Scooter, etc.): ");
	        tipoEspecifico = scanner.nextLine();
	    } else {
	        System.out.println("Tipo de vehículo no válido. Debe ser 'coche' o 'moto'.");
	        return;
	    }

	    String sqlVehiculo = "INSERT INTO Vehiculo (Matricula, Marca, Modelo, Color) VALUES (?, ?, ?, ?)";
	    try {
	        // Insertar en la tabla Vehiculo
	        PreparedStatement pstmtVehiculo = conexion.prepareStatement(sqlVehiculo);
	        pstmtVehiculo.setString(1, matricula);
	        pstmtVehiculo.setString(2, marca);
	        pstmtVehiculo.setString(3, modelo);
	        pstmtVehiculo.setString(4, color);
	        
	        int filasAfectadasVehiculo = pstmtVehiculo.executeUpdate();
	        if (filasAfectadasVehiculo > 0) {
	            System.out.println("Vehículo registrado correctamente en la tabla Vehiculo.");

	            // Insertar en la tabla Coche o Moto según el tipo de vehículo
	            String sqlTipoEspecifico = "";
	            if (tipoVehiculo.equals("coche")) {
	                sqlTipoEspecifico = "INSERT INTO Coche (Matricula, TipoCoche) VALUES (?, ?)";
	            } else if (tipoVehiculo.equals("moto")) {
	                sqlTipoEspecifico = "INSERT INTO Moto (Matricula, TipoMoto) VALUES (?, ?)";
	            }

	            PreparedStatement pstmtTipoEspecifico = conexion.prepareStatement(sqlTipoEspecifico);
	            pstmtTipoEspecifico.setString(1, matricula);
	            pstmtTipoEspecifico.setString(2, tipoEspecifico);

	            int filasAfectadasTipoEspecifico = pstmtTipoEspecifico.executeUpdate();
	            if (filasAfectadasTipoEspecifico > 0) {
	                System.out.println("Vehículo registrado correctamente en la tabla " + (tipoVehiculo.equals("coche") ? "Coche" : "Moto") + ".");
	            } else {
	                System.out.println("No se pudo registrar el tipo específico del vehículo en la base de datos.");
	            }
	        } else {
	            System.out.println("No se pudo registrar el vehículo en la tabla Vehiculo.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
	    }
	}


	
	
	 /**
     * Da de baja un vehículo de la base de datos.
     * @param conexion La conexión a la base de datos.
     * @param scanner El objeto Scanner para entrada de usuario.
     */
	public static void darBajaVehiculo(Connection conexion, Scanner scanner) {
	    scanner.nextLine(); // Limpiar el buffer del scanner

	    System.out.println("¿Es un coche o una moto? (coche/moto): ");
	    String tipoVehiculo = scanner.nextLine().toLowerCase();

	    System.out.println("Introduce la matrícula del vehículo: ");
	    String matricula = scanner.nextLine();

	    String sqlEliminarVehiculo = "DELETE FROM Vehiculo WHERE Matricula = ?";
	    String sqlEliminarEspecifico = "";

	    if (tipoVehiculo.equals("coche")) {
	        sqlEliminarEspecifico = "DELETE FROM Coche WHERE Matricula = ?";
	    } else if (tipoVehiculo.equals("moto")) {
	        sqlEliminarEspecifico = "DELETE FROM Moto WHERE Matricula = ?";
	    } else {
	        System.out.println("Tipo de vehículo no válido. Debe ser 'coche' o 'moto'.");
	        return;
	    }

	    try {
	        // Eliminar de la tabla específica (Coche o Moto) primero para mantener la integridad referencial
	        PreparedStatement pstmtEspecifico = conexion.prepareStatement(sqlEliminarEspecifico);
	        pstmtEspecifico.setString(1, matricula);
	        int filasAfectadasEspecifico = pstmtEspecifico.executeUpdate();

	        if (filasAfectadasEspecifico > 0) {
	            System.out.println((tipoVehiculo.equals("coche") ? "Coche" : "Moto") + " eliminado correctamente de la tabla " + (tipoVehiculo.equals("coche") ? "Coche" : "Moto") + ".");

	            // Eliminar de la tabla Vehiculo
	            PreparedStatement pstmtVehiculo = conexion.prepareStatement(sqlEliminarVehiculo);
	            pstmtVehiculo.setString(1, matricula);
	            int filasAfectadasVehiculo = pstmtVehiculo.executeUpdate();

	            if (filasAfectadasVehiculo > 0) {
	                System.out.println("Vehículo eliminado correctamente de la tabla Vehiculo.");
	            } else {
	                System.out.println("No se pudo eliminar el vehículo de la tabla Vehiculo.");
	            }
	        } else {
	            System.out.println("No se encontró el vehículo en la tabla " + (tipoVehiculo.equals("coche") ? "Coche" : "Moto") + " o ya ha sido eliminado.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
	    }
	}


	/**
     * Consulta información de una plaza en el garaje.
     * @param conexion La conexión a la base de datos.
     * @param scanner El objeto Scanner para entrada de usuario.
     */
	
	public static void consultarPlaza(Connection conexion,Scanner scanner) {
       
        System.out.print("Ingrese el ID de la plaza a consultar: ");
        int idPlaza = scanner.nextInt();
        
        String sql = "SELECT p.PlazaID, p.Ubicacion, p.Tipo, t.ClienteID, c.Nombre, c.Apellido " +
                     "FROM Plaza p " +
                     "LEFT JOIN Ticket t ON p.PlazaID = t.PlazaID " +
                     "LEFT JOIN Cliente c ON t.ClienteID = c.ClienteID " +
                     "WHERE p.PlazaID = ?";
        try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setInt(1, idPlaza);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int plazaID = rs.getInt("PlazaID");
                    String ubicacion = rs.getString("Ubicacion");
                    String tipo = rs.getString("Tipo");
                    int clienteID = rs.getInt("ClienteID");
                    String nombreCliente = rs.getString("Nombre");
                    String apellidoCliente = rs.getString("Apellido");

                    System.out.println("Información de la plaza:");
                    System.out.println("ID de la plaza: " + plazaID);
                    System.out.println("Ubicación: " + ubicacion);
                    System.out.println("Tipo: " + tipo);
                    System.out.println();

                    if (clienteID != 0) {
                        System.out.println("Información del cliente que hizo el ticket:");
                        System.out.println("ID del cliente: " + clienteID);
                        System.out.println("Nombre: " + nombreCliente + " " + apellidoCliente);
                    } else {
                        System.out.println("No hay información sobre el cliente que hizo el ticket.");
                    }
                } else {
                    System.out.println("No se encontró ninguna plaza con el ID proporcionado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar la plaza: " + e.getMessage());
        } 
    }
	
	
	
	
	/**
     * Busca un vehículo por matrícula en el garaje.
     * @param conexion La conexión a la base de datos.
     * @param scanner El objeto Scanner para entrada de usuario.
     */
	public static void buscarVehiculoMatricula(Connection conexion, Scanner scanner) {
	    scanner.nextLine();

	    System.out.println("Introduce la matrícula del vehículo: ");
	    String matricula = scanner.nextLine();

	    String sql = "SELECT p.Ubicacion, t.FechaSalida " +
	                 "FROM Plaza p " +
	                 "JOIN Ticket t ON p.PlazaID = t.PlazaID " +
	                 "WHERE t.Matricula = ?";

	    try (PreparedStatement pstmt = conexion.prepareStatement(sql)) {
	        pstmt.setString(1, matricula);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                Timestamp fechaSalidaTimestamp = rs.getTimestamp("FechaSalida");

	                if (fechaSalidaTimestamp == null) {
	                    String ubicacion = rs.getString("Ubicacion");
	                    System.out.println("El vehículo con matrícula " + matricula + " se encuentra en la plaza " + ubicacion);
	                } else {
	                    System.out.println("El vehículo con matrícula " + matricula + " no está en el garaje.");
	                }
	            } else {
	                System.out.println("No se encontró ningún vehículo con esa matrícula en el parking.");
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al buscar el vehículo: " + e.getMessage());
	    }
	}

	
	 /**
     * Muestra todos los vehículos presentes en el garaje.
     * @param conexion La conexión a la base de datos.
     */
	public static void mostrarVehiculosGaraje(Connection conexion, List<Vehiculo> vehiculosGaraje) {
		String sql = "SELECT * FROM Vehiculo";
	    
	    try {
	        Statement stmt = conexion.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        
	        vehiculosGaraje.clear(); // Limpiar la lista antes de agregar nuevos vehículos

	        while (rs.next()) {
	            String matricula = rs.getString("Matricula");
	            String marca = rs.getString("Marca");
	            String modelo = rs.getString("Modelo");
	            String color = rs.getString("Color");
	            
	            Vehiculo vehiculo = new Vehiculo(matricula, marca, modelo, color);
	            vehiculosGaraje.add(vehiculo);
	        }
	        
	        // Stream mostrar todos los vehiculos
	        vehiculosGaraje.stream()
	            .forEach(vehiculo -> System.out.println(vehiculo));

	    } catch (SQLException e) {
	        System.err.println("Error al ejecutar la consulta SQL: " + e.getMessage());
	    }
	}

	/**
     * Lista todos los clientes usando Streams.
     * @param clientes La lista de clientes.
     */
	public static void listarTodosLosClientes(List<Cliente> clientes) {
		if (clientes.isEmpty()) {
	        System.out.println("No hay clientes registrados.");
	    } else {
	        System.out.println("Listado de todos los clientes:");
	        for (Cliente cliente : clientes) {
	            System.out.println(cliente.toString());
	            System.out.println("----------------------------------------");
	        }
	    }
	}

	/**
     * Filtra los vehículos por marca usando Streams.
     * @param vehiculosGaraje La lista de vehículos en el garaje.
     */
	public static void filtrarVehiculosPorMarca(List<Vehiculo> vehiculosGaraje, Scanner scanner) {
		scanner.nextLine(); // Limpiar el buffer de entrada
	    System.out.println("Introduce la marca de los vehículos a filtrar: ");
	    String marca = scanner.nextLine().trim(); // Leer la marca y eliminar espacios en blanco

	    List<Vehiculo> vehiculosFiltrados = vehiculosGaraje.stream()
	            .filter(v -> v.getMarca().equalsIgnoreCase(marca))
	            .collect(Collectors.toList());

	    if (!vehiculosFiltrados.isEmpty()) {
	        System.out.println("Vehículos encontrados de la marca " + marca + ":");
	        vehiculosFiltrados.forEach(System.out::println);
	    } else {
	        System.out.println("No se encontraron vehículos de la marca " + marca);
	    }
	}

}

package Proyecto;


import java.sql.*;
import java.util.*;
/**
 * Esta clase proporciona métodos para interactuar con la tabla Ticket en la base de datos.
 */
public class TicketDAO {

	 /**
     * Crea un nuevo ticket en la base de datos.
     * @param scanner El objeto Scanner para entrada de usuario.
     */
    public static void crearTicket(Scanner scanner) {
        try {
        	scanner.nextLine();
            System.out.println("Ingrese la matrícula del vehículo:");
            String matricula = scanner.nextLine();

            System.out.println("Ingrese el nombre del cliente:");
            String nombre = scanner.nextLine();

            System.out.println("Ingrese el apellido del cliente:");
            String apellido = scanner.nextLine();

            int clienteID = obtenerIDCliente(nombre, apellido);
            if (clienteID == -1) {
                System.out.println("No se encontró ningún cliente con ese nombre y apellido.");
                return;
            }

            System.out.println("Ingrese el ID de la plaza:");
            int plazaID = Integer.parseInt(scanner.nextLine());

            System.out.println("Ingrese la fecha de entrada (YYYY-MM-DD HH:mm:ss):");
            String fechaEntradaStr = scanner.nextLine();
            java.util.Date fechaEntrada = java.sql.Timestamp.valueOf(fechaEntradaStr);

            System.out.println("Ingrese la fecha de salida (deje en blanco si no hay fecha de salida) (YYYY-MM-DD HH:mm:ss):");
            String fechaSalidaStr = scanner.nextLine();
            java.util.Date fechaSalida = null;
            if (!fechaSalidaStr.isEmpty()) {
                fechaSalida = java.sql.Timestamp.valueOf(fechaSalidaStr);
            }

            System.out.println("Ingrese el precio:");
            double precio = Double.parseDouble(scanner.nextLine());

            Ticket ticket = new Ticket(matricula, clienteID, plazaID, fechaEntrada, fechaSalida, precio);
            if (creacionTicket(ticket)) {
                System.out.println("¡Ticket creado exitosamente!");
            } else {
                System.out.println("Error al crear el ticket.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Entrada inválida. Asegúrese de ingresar números para los IDs y el precio.");
        }
    }
    
    /**
     * Obtiene el ID de un cliente basado en su nombre y apellido.
     * @param nombre El nombre del cliente.
     * @param apellido El apellido del cliente.
     * @return El ID del cliente o -1 si no se encuentra.
     */

    public static int obtenerIDCliente(String nombre, String apellido) {
        String sql = "SELECT ClienteID FROM Cliente WHERE Nombre = ? AND Apellido = ?";
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/parking","root","root");
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellido);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ClienteID");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID del cliente: " + e.getMessage());
        }
        return -1; // Retorna -1 si no se encontró el cliente
    }

    
    /**
     * Crea un nuevo ticket en la base de datos.
     * @param ticket El objeto Ticket a crear.
     * @return true si el ticket se creó correctamente, false de lo contrario.
     */
    public static boolean creacionTicket(Ticket ticket) {
        String sql = "INSERT INTO Ticket (Matricula, ClienteID, PlazaID, FechaEntrada, FechaSalida, Precio) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/parking","root","root");
             PreparedStatement pstmt = conexion.prepareStatement(sql)) {

            pstmt.setString(1, ticket.getMatricula());
            pstmt.setInt(2, ticket.getClienteID());
            pstmt.setInt(3, ticket.getPlazaID());
            pstmt.setTimestamp(4, new java.sql.Timestamp(ticket.getFechaEntrada().getTime()));
            pstmt.setTimestamp(5, ticket.getFechaSalida() != null ? new java.sql.Timestamp(ticket.getFechaSalida().getTime()) : null);
            pstmt.setDouble(6, ticket.getPrecio());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.err.println("Error al crear el ticket: " + e.getMessage());
            return false;
        }
    }
    
    
    
    /**
     * Borra un ticket de la base de datos.
     * @param conexion La conexión a la base de datos.
     * @param scanner El objeto Scanner para entrada de usuario.
     */
    
    public static void borrarTicket(Connection conexion,Scanner scanner) {
       
        
        System.out.print("Ingrese la ID del ticket que desea borrar: ");
        int ticketID = scanner.nextInt();

        String sql = "DELETE FROM Ticket WHERE TicketID = ?";

        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setInt(1, ticketID);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Ticket borrado correctamente.");
            } else {
                System.out.println("No se encontró ningún ticket con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al borrar el ticket: " + e.getMessage());
        };
    }
    
    
    /**
     * Modifica la fecha de salida de un ticket en la base de datos.
     * @param conexion La conexión a la base de datos.
     * @param scanner El objeto Scanner para entrada de usuario.
     */
    public static void modificarTicket(Connection conexion,Scanner scanner) {
        

        System.out.print("Ingrese la ID del ticket que desea modificar: ");
        int ticketID = scanner.nextInt();

        
        System.out.print("Ingrese la nueva fecha de salida (formato: yyyy-MM-dd HH:mm:ss): ");
        String nuevaFechaSalida = scanner.next();

        
        String sql = "UPDATE Ticket SET FechaSalida = ? WHERE TicketID = ?";

        try {
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, nuevaFechaSalida);
            pstmt.setInt(2, ticketID);

            int filasAfectadas = pstmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Ticket modificado correctamente.");
            } else {
                System.out.println("No se encontró ningún ticket con el ID proporcionado.");
            }
        } catch (SQLException e) {
            System.err.println("Error al modificar el ticket: " + e.getMessage());
        } 
    }
    
}
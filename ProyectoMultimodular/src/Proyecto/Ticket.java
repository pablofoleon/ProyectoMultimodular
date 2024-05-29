package Proyecto;

import java.util.Date;
/**
 * Esta clase representa un ticket de parking.
 */
public class Ticket {
     String matricula;
     int clienteID;
     int plazaID;
     Date fechaEntrada;
     Date fechaSalida;
     double precio;

    /**
     * Constructor para crear un nuevo objeto Ticket.
     * @param matricula La matrícula del vehículo asociado al ticket.
     * @param clienteID El ID del cliente asociado al ticket.
     * @param plazaID El ID de la plaza asociada al ticket.
     * @param fechaEntrada La fecha y hora de entrada del vehículo al parking.
     * @param fechaSalida La fecha y hora de salida del vehículo del parking (puede ser null si el vehículo aún está en el parking).
     * @param precio El precio del ticket.
     */
    public Ticket(String matricula, int clienteID, int plazaID, Date fechaEntrada, Date fechaSalida, double precio) {
        this.matricula = matricula;
        this.clienteID = clienteID;
        this.plazaID = plazaID;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
    }


    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getClienteID() {
        return clienteID;
    }

    public void setClienteID(int clienteID) {
        this.clienteID = clienteID;
    }

    public int getPlazaID() {
        return plazaID;
    }

    public void setPlazaID(int plazaID) {
        this.plazaID = plazaID;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

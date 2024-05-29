package Proyecto;

/**
 * La clase Moto representa un tipo de vehículo que puede ser almacenado en un parking.
 */
public class Moto extends Vehiculo {
     String tipoMoto;
     
     public Moto() {
    	 
     }
    /**
     * Constructor para la clase Moto.
     * 
     * @param matricula La matrícula de la moto.
     * @param marca La marca de la moto.
     * @param modelo El modelo de la moto.
     * @param color El color de la moto.
     * @param tipoMoto El tipo de moto (ej. Deportiva, Scooter, etc.).
     */
    public Moto(String matricula, String marca, String modelo, String color, String tipoMoto) {
        super(matricula, marca, modelo, color);
        this.tipoMoto = tipoMoto;
    }
	public String getTipoMoto() {
		return tipoMoto;
	}
	public void setTipoMoto(String tipoMoto) {
		this.tipoMoto = tipoMoto;
	}
	@Override
	public String toString() {
		return "Moto [tipoMoto=" + tipoMoto + "]";
	}
    
    
}
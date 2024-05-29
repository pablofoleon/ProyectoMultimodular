package Proyecto;

/**
 * La clase Coche representa un tipo  de vehículo que puede ser almacenado en un parking.
 */

public class Coche extends Vehiculo {
	
     String tipoCoche;

     public Coche() {
    	 
     }
     /**
      * Constructor para la clase Coche.
      * 
      * @param matricula La matrícula del coche.
      * @param marca La marca del coche.
      * @param modelo El modelo del coche.
      * @param color El color del coche.
      * @param tipoCoche El tipo de coche (ej. Sedán, SUV, etc.).
      */
    public Coche(String matricula, String marca, String modelo, String color, String tipoCoche) {
        super(matricula, marca, modelo, color);
        this.tipoCoche = tipoCoche;
    }

	public String getTipoCoche() {
		return tipoCoche;
	}

	public void setTipoCoche(String tipoCoche) {
		this.tipoCoche = tipoCoche;
	}

	@Override
	public String toString() {
		return "Coche [tipoCoche=" + tipoCoche + "]";
	}
    
    
    
}
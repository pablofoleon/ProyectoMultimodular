package Proyecto;

/**
 * La clase Vehiculo representa un vehículo que puede ser almacenado en un parking.
 */
public class Vehiculo {
     String matricula;
     String marca;
     String modelo;
     String color;
    
    public Vehiculo()
    {
    	
    }
    /**
     * Constructor para la clase Vehiculo.
     * 
     * @param matricula La matrícula del vehículo.
     * @param marca La marca del vehículo.
     * @param modelo El modelo del vehículo.
     * @param color El color del vehículo.
     */
    public Vehiculo(String matricula, String marca, String modelo, String color) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
    }
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "Vehiculo [matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", color=" + color
				+ "]";
	}

    
    
}

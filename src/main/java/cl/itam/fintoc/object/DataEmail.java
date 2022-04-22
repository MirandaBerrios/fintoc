package cl.itam.fintoc.object;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DataEmail {
	
	private String nombre;
	private Integer movimientos; 
	private Integer ok; 
	private Integer error; 
	private String fechaInicio; 
	private String fechaFin;
	private String cuenta; 
	private String banco; 
	
	 
	
	
}

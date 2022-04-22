package cl.itam.fintoc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString	
public class CuentaCorriente {

	
	private String id_cce;
	private String id_ctate; 
	private String id_emisor; 
	private String login; 
	private String created;
	private String update; 

}

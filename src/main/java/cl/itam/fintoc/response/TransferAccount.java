package cl.itam.fintoc.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(Include.NON_NULL)
public class TransferAccount {
	
	private String holder_id;
	private String holder_name; 
	private String number; 
	private InstitutionMovement institution; 

}

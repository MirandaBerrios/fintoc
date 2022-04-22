package cl.itam.fintoc.vo.paymentIntent;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Controller
@Setter
@Getter
@ToString
public class AccountRecipientSender {

	@JsonProperty("holder_id")
	public String holder_id;
	public String number; 
	public String type; 
	@JsonProperty("institucion_id")
	public String institucion_id; 
	
	
}

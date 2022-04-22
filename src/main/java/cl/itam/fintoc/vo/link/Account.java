package cl.itam.fintoc.vo.link;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Controller
@Setter
@Getter
@ToString
public class Account {
	public String id;
	public String object;
	public String name;
	public String number;
	public String type;
	public String currency;
	public Balance balance; 
	
	@JsonProperty("official_name")
	public String official_name;
	
	
	
	@JsonProperty("holder_id")
	public String holder_id;
	
	@JsonProperty("holder_name")
	public String holder_name;
	
	
	
	
	

}

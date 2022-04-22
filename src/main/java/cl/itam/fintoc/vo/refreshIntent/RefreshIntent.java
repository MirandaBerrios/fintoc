package cl.itam.fintoc.vo.refreshIntent;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Controller
@Setter
@Getter
@ToString
public class RefreshIntent {

	public String id; 
	
	@JsonProperty("refreshed_object")
	public String refreshed_object;
	
	@JsonProperty("refreshed_object_id")
	public String refreshed_object_id; 
	
	public String object ; 
	public String status; 
	
	@JsonProperty("new_movements")
	public String new_movements;
}

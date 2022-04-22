package cl.itam.fintoc.vo.link;

import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Controller
@Setter
@Getter
@ToString
public class Balance {

	public int available;
	public int current; 
	public int limit;
	
}

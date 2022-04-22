package cl.itam.fintoc.object;

import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Controller
@Setter
@Getter
@ToString
public class LinkToken {
	
	public String id_fintoc;
	public String rut;
	public String banco;
	public String pais;
	public String link_token;
	public String created;
	public String activated; 
	
}

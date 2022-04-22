package cl.itam.fintoc.vo.linkAndStatus;

import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Controller
public class LinkStatus {

	public String id; 
	public String active; 
}

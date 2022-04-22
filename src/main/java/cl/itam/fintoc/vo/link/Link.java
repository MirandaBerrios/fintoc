package cl.itam.fintoc.vo.link;

import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Controller
public class Link {
 
		public String type;
		public Data data;    

}

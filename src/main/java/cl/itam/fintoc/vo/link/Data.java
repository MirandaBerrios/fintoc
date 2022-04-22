package cl.itam.fintoc.vo.link;

import java.util.List;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Controller
@Setter
@Getter
@ToString
public class Data {
	
	@JsonProperty("id")
	public String id_fintoc;
	
	@JsonProperty("holder_id")
	public String holder_id; 
	public String username;
    
	@JsonProperty("holder_type")
    public String holder_type;
    
	@JsonProperty("created_at")
    public String created_at;
    
    public Institution institution;
    @JsonProperty("link_token")
    public String link_token;
	public String mode;
    public String active;
    public String status;
    public String object;
    public List<Account> accounts;
	    
}

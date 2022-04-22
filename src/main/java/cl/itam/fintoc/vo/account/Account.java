package cl.itam.fintoc.vo.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@JsonInclude(Include.NON_NULL)
public class Account{

	private String id;
	private String type; 
	private String number;
	@JsonProperty("official_name")
	private String official_name;
	private Balance balance;
	@JsonProperty("holder_id")
	private String holder_id;
	@JsonProperty("holder_name")
	private String holder_name;
	private String currency; 
	@JsonProperty("refreshed_at")
	private String refreshed_at;
	private String object; 
	private String linkToken;
	
}

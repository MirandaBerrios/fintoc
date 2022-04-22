package cl.itam.fintoc.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonProperty;

@Setter
@Getter
@ToString
@JsonInclude(Include.NON_NULL)
public class GetMovementResponse {
	
	private String id ; 
	private String object ;
	private int amount;
	@JsonProperty("post_date")
	private String post_date;
	private String description;
	@JsonProperty("transaction_date")
	private String transaction_date;
	private String currency;
	@JsonProperty("reference_id")
	private String reference_id; 
	private String type;
	private Boolean pending;
	@JsonProperty("recipient_account")
	private TransferAccount recipient_account ; 
	private TransferAccount sender_account ;
	private String comment; 
}

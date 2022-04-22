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
public class PaymentIntent {

	public String id; 
	public String object;
	public int amount; 
	public String currency; 
	@JsonProperty("widget_token")
	public String widget_token; 
	public String status; 
	@JsonProperty("recipient_account")
	public AccountRecipientSender recipient_account; 
	@JsonProperty("sender_account")
	public AccountRecipientSender sender_account;
	@JsonProperty("references_id")
	public String references_id;
	@JsonProperty("transaction_date")
	public String trasaction_date; 
	public String mode; 
	@JsonProperty("created_at")
	public String created_at; 
}

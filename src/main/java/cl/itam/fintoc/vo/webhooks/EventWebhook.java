package cl.itam.fintoc.vo.webhooks;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class EventWebhook { 
	
	
	public String id;
	public String type;
	public String mode;
	public String createdAt; 
	public DataWebhook data;
	public String object;
	
}

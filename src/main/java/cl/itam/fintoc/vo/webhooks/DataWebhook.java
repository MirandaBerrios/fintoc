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
public class DataWebhook {
		
	
	public String object;
	public String refreshedObject;
	public String refreshedIbjectId;
	public String status;
	public String createdAt;

}

package cl.itam.fintoc.vo.account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Balance {

	private int available;
	private int current;
	private int limit; 
}

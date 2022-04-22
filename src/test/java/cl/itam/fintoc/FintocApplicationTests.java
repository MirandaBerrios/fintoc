package cl.itam.fintoc;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cl.itam.fintoc.service.serviceImpl.EmailServiceImpl;

@SpringBootTest
class FintocApplicationTests {

	@Autowired
	EmailServiceImpl emailServiceImpl; 
	@Test
	void getEmpresas() {		
		 
	
	}

}

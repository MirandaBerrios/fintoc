package cl.itam.fintoc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import cl.itam.fintoc.mapper.LinkTokenMapper;
import cl.itam.fintoc.vo.link.Link;

@RestController
@RequestMapping("/webhook")
public class WebHookController {

	@Autowired
	LinkTokenMapper linkTokenMapper;
	
//	@Autowired
//	FintocServiceImpl fintoServiceImpl;
	
	@RequestMapping(value = "/link", method = RequestMethod.POST,  produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<?> updateTransactionsDocs(@RequestBody Link request ) throws JsonProcessingException  {	
		
		
		
		if(request.getData().getActive().equals("True")) {
			request.getData().setActive("S");
		}else {
			request.getData().setActive("S");
		}
		
		if(request.getData().getHolder_id() != null) {
			request.getData().setHolder_id(request.getData().getHolder_id().substring(0,8)+"-"+ request.getData().getHolder_id().substring(8,9));
			System.err.println("INSERTANDO TOKEN" + request);
			linkTokenMapper.insertLinkToken(request);
			
		}
												
	return new ResponseEntity<Link>(request, HttpStatus.OK);
	 
	}
	
	
}

package cl.itam.fintoc.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.itam.fintoc.mapper.LinkTokenMapper;
import cl.itam.fintoc.object.LinkToken;
import cl.itam.fintoc.service.LinkTokenService;

@Service
public class LinkTokenServiceImpl implements LinkTokenService {

	@Autowired
	LinkTokenMapper linkTokenMapper;
	
	@Override
	public List<LinkToken> getLinkToken() {
		
		return linkTokenMapper.getLinkTokenFintoc();
	}

}

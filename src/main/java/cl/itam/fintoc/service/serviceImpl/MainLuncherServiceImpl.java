							package cl.itam.fintoc.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

																																									
import cl.itam.fintoc.entity.Itx;
import cl.itam.fintoc.mapper.ItxMapper;
import cl.itam.fintoc.object.DataEmail;
import cl.itam.fintoc.object.LinkToken;
import cl.itam.fintoc.service.LinkTokenService;
import cl.itam.fintoc.service.MainLuncherService;
import cl.itam.fintoc.vo.account.Account;
import cl.itam.fintoc.vo.movement.Movement;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MainLuncherServiceImpl implements MainLuncherService {
	
	@Autowired
	FintocServiceImpl fintocServiceImpl;
	
	@Value("${fintoc.date.format}")
	String fechaFormato; 
	
	@Autowired
	LinkTokenService linkTokenService;
	
	@Autowired
	ItxMapper itxMapper; 
	
	@Autowired
	EmailServiceImpl emailServiceImpl; 

	
	
	@Scheduled(cron = "${cron.launchtime}")
	public void  mainLoadProcess(){
		
		log.info("------COMINEZA LA SINCRONIZACIÓN DE MOVIMIENTOS------ ");
		
		
		log.info("sincronizando los estados de los linkToken");
		fintocServiceImpl.changeStatusLinksByFintocApi();
		//cambiar el estado a S o  N (T o F)  
		
		fintocServiceImpl.cleanAllList();
		fintocServiceImpl.fillAllList();
		
		

//      Setting the current date for the url builder 
//	String fechaAyer = fintocServiceImpl.getDateYesterday();
//	String fechaHoy = fintocServiceImpl.getDateNow();
//		
		String fechaAyer = "2022-02-22";
		String fechaHoy  = "2022-02-23";	
//		
		
//		LLenamos la lista con los listToken de la base de datos
		List<LinkToken> requests = linkTokenService.getLinkToken();
		DataEmail dataEmail = new DataEmail();
		dataEmail.setFechaInicio(fintocServiceImpl.getDateNow());
		requests.forEach(req->{
			
			System.err.println("id fintoc " + req.getId_fintoc());			
			if(req.getActivated().equalsIgnoreCase("S")) {
				String linkToken = req.link_token;
				String since = fechaAyer; 
				String until = fechaHoy;
				String perPage = "300";
				
				List<Account> accounts = fintocServiceImpl.getAllAccounts(linkToken);
				log.info("Obteniendo las cuentas desde fintoc");
				accounts.forEach(acc->{		
					
					
					dataEmail.setNombre(fintocServiceImpl.getNameEmpresa(acc));
					
					String idCuenta = acc.getId();
					List<Movement> arrayTemp = 	fintocServiceImpl.getAllMovements(idCuenta , linkToken, since, until, perPage);	
					List<Itx> itxs= new ArrayList<>();	
					arrayTemp.forEach(item->{	
							Itx itx = fintocServiceImpl.generateItx(item, acc, req);
							
							itxs.add(itx);
						 
							});
				
				if(!itxs.isEmpty()) {
				  Integer rows = itxMapper.insertAllItx(itxs);
				 dataEmail.setMovimientos(fintocServiceImpl.countMovement(itxs));
				 dataEmail.setError(fintocServiceImpl.getErrorsItx(itxs));
				 dataEmail.setOk(fintocServiceImpl.getOkItx(itxs));
				 dataEmail.setFechaFin(fintocServiceImpl.getDateNow());
				 dataEmail.setCuenta(acc.getNumber());
			     emailServiceImpl.sendEmail(dataEmail , itxMapper.getEmailStatusById(req.getId_fintoc()));
			     fintocServiceImpl.setUpdateDate(req);
			     log.info("Se ha actualizado el correctamente :  " + req.id_fintoc + " " );
				 log.info("Se han registrado " +rows+ "  datos con éxito en la base de datos");
				  
				}else {
				 log.info("No se han registrado movimientos para este día , en esta cuenta " );
				}
				
			});
		   }else {
			   log.info("Hay Cuentas inactivas , sus movimientos no han sido almacenados");
		   }
			
			
			log.info("------SE HA COMPLETADO CON ÉXITO EL PROCESO------");
		});
		
		//fintocServiceImpl.setUpdateDate();
		log.info(" Se han actualizado las cuentas");
		
	}

}

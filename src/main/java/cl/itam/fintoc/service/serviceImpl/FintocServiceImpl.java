package cl.itam.fintoc.service.serviceImpl;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.CRC32;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cl.itam.fintoc.entity.CuentaProceso;
import cl.itam.fintoc.entity.Emisor;
import cl.itam.fintoc.entity.Empresa;
import cl.itam.fintoc.entity.Itx;
import cl.itam.fintoc.mapper.ItxMapper;
import cl.itam.fintoc.mapper.LinkTokenMapper;
import cl.itam.fintoc.object.LinkToken;
import cl.itam.fintoc.service.FintocService;
import cl.itam.fintoc.service.LinkTokenService;
import cl.itam.fintoc.vo.account.Account;
import cl.itam.fintoc.vo.link.Data;
import cl.itam.fintoc.vo.linkAndStatus.LinkStatus;
import cl.itam.fintoc.vo.movement.Movement;
import lombok.extern.log4j.Log4j2;


@Log4j2
@Service
public class FintocServiceImpl implements FintocService {
	
	@Value("${fintoc.date.format}")
	String fechaFormato; 
	
	@Value("${fintoc.api.url}")

	String apiUrl;

	@Value("${fintoc.authorization}")
	String authorization;
	
	@Autowired
	LinkTokenService linkTokenService;
	
	@Autowired
	LinkTokenMapper linkTokenMapper; 
	
	@Autowired
	ItxMapper itxMapper; 
	
	
	static List<Empresa> empresas = new ArrayList<>();
	static List<Emisor> emisores = new  ArrayList<>();
	static List<CuentaProceso> cuentasProcesos = new ArrayList<>();
	private String application;
	private HttpHeaders httpHeaders;


	/**
 * Se limpian las listas státicas 
 * @return void
	*/
@Override
public void cleanAllList() {
		empresas = new ArrayList<>();
		emisores = new ArrayList<>();
		cuentasProcesos = new ArrayList<>();
}


/**
 * Se llenan las listas estáticas
 * @return void
 */
@Override
public void fillAllList() {
		empresas = itxMapper.getAllEmpresa();
		emisores = itxMapper.getAllEmisores();
		cuentasProcesos = itxMapper.getAllCuentaProceso();
}	
	/**
	 * Se obtienene todas las cuentas , mediante la api de fintoc 
	 * @param linkToken 
	 * @return List<Account>
	 */
	@Override
public List<Account> getAllAccounts(String linkToken) {
		try {
									
			if(!linkToken.isEmpty()) {
				log.info("Se inicia la captura de las cuentas desde fintoc");
				RestTemplate restTemplate = new RestTemplate();	
				
//				Establecemos los headers que viajarán con el url
				HttpHeaders headers = new HttpHeaders();
				headers.add("User-Agent", "Application");
				headers.add("Authorization", authorization);
				
//				Lista que almcenará las cuentas que iremos construyendo
				List<Account> accounts = new ArrayList<>();
				
				String url = apiUrl + "accounts/?link_token=" + linkToken; 
				
				ResponseEntity<Account[]> result = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(headers), Account[].class);
				
				try {
					
					log.info("Las cuentas están siendo almacenadas en la memoria");
		
//					Guardamos la respuesta en un Array[] , lo parseamos a List, 
//					guardamos el campo linkToken y lo agregamos a la lista accounts
					List<Account> array = Arrays.asList(result.getBody());
					for (Account account : array) {
						account.setLinkToken(linkToken);
						accounts.add(account);			
					}
					log.info("Se han obtenido las cuentas satisfactoriamente");
				} catch (Exception e) {
					log.error("No se han podido obtener las cuentas" + e);
				}
				return accounts;	
			}else {
				log.error("La solicitud a sido rechazada desde el servicio fintoc");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Ha ocurrido un error al momento de  sincronizar" +  e);
			
		}
				return null;
	}

/** 
 * Se obtienen todos los movimientos desde la api fintoc 
 * @param con estos parámetros contruiremos el url y los datos necesarios para solicitar los datos a la api
 * @return	List<Movement>
 */
public List<Movement> getAllMovements(String idCuenta , String linkToken, String since, String until, String perPage){
	String url = apiUrl + "accounts/" 
			+ idCuenta +"/movements?" 
			+ "link_token=" + linkToken 
			+ "&since=" + since 
			+ "&until=" + until 
			+ "&per_page="+ perPage  ; 
	
	HttpHeaders headers = new HttpHeaders();
	headers.add("User-Agent", "Application");
	headers.add("Authorization", authorization);
	
	RestTemplate restTemplate = new RestTemplate();
	ResponseEntity<Movement[]> result = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(headers), Movement[].class);

	List<Movement> arrayTemp = Arrays.asList(result.getBody());	
	return arrayTemp;
}


/**
 * Obtenemos el nombre de la empresa dado un holder_id que es provisto por la api 
 * @param acc
 * @return retornará un String con el nombre de la empresa
 */
public String getNameEmpresa(Account acc ) {
	String nombreEmpresa = "";
	try {
		Empresa empresa = empresas.stream().filter(e-> e.getRut().replaceAll("\\.", "").replaceAll("-", "").equals(acc.getHolder_id())).findAny().orElse(null);
		nombreEmpresa = empresa.getNombre();
	
	} catch (Exception e2) {
		log.error("Error: empresa no encontrada", e2);
	}
	return nombreEmpresa; 
}

/**
 * Calcularemos la cantidad de errores que presentará la inserción de las itx
 * @param
 * @return Integer
 */

public Integer getErrorsItx(List<Itx> itxs) {
	Integer count = 0; 
	for(Itx itx : itxs) {
		if(itx.getItx_estado() != "CRE") {		
			count++;
		}
	}
	
	return count; 
}

/**
 * Calcularemos la cantidad de movimientos correctos que presentará la inserción de las itx 
 * @param
 * @return Integer
 */
public Integer getOkItx(List<Itx> itxs) {
	Integer count = 0; 
	for(Itx itx : itxs) {
		if(itx.getItx_estado() == "APR"	) {		
			count++;
		}
	}
	
	return count; 
}

/**
 * Calcularemos la cantidad de movimientos en la inserción de las itx 
 * @param
 * @return Integer
 */
public Integer countMovement(List<Itx> itxs) {
	Integer count = 0 ;
	for(int i=0 ; i<= itxs.size()-1 ; i++) {
		count++; 	
	}
	return count; 
}


/**
 * Generamos la itx con cada uno de los datos necesarios para poder gatillar el proceso que completará los datos
 * necesarios para poder completar el registro de manera correcta en la base de datos. 
 * @param
 * @return retornará un objeto del tipo Itx 
 */
public Itx generateItx(Movement movement , Account acc , LinkToken req) {
	
	Itx itx = new Itx();
	
	
	Empresa empresa = empresas.stream().filter(e -> e.getRut().replaceAll("\\.", "").replaceAll("-", "").equals(acc.getHolder_id())).findAny().orElse(null);	
	CuentaProceso cuentaProceso = cuentasProcesos.stream().filter(e -> e.getCtaCodigo().equals(acc.getNumber())).findAny().orElse(null);
	
	String fileName = "movimiento" + acc.getNumber() + movement.getPost_date().substring(0,10);
//	String country = (movement.getRecipient_account() == null)?"DEFAULT":movement.getRecipient_account().getInstitution().getCountry().toUpperCase();	
	//String country = itxMapper.getCountryByCode(req.getPais().toUpperCase()); 
	itx.setItx_estado("CRE");
	
	
	if(cuentaProceso == null) {
		itx.setItx_estado("EMI");                                                        
	}else {
		itx.setItx_banco(cuentaProceso.getBancoCodigo());
		itx.setItx_emisor(cuentaProceso.getEmisorCodigo());
	}
	
		
	if(	empresa == null) {
		itx.setItx_estado("EMP");                                                        
     
	}else {
		itx.setItx_empresa(empresa.getCodigo());        
	}
	
	
	// Se cambia el nombre del país eg(CHILE) por sólo el código (CL)
	//itx.setItx_pais(country);
	itx.setItx_pais(req.getPais().toUpperCase());
	itx.setItx_canal(movement.getType());
	itx.setItx_local("DEFAULT");
	itx.setItx_tipo_proceso("DEP");
	itx.setItx_formapago(acc.getType());
	itx.setItx_tipotrx(movement.getType());
	itx.setItx_fecha(movement.getPost_date().substring(0,10));
	itx.setItx_moneda(movement.getCurrency());
	itx.setItx_monto(Integer.toString(movement.getAmount()));
	itx.setItx_rpta_glosa(movement.getDescription()); 
	itx.setItx_file(fileName);
	itx.setItx_lote(getCRC32(fileName.getBytes()));
	itx.setItx_cta_cte(acc.getNumber());
	itx.setItx_tarjeta("DEFAULT");
	itx.setItx_estadotrx("APR");
	itx.setItx_fechacon(movement.getPost_date().substring(0,10));
	itx.setItx_origen("OPB");
	itx.setItx_sistema("OPB");
	

	return itx;
	
}

/**
 * Consultaremos a la api fintoc cuales son los linkTokens que están activos, inactivos y los que
 * han sido eliminados, si existe algún linkToken eliminado, este persistirá en la base de datos, pero
 * su estado será inactivo , no contemplandose en el proceso
 * @return void
 */
@Override
public void changeStatusLinksByFintocApi() {
	
	String url = apiUrl + "links"; 
	
	RestTemplate restTemplate = new RestTemplate();
	
	HttpHeaders httpHeaders = new HttpHeaders();
	httpHeaders.add("Authorization", authorization );
	httpHeaders.add("User-Agent", "Application");
	ResponseEntity<Data[]> result = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(httpHeaders), Data[].class);
	
	
	try {
		List<Data> array = Arrays.asList(result.getBody());
		
		List<LinkStatus> linkStatus = new ArrayList<LinkStatus>();
		
		array.forEach(item->{
			LinkStatus tempLink = new LinkStatus(); 
			tempLink.setId(item.getId_fintoc());
			if(item.getActive() == "true") {
				tempLink.setActive("S");
			}else { tempLink.setActive("N");}
			
			linkStatus.add(tempLink);
			
		});

		if (!linkStatus.isEmpty()) {
			log.info("Setiamos todos los estados de los linkToken como inactivos");
			linkTokenMapper.setAllLinkTokenAsFalse();
			linkTokenMapper.updateLinkToken(linkStatus);
			log.info("Se han actualizado los estados de LinkToken");
		}
	} catch (Exception e) {
		log.error("Ha ocurrido un error al procesar los LinkTokens , error : " + e );
		
	}
	
}

/**
 * Obtiene la fecha del dia anterior 
 * @return retorna una fecha en formato dd/mm/yyy
 */
public String getDateYesterday() {

	DateTimeFormatter  dtf = DateTimeFormatter.ofPattern(fechaFormato); 
	String fecha = dtf.format(LocalDateTime.now().minusDays(1));
	return fecha; 
	}

/**
 * Obtiene la fecha del dia presente 
 * @return retorna una fecha en formato dd/mm/yyy
 */
public String getDateNow() {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern(fechaFormato);
	String fecha = dtf.format(LocalDateTime.now());
	return fecha; 
}

/**
 * Obtiene la fecha y hora del dia presente y actualiza los campos update
 */

public void setUpdateDate(LinkToken req) {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
	String fecha = dtf.format(LocalDateTime.now());
	try {
		itxMapper.updateUpdatedOpenBakingById(fecha, req.id_fintoc );
		log.info("Se ha actualizado openbanking con fecha : " + fecha);
	}catch(Exception e) {
		log.error("Ha ocurrido un error al actualizar la fecha, " + e );
	}
}

/**
 * Se utilizará para poder cifrar el nombre de el archivo 
 * @param data
 * @return String , retornará el nombre del archivo ya encriptado
 */
public static String getCRC32(byte[] data) {
	String salida = "123456789";
	try {
		CRC32 fileCRC32 = new CRC32();
		fileCRC32.update(data);
		salida = new BigInteger(new Long(fileCRC32.getValue()).toString()).abs().toString();
	} catch (Exception e) {
		e.printStackTrace();
	}

	return salida;
}



}
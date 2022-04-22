package cl.itam.fintoc.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import cl.itam.fintoc.mapper.ItxMapper;
import cl.itam.fintoc.object.DataEmail;
import cl.itam.fintoc.service.EmailService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
    TemplateEngine templateEngine; // From Thymeleaf
    
	@Autowired
    JavaMailSender mailSender;
	
	@Autowired
	

	
	 @Value("${notification.email}")
	 List<String> emails ; 
	 

	/*
	 * Se enviará un email de notificación sólo si ha seleccionado la opción en el portal 
	 */
	public void sendEmail(DataEmail email , String emailStatus) {
		
		if(emailStatus.equals("S")) {
		String processedHTMLTemplate = this.constructHTMLTemplate(email.getNombre(), email.getMovimientos(), email.getError(), email.getOk(), email.getFechaInicio(), email.getFechaFin(), email.getCuenta() , email.getBanco());

		log.info("ENVIANDO NOTIFICACIÓN A {}",emails);
		
		try {
			MimeMessagePreparator preparator = message -> {
				MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED,
						"UTF-8");
				helper.setFrom("soporte@retailsbs.com");
				helper.setTo(emails.toArray(new String[emails.size()])); 
				if (email.getError() == 0) {
					helper.setSubject("✔️ Ejecución OK " + email.getFechaInicio() + " OK:" + email.getMovimientos()+ " ERR:"
							+ email.getError());
				} else {
					helper.setSubject("❌ Important - Ejecución ERR " + email.getFechaInicio()  + " OK:"
							+ email.getMovimientos() + " ERR:" + email.getError());
				}
				helper.setText(processedHTMLTemplate, true);
			};
			
			mailSender.send(preparator);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		}
	}

	 
	 
	 
  
//	public void emailSend(String empresa, int procesados, int noProcesados, int cantidad, Date fechaInicio, Date fechaFin) {
//    	
//		String processedHTMLTemplate = this.constructHTMLTemplate(empresa ,procesados, noProcesados, cantidad, fechaInicio, fechaFin);
//					
//		
//			log.info("ENVIANDO NOTIFICACIÓN A {}",emails);
//			
//			try {
//				MimeMessagePreparator preparator = message -> {
//					MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED,
//							"UTF-8");
//					helper.setFrom("soporte@retailsbs.com");
//					helper.setTo(emails.toArray(new String[emails.size()])); 
//					if (noProcesados == 0) {
//						helper.setSubject("✔️ Ejecución OK " + fechaInicio + " OK:" + procesados + " ERR:"
//								+ noProcesados);
//					} else {
//						helper.setSubject("❌ Important - Ejecución ERR " + fechaInicio + " OK:"
//								+ procesados + " ERR:" + noProcesados);
//					}
//					helper.setText(processedHTMLTemplate, true);
//				};
//				
//				mailSender.send(preparator);
//			} catch (Exception e) {
//				log.error(e.getMessage());
//			}
//		
//					
//	}

	// Fills up the HTML file
	private String constructHTMLTemplate(String empresa , Integer movimientos, Integer errores, Integer ok, String fechaInicio, String fechaFin , String cuenta , String banco) {
				
		
		Context context = new Context();
		context.setVariable("empresa", empresa);
		context.setVariable("movimientos", movimientos);
		context.setVariable("errores", errores);
		context.setVariable("ok", ok);
		context.setVariable("fechaInicio", fechaInicio);
		context.setVariable("fechaFin", fechaFin);
		context.setVariable("cuenta", cuenta);
		context.setVariable("banco", banco);
		

		return templateEngine.process("email_notification", context);
	}
    
    
}
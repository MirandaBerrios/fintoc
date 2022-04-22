package cl.itam.fintoc.service;
import java.util.Date;

import cl.itam.fintoc.object.DataEmail;


public interface EmailService {
	
//    public void emailSend(String empresa, int procesados, int noProcesados, int cantidad, Date fechaInicio, Date fechaFin);
    
    public void sendEmail(DataEmail email , String emailStatus);

}
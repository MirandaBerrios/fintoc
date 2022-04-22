package cl.itam.fintoc.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Scope;

public class IBSSUtil {

	public static String getCurrentTimeStamp() { 
	    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy;HH:mm:ss");//dd/MM/yyyy 
	    Date now = new Date(); 
	    String strDate = sdfDate.format(now); 
	    return strDate; 
	}
	
	@Scope("singleton")
	public static class Codigos {
		static Codigos instance;

		// ERRORES
		public static final String PARAMETROSINVALIDOS = "002;ERR-002 - PARAMETROSINVALIDOS - parametros inválidos";
		public static final String RUTFICHAOCONTASENAINVALIDOS = "ERR-002;Rut, ficha y/o contraseña inválidos ";
		public static final String FALLOAUTENTIFICACION = "ERR-003;Usuario no autentificado";
		public static final String APPINVALIDA = "ERR-004;La aplicación ingresada no corresponde.";
		public static final String VERSIONOBSOLETA = "ERR-005;Por favor primero actualice la aplicación";
		public static final String FALLOENVIOCORREO = "ERR-006;No pudo ser enviado el correo para recuperar su contreseña. Intente nuevamente";
		public static final String SINEMAIL = "ERR-007;No posee registrado un email, por favor diríjase al INO. Gracias.";
		public static final String ENCUESTANOGUARDADA = "ERR-008;Encuesta no pudo ser guardada";
		public static final String TOKENORUTINVALIDO = "ERR-009;Token ó rut inválido";
		public static final String CITANOANULADA = "ERR-010;La cita no pudo ser anulada";
		public static final String RUTINVALIDO = "ERR-011;Rut debe ser sin puntos, Ejemplo: 12345678-9";
		public static final String FICHAINVALIDA = "ERR-012;Ficha debe ser numerica, Ejemplo: 12345";
		public static final String PACIENTEINACTIVO = "ERR-013;Paciente inactivo, no puede ingresar";
		public static final String PACIENTEBLOQUEADO = "ERR-014;Paciente bloqueado, no puede ingresar";
		public static final String EMAILYAREGISTRADO = "ERR-015;El email ingresado ya ha sido registrado por otro usuario";
		public static final String NOTIFICACIONNOMARCADA = "ERR-016;Notificación no marcada";
		public static final String PASSWORDSNOCOINCIDENTES = "ERR-017;No pudo efectuarse el cambio de contraseña";
		public static final String ERRORINESPERADO = "003;ERR-003 - ERRORINESPERADO - Ha ocurrido un error inesperado, revise el log";
		public static final String INGRESONOREGISTRADO = "ERR-019;El ingreso no pudo ser registrado";
		public static final String NUMEROORDENYAREGISTRADO = "ERR-020;El número de orden de compra ya existe";
		public static final String FECHANOPERMITIDA = "ERR-021;La fecha ingresada no puede ser menor a la fecha actual";
		public static final String NOCUMPLEREQUISITOS = "ERR-022; Paciente no cumple con los requisitos para agendar cita";
		public static final String ERRORCONFIRMACION= "ERR-023; La cita no pudo ser confirmada";
		public static final String CONTRASEÑANOVALIDA= "ERR-024; La contraseña ingresada no corresponde";
		public static final String RUNERR = "000;ERR-002 - RUNERR - Inicio de proceso de carga desde cl_itx a cl_trx incorrecto";
		// FIN ERROR
		// OK
		public static final String NOTDATAFOUND= "001;ERR-001 - NOTDATAFOUND - No se encontraron datos";
		public static final String INFOOK = "000;OK-000 - INFOOK - Información correcta";
		public static final String RUNOK = "000;OK-000 - RUNOK - proceso de carga desde cl_itx a cl_trx iniciado correctamente";
		public static final String RUNENDOK = "200;OK-200 - RUNENDOK - proceso de carga de cl_itx a finalizado con éxito";
		public static final String OK = "200;OK-200 - OK - proceso de carga conectado con éxito";
		public static final String NOLIST = "200;NOLIST-200 - OK - no existen datos para procesar";
		public static final String NODATA = "002;NODATA-002 - ERR - Falta criterio para procesar";
		//CODIGOS TIPO DE OPERACION
		public static final int TIPO_OPERACION_GENERACION_CUOTA = 1;
		public static final int TIPO_OPERACION_PAGO = 2;
		public static final int TIPO_OPERACION_PAGO_TOLERANCIA_POSITIVA = 3;
		public static final int TIPO_OPERACION_PAGO_TOLERANCIA_NEGATIVA = 4;
		public static final int TIPO_OPERACION_PAGO_POR_APLICAR = 5;
		public static final int TIPO_OPERACION_ANULACION_CUOTA = 6;
		public static final int TIPO_OPERACION_REVERSO_PAGO = 7;
		public static final int TIPO_OPERACION_REVERSO_TOLERANCIA_POSITIVA = 8;
		public static final int TIPO_OPERACION_REVERSO_TOLERANCIA_NEGATIVA = 9;
		public static final int TIPO_OPERACION_REVERSO_PAGO_POR_APLICAR = 10;

		// CODIGOS TIPO TRX
		public static final String TIPO_TRX_CONTRACARGO = "CNT";
		public static final String TIPO_TRX_VENTA = "VTA";


		public static Codigos getInstance() {
			if (instance == null) {
				instance = new Codigos();
			}
			return instance;
		}

		public Codigos() {

		}
	}
	
	//FORMAT DATE
	public static String formatDate(Date date) { 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String strDate = dateFormat.format(date);  
        //System.out.println("Converted String: " + strDate); 
        return strDate;
	}
	
	//FORMAT DATE
	public static String formatDateCon(Date date) { 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        String strDate = dateFormat.format(date);  
        //System.out.println("Converted String: " + strDate); 
        return strDate;
	}
	

	public static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	
}

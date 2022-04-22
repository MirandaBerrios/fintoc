package cl.itam.fintoc.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cl.itam.fintoc.entity.CuentaProceso;
import cl.itam.fintoc.entity.Emisor;
import cl.itam.fintoc.entity.Empresa;
import cl.itam.fintoc.entity.Itx;

@Mapper
public interface ItxMapper {

@Insert({ "<script> "
		+ "INSERT INTO cl_itx "
		+ "(itx_empresa ,"
		+ "itx_tipo_proceso, "
        + "itx_pais ," 
        + "itx_canal,"
        + "itx_local,"
        + "itx_emisor,"
        + "itx_estado,"
        + "itx_formapago,"
        + "itx_tipotrx,"
        + "itx_fecha,"
        + "itx_moneda,"
        + "itx_monto,"
        + "itx_rpta_glosa,"
        + "itx_file,"  
        + "itx_lote,"
        + "itx_banco,"
        + "itx_cta_cte, "
        + "itx_tarjeta, "
        + "itx_estadotrx, "
        + "itx_fechacon, "
        + "itx_origen, "
        + "itx_sistema )"
		+ "VALUES"
		+ "<foreach collection='itx' item='item' index='index' separator=',' close=';' >" 
        + "(#{item.itx_empresa},"
		+ "#{item.itx_tipo_proceso}, "
        + "#{item.itx_pais} ," 
        + "#{item.itx_canal},"
        + "#{item.itx_local},"
        + "#{item.itx_emisor}," 
        + "#{item.itx_estado},"
        + "#{item.itx_formapago},"
        + "#{item.itx_tipotrx},"
        + "#{item.itx_fecha},"
        + "#{item.itx_moneda},"
        + "#{item.itx_monto},"
        + "#{item.itx_rpta_glosa},"
        + "#{item.itx_file},"  
        + "#{item.itx_lote},"
        + "#{item.itx_banco},"
        + "#{item.itx_cta_cte},"
        + "#{item.itx_tarjeta},"
        + "#{item.itx_estadotrx},"
        + "#{item.itx_fechacon},"
        + "#{item.itx_origen},"
        + "#{item.itx_sistema})"
		+ "</foreach>  "
		+ "</script> " })
int insertAllItx(@Param("itx") List<Itx> itx);



@Select({"SELECT "
		+ "   emi.codigo "
		+ " FROM "
		+ "    cl_ctacte   cta"
		+ "        JOIN"
		+ "    cl_ctacte_emisor  ctaem ON ctaem.id_ctacte = cta.id_ctacte"
		+ "        JOIN"
		+ "    cl_emisor emi ON emi.id_emisor = ctaem.id_emisor"
		+ " WHERE "
		+ "    cta.codigo = #{cuentaCorriente};"})
String getEmisorByQuery(@Param("cuentaCorriente") String cuentaCorriente);


@Select({"SELECT "
	    + "id_emisor "
	    + "	FROM "
	    + "cl_emisor "
	    + "where nombre like '%${banco}';"})
String getIdBancoByQuery(@Param("banco") String banco);


@Select({"SELECT "
	   + "    codigo  "
	   + "  FROM  "
	   + " cl_empresa "
	   + "  WHERE  "
	   + "    rut LIKE '%${rut}%';"})
String getCodigoEmpresaByQuery(@Param("rut") String rut );

//se deja en duro el rut de la empresa de test con rut 77777777-7
@Select({" SELECT "
		   + " emp.codigo,  "
		   + " coalesce(emp.rut, '') as rut , "
		   + " emp.nombre"
		   + "  FROM  "
		   + "    cl_empresa emp "
		   + " where activo = 'S' "
		   + "GROUP BY emp.nombre"})
List<Empresa> getAllEmpresa();

@Select({" SELECT "
	    + " id_emisor ,"
	    + " nombre "
	    + "	FROM "
	    + " cl_emisor "
		+ "where activo  = 'S'"})
List<Emisor> getAllEmisores(); 


@Select({" SELECT "
		+ " emi.codigo as emisorCodigo,"
		+ " cta.codigo as ctaCodigo, "
		+ " ba.id_banco as bancoCodigo"
		+ " FROM "
		+ "  cl_ctacte   cta "
		+ " JOIN "
		+ "  cl_ctacte_emisor  ctaem ON ctaem.id_ctacte = cta.id_ctacte "
		+ " JOIN "
		+ "  cl_emisor emi ON emi.id_emisor = ctaem.id_emisor "
		+ " JOIN "
		+ " banco ba ON ba.id_banco = cta.id_banco"
		+ " WHERE cta.activo = 'S' "})
List<CuentaProceso> getAllCuentaProceso();


@Update({"UPDATE cl_open_banking "
		+ "SET "
		+ " updated = #{date} "
		+ " WHERE "
		+ "    activated = 'S';"})
Integer updateDateTimeForAllLinkTokens(@Param("date") String date);

@Update({"UPDATE cl_open_banking "
	    + " SET "
		+ " updated_openbanking = #{date} "
	    + " WHERE "
		+ " id_fintoc = #{idFintoc} " })
Integer updateUpdatedOpenBakingById(@Param("date") String date , @Param("idFintoc") String idFintoc);

@Select({"SELECT nombre "
		+ "from cl_pais "
		+ "where codigo= #{code};"})
String getCountryByCode(@Param("code") String code);


@Select({"SELECT enviar_email from cl_open_banking where id_fintoc = #{idFintoc}"})
String getEmailStatusById(@Param("idFintoc") String idFintoc);
}



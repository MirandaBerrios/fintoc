package cl.itam.fintoc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import cl.itam.fintoc.object.LinkToken;
import cl.itam.fintoc.vo.link.Link;
import cl.itam.fintoc.vo.linkAndStatus.LinkStatus;

@Mapper
public interface LinkTokenMapper {

	
@Insert({"INSERT INTO cl_open_banking "
		+ "(rut,"
		+ " banco,"
		+ " pais,"
		+ " link_token,"
		+ " activated,"
		+ "id) "
		+ "VALUES "
		+ "(#{link.data.holder_id},"
		+ "#{link.data.institution.name},"
		+ "#{link.data.institution.country},"
		+ "#{link.data.link_token},"
		+ "#{link.data.active},"
		+ "#{link.data.id}) "})
public int insertLinkToken(@Param("link") Link link);


@Select({"SELECT "
		+ "rut ,"
		+ "banco,"
		+ "pais,"
		+ "link_token,"
		+ "created ,"
		+ "activated , "
		+ " id , "
		+ " id_fintoc , "
		+ "enviar_email "
		+ " FROM "
		+ "cl_open_banking "})
public List<LinkToken> getLinkTokenFintoc();

@Select({"SELECT link_token FROM cl_open_banking WHERE rut = #{rut}"})
public String getLinkTokenById(@Param("rut") String rut);

@Update({"<script>"
		+ "<foreach collection='arrayStatus' item='item' index='index' separator=';' close=';' >"
		+ "UPDATE cl_open_banking "
		+ "SET "
		+ "activated = #{item.active} "
		+ "WHERE  "
		+ " id = #{item.id} "
		+ "</foreach>  "
		+ "</script> "})
public void updateLinkToken(@Param("arrayStatus") List<LinkStatus> arrayStatus);

@Update({"UPDATE cl_open_banking"
	+ " SET "
	+ " activated = 'N' "})
public void setAllLinkTokenAsFalse();	


@Select({"SELECT emp.id_empresa "
		+ " FROM cl_open_banking ob "
		+ " JOIN cl_empresa emp on ob.rut = replace(emp.rut , '.' , ''); "
		+ " WHERE ob.rut = #{rut}"})
public String getIdEmpresaByRut(@Param("rut") String rut);


}
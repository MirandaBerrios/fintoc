package cl.itam.fintoc.service;

import java.util.List;

import cl.itam.fintoc.entity.Itx;
import cl.itam.fintoc.object.LinkToken;
import cl.itam.fintoc.vo.account.Account;
import cl.itam.fintoc.vo.movement.Movement;

public interface FintocService {
	
	public List<Account> getAllAccounts(String linkToken);
	public List<Movement> getAllMovements(String idCuenta , String linkToken, String since, String until, String perPage);
	public Itx generateItx(Movement movement , Account acc , LinkToken req);	
	public void changeStatusLinksByFintocApi();
	public void cleanAllList();
	public void fillAllList();
	public String getDateNow();
	public String getDateYesterday();
	public Integer getErrorsItx(List<Itx> itxs);
	public Integer getOkItx(List<Itx> itxs);
	public Integer countMovement(List<Itx> itxs);
	public void setUpdateDate(LinkToken req);
	
}

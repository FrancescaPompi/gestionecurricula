package it.gestionecurricula.service.esperienza;

import java.util.List;

import it.gestionecurricula.dao.esperienza.EsperienzaDAO;
import it.gestionecurricula.model.Esperienza;
import it.gestionecurricula.model.Curriculum;

public interface EsperienzaService {
	
	public void setEsperienzaDAO(EsperienzaDAO esperienzaDAO);
	
	public List<Esperienza> listAll() throws Exception;

	public Esperienza findById(Long idInput) throws Exception;

	public int aggiorna(Esperienza input) throws Exception;

	public int inserisciNuovo(Esperienza input) throws Exception;

	public int rimuovi(Esperienza input) throws Exception;

	public List<Esperienza> findByExample(Esperienza input) throws Exception;
	
	public List<Esperienza> findAllPerCurriculumOrdinate(Curriculum input) throws Exception;
}

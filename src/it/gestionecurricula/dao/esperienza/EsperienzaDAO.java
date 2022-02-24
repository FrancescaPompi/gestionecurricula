package it.gestionecurricula.dao.esperienza;

import java.util.List;

import it.gestionecurricula.dao.IBaseDAO;
import it.gestionecurricula.model.Curriculum;
import it.gestionecurricula.model.Esperienza;

public interface EsperienzaDAO extends IBaseDAO<Esperienza>{
	
	public List<Esperienza> findAllByCurriculumOrderBy(Curriculum input) throws Exception;
}

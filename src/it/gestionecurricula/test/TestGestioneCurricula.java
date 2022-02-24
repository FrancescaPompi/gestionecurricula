package it.gestionecurricula.test;

import it.gestionecurricula.service.MyServiceFactory;
import it.gestionecurricula.service.curriculum.CurriculumService;
import it.gestionecurricula.service.esperienza.EsperienzaService;

public class TestGestioneCurricula {

	public static void main(String[] args) {
		
		CurriculumService curriculumService = MyServiceFactory.getCurriculumServiceImpl();
		EsperienzaService esperienzaService = MyServiceFactory.getEsperienzaServiceImpl();
	}

}

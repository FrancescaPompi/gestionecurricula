package it.gestionecurricula.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import it.gestionecurricula.model.Curriculum;
import it.gestionecurricula.model.Esperienza;
import it.gestionecurricula.service.MyServiceFactory;
import it.gestionecurricula.service.curriculum.CurriculumService;
import it.gestionecurricula.service.esperienza.EsperienzaService;

public class TestGestioneCurricula {

	public static void main(String[] args) {
		
		CurriculumService curriculumService = MyServiceFactory.getCurriculumServiceImpl();
		EsperienzaService esperienzaService = MyServiceFactory.getEsperienzaServiceImpl();
		
		try {
			System.out.println("In tabella curriculum ci sono " + curriculumService.listAll().size() + " elementi.");
			
			testInserimentoCurriculum(curriculumService);
			System.out.println("In tabella ci sono " + curriculumService.listAll().size() + " elementi.");
			
			
			System.out.println("In tabella esperienza ci sono " + esperienzaService.listAll().size() + " elementi.");
			
			testInserimentoEsperienza(esperienzaService, curriculumService);
			System.out.println("In tabella esperienza ci sono " + esperienzaService.listAll().size() + " elementi.");

		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
	
	private static void testInserimentoCurriculum(CurriculumService curriculumService) throws Exception {
		System.out.println(".......testInserimentoCurriculum inizio.............");
		Curriculum nuovoCurriculum = new Curriculum("Mario", "Rossi", new Date(), "1234", "mr@example.com");
		if (curriculumService.inserisciNuovo(nuovoCurriculum) != 1)
			throw new RuntimeException("testInserimentoCurriculum FAILED ");

		System.out.println(".......testInserimentoCurriculum PASSED.............");
	}
	
	private static void testInserimentoEsperienza(EsperienzaService esperienzaService, CurriculumService curriculumService) throws Exception {
		System.out.println(".......testInserimentoEsperienza inizio.............");
		Esperienza nuovaEsperienza = new Esperienza("Cameriere", new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2020"), new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2021"), "team-work", curriculumService.findById(1l));
		if(esperienzaService.inserisciNuovo(nuovaEsperienza) != 1) {
			throw new RuntimeException("testInserimentoEsperienza FAILED ");
		}
		
		System.out.println(".......testInserimentoEsperienza PASSED.............");

	}

}

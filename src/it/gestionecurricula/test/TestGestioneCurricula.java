package it.gestionecurricula.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
			
			// test curriculum
			System.out.println("In tabella curriculum ci sono " + curriculumService.listAll().size() + " elementi.");
			
			testInserimentoCurriculum(curriculumService);
			System.out.println("In tabella ci sono " + curriculumService.listAll().size() + " elementi.");
			
			System.out.println("Prima dell'eliminazione: in tabella ci sono " + curriculumService.listAll().size() + " elementi.");
			testRimuoviCurriculum(curriculumService, esperienzaService);
			System.out.println("Dopo l'eliminazione: in tabella ci sono " + curriculumService.listAll().size() + " elementi.");
			
			// test esperienza
			System.out.println("In tabella esperienza ci sono " + esperienzaService.listAll().size() + " elementi.");
			
			testInserimentoEsperienza(esperienzaService, curriculumService);
			System.out.println("In tabella esperienza ci sono " + esperienzaService.listAll().size() + " elementi.");
			
			System.out.println("Prima della rimozione: in tabella esperienza ci sono " + esperienzaService.listAll().size() + " elementi.");
			testRimuoviEsperienza(esperienzaService);
			System.out.println("Dopo la rimozione: in tabella esperienza ci sono " + esperienzaService.listAll().size() + " elementi.");

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
	
	private static void testRimuoviCurriculum(CurriculumService curriculumService, EsperienzaService esperienzaService) throws Exception {
		System.out.println(".......testRimuoviCurriculum inizio.............");
		List<Curriculum> interoContenutoTabella = curriculumService.listAll();
		if (interoContenutoTabella.isEmpty() || interoContenutoTabella.get(0) == null)
			throw new Exception("Non ho nulla da rimuovere");
		
		// creo un'esperienza da inserire nella lista di esperienze del primo curriculum
		Esperienza nuovaEsperienza = new Esperienza("Cameriere", new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2020"), new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2021"), "team-work", curriculumService.findById(1l));
		if(esperienzaService.inserisciNuovo(nuovaEsperienza) != 1) {
			throw new RuntimeException("testInserimentoEsperienza FAILED ");
		}
		List<Esperienza> esperienzeNelCurriculum = new ArrayList<Esperienza>();
		esperienzeNelCurriculum.add(nuovaEsperienza);
		
		Long idDelPrimo = interoContenutoTabella.get(0).getId();
		
		Curriculum toBeRemoved = curriculumService.findById(idDelPrimo);
		toBeRemoved.setEsperienze(esperienzeNelCurriculum);
		if (curriculumService.rimuovi(toBeRemoved) != 1)
			throw new RuntimeException("testRimuoviCurriculum FAILED ");

		System.out.println(".......testRimuoviCurriculum PASSED.............");
	}
	
	private static void testInserimentoEsperienza(EsperienzaService esperienzaService, CurriculumService curriculumService) throws Exception {
		System.out.println(".......testInserimentoEsperienza inizio.............");
		Esperienza nuovaEsperienza = new Esperienza("Cameriere", new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2020"), new SimpleDateFormat("dd-MM-yyyy").parse("03-01-2021"), "team-work", curriculumService.findById(1l));
		if(esperienzaService.inserisciNuovo(nuovaEsperienza) != 1) {
			throw new RuntimeException("testInserimentoEsperienza FAILED ");
		}
		
		System.out.println(".......testInserimentoEsperienza PASSED.............");

	}
	
	private static void testRimuoviEsperienza(EsperienzaService esperienzaService) throws Exception {
		System.out.println(".......testRimuoviEsperienza inizio.............");
		List<Esperienza> interoContenutoTabella = esperienzaService.listAll();
		if (interoContenutoTabella.isEmpty() || interoContenutoTabella.get(0) == null)
			throw new Exception("Non ho nulla da rimuovere");

		Long idDelPrimo = interoContenutoTabella.get(0).getId();
		// ricarico per sicurezza con l'id individuato
		Esperienza toBeRemoved = esperienzaService.findById(idDelPrimo);
		if (esperienzaService.rimuovi(toBeRemoved) != 1)
			throw new RuntimeException("testRimuoviEsperienza FAILED ");

		System.out.println(".......testRimuoviEsperienza PASSED.............");
	}

}

package it.gestionecurricula.dao.esperienza;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.gestionecurricula.dao.AbstractMySQLDAO;
import it.gestionecurricula.model.Curriculum;
import it.gestionecurricula.model.Esperienza;

public class EsperienzaDAOImpl extends AbstractMySQLDAO implements EsperienzaDAO{
	
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Esperienza> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Esperienza> result = new ArrayList<Esperienza>();
		Esperienza espTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from gestionecurricula.esperienza")) {

			while (rs.next()) {
				espTemp = new Esperienza();
				espTemp.setDescrizione(rs.getString("descrizione"));
				espTemp.setDataInizio(rs.getDate("dataInizio"));
				espTemp.setDataFine(rs.getDate("dataFine"));
				espTemp.setConoscenzeAcquisite(rs.getString("conoscenzeAcquisite"));
				espTemp.setId(rs.getLong("id"));
				result.add(espTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Esperienza get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Esperienza result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from gestionecurricula.esperienza where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Esperienza();
					result.setDescrizione(rs.getString("descrizione"));
					result.setDataInizio(rs.getDate("dataInizio"));
					result.setDataFine(rs.getDate("dataFine"));
					result.setConoscenzeAcquisite(rs.getString("conoscenzeAcquisite"));
					result.setId(rs.getLong("id"));
				} else {
					result = null;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int update(Esperienza input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE gestionecurricula.esperienza SET descrizione=?, dataInizio=?, dataFine=?, conoscenzeAcquisite=? where id=?;")) {
			ps.setString(1, input.getDescrizione());
			ps.setDate(2, new java.sql.Date(input.getDataInizio().getTime()));
			ps.setDate(3, new java.sql.Date(input.getDataFine().getTime()));
			ps.setString(4, input.getConoscenzeAcquisite());
			ps.setLong(5, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Esperienza input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO gestionecurricula.esperienza (descrizione, dataInizio, dataFine, conoscenzeAcquisite, curriculum_id) VALUES (?, ?, ?, ?, ?);")) {
			ps.setString(1, input.getDescrizione());
			ps.setDate(2, new java.sql.Date(input.getDataInizio().getTime()));
			ps.setDate(3, new java.sql.Date(input.getDataFine().getTime()));
			ps.setString(4, input.getConoscenzeAcquisite());
			ps.setLong(5, input.getCurriculum().getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Esperienza input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM gestionecurricula.esperienza WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Esperienza> findByExample(Esperienza input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Esperienza> result = new ArrayList<Esperienza>();
		Esperienza espTemp = null;

		String query = "select * from gestionecurricula.esperienza where 1=1 ";
		if (input.getDescrizione() != null && !input.getDescrizione().isEmpty()) {
			query += " and descrizione like '" + input.getDescrizione() + "%' ";
		}
		
		if (input.getDataInizio() != null) {
			query += " and dataInizio like '" + new java.sql.Date(input.getDataInizio().getTime()) + "%' ";
		}

		if (input.getDataFine() != null) {
			query += " and dataFine like '" + new java.sql.Date(input.getDataFine().getTime()) + "%' ";
		}

		if (input.getConoscenzeAcquisite() != null && !input.getConoscenzeAcquisite().isEmpty()) {
			query += " and conoscenzeAcquisite like '" + input.getConoscenzeAcquisite() + "%' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				espTemp = new Esperienza();
				espTemp.setDescrizione(rs.getString("descrizione"));
				espTemp.setDataInizio(rs.getDate("dataInizio"));
				espTemp.setDataFine(rs.getDate("dataFine"));
				espTemp.setConoscenzeAcquisite(rs.getString("conoscenzeAcquisite"));
				espTemp.setId(rs.getLong("id"));
				result.add(espTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Esperienza> findAllByCurriculumOrderBy(Curriculum input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Esperienza> result = new ArrayList<Esperienza>();
		Esperienza espTemp = null;

		try (PreparedStatement ps = connection.prepareStatement("select * from gestionecurricula.esperienza e inner join gestionecurricula.curriculum c on c.id = e.curriculum_id where e.curriculum_id=? order by dataInizio;")) {
			
			ps.setLong(1, input.getId());

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					espTemp = new Esperienza();
					espTemp.setDescrizione(rs.getString("descrizione"));
					espTemp.setDataInizio(rs.getDate("dataInizio"));
					espTemp.setDataFine(rs.getDate("dataFine"));
					espTemp.setConoscenzeAcquisite(rs.getString("conoscenzeAcquisite"));
					espTemp.setId(rs.getLong("id"));
					result.add(espTemp);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}

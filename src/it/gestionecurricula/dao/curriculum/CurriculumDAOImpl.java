package it.gestionecurricula.dao.curriculum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.gestionecurricula.dao.AbstractMySQLDAO;
import it.gestionecurricula.model.Curriculum;

public class CurriculumDAOImpl extends AbstractMySQLDAO implements CurriculumDAO{
	
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Curriculum> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Curriculum> result = new ArrayList<Curriculum>();
		Curriculum currTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from gestionecurricula.curriculum")) {

			while (rs.next()) {
				currTemp = new Curriculum();
				currTemp.setNome(rs.getString("nome"));
				currTemp.setCognome(rs.getString("cognome"));
				currTemp.setDataDiNascita(rs.getDate("dataDiNascita"));
				currTemp.setTelefono(rs.getString("telefono"));
				currTemp.setEmail(rs.getString("email"));
				currTemp.setId(rs.getLong("id"));
				result.add(currTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public Curriculum get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Curriculum result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from gestionecurricula.curriculum where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Curriculum();
					result.setNome(rs.getString("nome"));
					result.setCognome(rs.getString("cognome"));
					result.setDataDiNascita(rs.getDate("dataDiNascita"));
					result.setTelefono(rs.getString("telefono"));
					result.setEmail(rs.getString("email"));
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
	public int update(Curriculum input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE gestionecurricula.curriculum SET nome=?, cognome=?, dataDiNascita=?, telefono=?, email=? where id=?;")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setDate(3, new java.sql.Date(input.getDataDiNascita().getTime()));
			ps.setString(4, input.getTelefono());
			ps.setString(5, input.getEmail());
			ps.setLong(6, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int insert(Curriculum input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO gestionecurricula.curriculum (nome, cognome, dataDiNascita, telefono, email) VALUES (?, ?, ?, ?, ?);")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setDate(3, new java.sql.Date(input.getDataDiNascita().getTime()));
			ps.setString(4, input.getTelefono());
			ps.setString(5, input.getEmail());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public int delete(Curriculum input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM gestionecurricula.curriculum WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public List<Curriculum> findByExample(Curriculum input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Curriculum> result = new ArrayList<Curriculum>();
		Curriculum currTemp = null;

		String query = "select * from user where 1=1 ";
		if (input.getCognome() != null && !input.getCognome().isEmpty()) {
			query += " and cognome like '" + input.getCognome() + "%' ";
		}
		if (input.getNome() != null && !input.getNome().isEmpty()) {
			query += " and nome like '" + input.getNome() + "%' ";
		}

		if (input.getTelefono() != null && !input.getTelefono().isEmpty()) {
			query += " and telefono like '" + input.getTelefono() + "%' ";
		}

		if (input.getEmail() != null && !input.getEmail().isEmpty()) {
			query += " and email like '" + input.getEmail() + "%' ";
		}

		if (input.getDataDiNascita() != null) {
			query += " and dataDiNascita='" + new java.sql.Date(input.getDataDiNascita().getTime()) + "' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				currTemp = new Curriculum();
				currTemp.setNome(rs.getString("nome"));
				currTemp.setCognome(rs.getString("cognome"));
				currTemp.setDataDiNascita(rs.getDate("dataDiNascita"));
				currTemp.setTelefono(rs.getString("telefono"));
				currTemp.setEmail(rs.getString("email"));
				currTemp.setId(rs.getLong("id"));
				result.add(currTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}

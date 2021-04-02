package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Studente;

public class StudenteDAO {
	
	public List<Studente> getIscrittiByCodiceCorso(String codice){
		
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.cds FROM iscrizione i, studente s "
				+ " WHERE i.matricola = s.matricola AND i.codins = ? ORDER BY s.matricola";
		
		List<Studente> studenti = new ArrayList<Studente>();
		
		try {
			Connection connect =  DBConnect.getConnection();
			PreparedStatement st = connect.prepareStatement(sql);
			st.setString(1, codice);
			
			ResultSet rs = st.executeQuery(); 
			
			while( rs.next() ) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), 
									rs.getString("nome"), rs.getString("cds"));
				studenti.add(s);
			}
			rs.close();
			st.close();
			connect.close();
			return studenti;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public Map<String, Integer> getDivisioneByCodiceCorso(String codice){
		
		String sql = "SELECT s.cds, COUNT(*) AS tot FROM studente s , iscrizione i "
				+ " WHERE s.matricola = i.matricola AND s.cds <> \"\" AND i.codins = ? GROUP by cds";
		Map<String, Integer> mappa = new HashMap<String, Integer>();
		
		try {
			Connection connect =  DBConnect.getConnection();
			PreparedStatement st = connect.prepareStatement(sql);
			st.setString(1, codice);
			ResultSet res = st.executeQuery(); 
			
			while(res.next() ) {
				String s = res.getString("cds");
				Integer n = res.getInt("tot");
				mappa.put(s, n);
			}
			
			res.close();
			st.close();
			connect.close();
			return mappa;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}

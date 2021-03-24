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
		
		List<Studente> studenti = new ArrayList<Studente>();
		
		try {
			Connection connect =  DBConnect.getConnection();
			String sql = "SELECT s.matricola, s.cognome, s.nome, s.cds FROM corso c, iscrizione i, studente s "
							+ " WHERE c.codins = i.codins AND i.matricola = s.matricola AND c.codins = ? ORDER BY s.matricola";
			PreparedStatement st = connect.prepareStatement(sql);
			st.setString(1, codice);
			
			ResultSet res = st.executeQuery(); 
			
			while( res.next() ) {
				Studente s = new Studente(res.getInt("matricola"), res.getString("cognome"), res.getString("nome"), res.getString("cds"));
				studenti.add(s);
			}
			
			st.close();
			connect.close();
			return studenti;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public Map<String, Integer> getDivisioneByCodiceCorso(String codice){
		
		Map<String, Integer> mappa = new HashMap<String, Integer>();
		
		try {
			Connection connect =  DBConnect.getConnection();
			String sql = "SELECT s.cds, COUNT(*) AS tot FROM studente s , iscrizione i "
						+ " WHERE s.matricola = i.matricola AND s.cds <> \"\" AND i.codins = ? GROUP by cds";
			PreparedStatement st = connect.prepareStatement(sql);
			st.setString(1, codice);
			
			ResultSet res = st.executeQuery(); 
			
			while( res.next() ) {
				String s = res.getString("cds");
				Integer n = res.getInt("tot");
				mappa.put(s, n);
			}
			
			st.close();
			connect.close();
			return mappa;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}

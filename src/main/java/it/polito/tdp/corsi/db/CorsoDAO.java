package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.db.DBConnect;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(Integer periodo) {
	
		List<Corso> corsi = new ArrayList<>();
		
		try {
			Connection connect =  DBConnect.getConnection();
			String sql = "SELECT * FROM corso WHERE pd = ?";
			PreparedStatement st = connect.prepareStatement(sql);
			st.setInt(1, periodo);
			
			ResultSet res = st.executeQuery(); 
			
			while( res.next() ) {
				Corso c = new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"), res.getInt("pd"));
				corsi.add(c);
			}
			
			st.close();
			connect.close();
			return corsi;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer periodo){
			
		Map<Corso, Integer> corsi = new HashMap<Corso, Integer>();
		
		try {
			Connection connect =  DBConnect.getConnection();
			String sql = "SELECT c.codins, c.nome, c.crediti, c.pd, COUNT(*) AS tot FROM corso c, iscrizione i	WHERE c.codins = i.codins AND c.pd = ? GROUP BY c.codins, c.nome, c.crediti, c.pd";
			PreparedStatement st = connect.prepareStatement(sql);
			st.setInt(1, periodo);
			
			ResultSet res = st.executeQuery(); 
			
			while( res.next() ) {
				Corso c = new Corso(res.getString("codins"), res.getInt("crediti"), res.getString("nome"), res.getInt("pd"));
				Integer n = res.getInt("tot");
				corsi.put(c, n);
			}
			
			st.close();
			connect.close();
			return corsi;
			
		}catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
}

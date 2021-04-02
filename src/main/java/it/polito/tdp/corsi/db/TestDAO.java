package it.polito.tdp.corsi.db;

public class TestDAO {
	
	public static void main(String[] args) {
		StudenteDAO dao = new StudenteDAO();
		System.out.print(dao.getIscrittiByCodiceCorso("01KSUPG"));
	}
}

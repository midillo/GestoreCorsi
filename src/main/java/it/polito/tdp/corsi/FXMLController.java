/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.corsi;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Model;
import it.polito.tdp.corsi.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPeriodo"
    private TextField txtPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="txtCorso"
    private TextField txtCorso; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorsiPerPeriodo"
    private Button btnCorsiPerPeriodo; // Value injected by FXMLLoader

    @FXML // fx:id="btnNumeroStudenti"
    private Button btnNumeroStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnStudenti"
    private Button btnStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnDivisioneStudenti"
    private Button btnDivisioneStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void corsiPerPeriodo(ActionEvent event) {
    	
    	String periodoStringa = txtPeriodo.getText();
    	Integer periodo;
    	try {
    		periodo = Integer.parseInt(periodoStringa);
    	}catch(NumberFormatException ne) {
    		txtRisultato.setText("Devi inserire un numero pari a 1 o 2 in base al periodo didattico che si vuole visualizzare");
    		return;
    	}catch(NullPointerException npe) {
    		txtRisultato.setText("Devi inserire un numero pari a 1 o 2 in base al periodo didattico che si vuole visualizzare");
    		return;
    	}
    	
    	if(periodo<1 || periodo>2) {
    		txtRisultato.setText("Devi inserire un numero pari a 1 o 2 in base al periodo didattico che si vuole visualizzare");
    		return;
    	}
    	
    	List<Corso> corsi = this.model.getCorsiByPeriodo(periodo);
    	StringBuilder sb = new StringBuilder();
    	
    	for(Corso c: corsi) {
    		sb.append(String.format("%-9s", c.getCodins()));
    		sb.append(String.format("%-5d", c.getCrediti()));
    		sb.append(String.format("%-50s", c.getNome()));
    		sb.append(String.format("%-5d\n", c.getPeriodo()));
    	}
    	txtRisultato.appendText(sb.toString());
    }

    @FXML
    void numeroStudenti(ActionEvent event) {
    	
    	String periodoStringa = txtPeriodo.getText();
    	Integer periodo;
    	try {
    		periodo = Integer.parseInt(periodoStringa);
    	}catch(NumberFormatException ne) {
    		txtRisultato.setText("Devi inserire un numero pari a 1 o 2 in base al periodo didattico che si vuole visualizzare");
    		return;
    	}catch(NullPointerException npe) {
    		txtRisultato.setText("Devi inserire un numero pari a 1 o 2 in base al periodo didattico che si vuole visualizzare");
    		return;
    	}
    	
    	if(periodo<1 || periodo>2) {
    		txtRisultato.setText("Devi inserire un numero pari a 1 o 2 in base al periodo didattico che si vuole visualizzare");
    		return;
    	}
    	
    	Map<Corso, Integer> corsi = this.model.getIscrittiByPeriodo(periodo);
    	for(Corso c: corsi.keySet()) {
    	txtRisultato.appendText(c.toString());
    	Integer n = corsi.get(c);
    	txtRisultato.appendText("\t"+n+"\n");
    	}
    }

    @FXML
    void stampaDivisione(ActionEvent event) {
    	txtRisultato.clear();
    	String corsoStringa = txtCorso.getText();

    	Map<String, Integer> studenti = this.model.getDivisioneByCodiceCorso(corsoStringa);
    		
    	for(String s: studenti.keySet()) {
    		if(studenti.get(s)!=null) {
   	    	txtRisultato.appendText(s.toString()+"\t"+studenti.get(s)+"\n");
    		}
    	}
      }

    @FXML
    void stampaStudenti(ActionEvent event) {
    	txtRisultato.clear();
    	String corsoStringa = txtCorso.getText();
    	
    		if(!model.esisteCorso(corsoStringa)) {
    			txtRisultato.appendText("Il corso non esiste!");
    			return;
    		}
    		List<Studente> studenti = this.model.getIscrittiByCodiceCorso(corsoStringa);
    		if(studenti.size()==0) {
    			txtRisultato.appendText("Il corso non ha iscritti!");
    			return;
    		}
    		StringBuilder sb = new StringBuilder();
    		for(Studente s: studenti) {
    			sb.append(String.format("%-11s ", s.getMatricola()));
        		sb.append(String.format("%-50s ", s.getCognome()));
        		sb.append(String.format("%-50s ", s.getNome()));
        		sb.append(String.format("%-50s\n", s.getCds()));
    		}
    		txtRisultato.appendText(sb.toString());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPeriodo != null : "fx:id=\"txtPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorso != null : "fx:id=\"txtCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCorsiPerPeriodo != null : "fx:id=\"btnCorsiPerPeriodo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnNumeroStudenti != null : "fx:id=\"btnNumeroStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStudenti != null : "fx:id=\"btnStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnDivisioneStudenti != null : "fx:id=\"btnDivisioneStudenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	txtRisultato.setStyle("-fx-font-family: monospace");
    }
    
    
}
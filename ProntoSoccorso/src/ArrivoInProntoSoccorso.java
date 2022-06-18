
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;

/**
 * Data model (classe bean) per il riempimento della tabella
 */
public class ArrivoInProntoSoccorso {
    
    private final SimpleStringProperty nominativo;
    private final SimpleStringProperty codice;
    private final SimpleIntegerProperty saturazione;
    private final SimpleIntegerProperty battiti;
    private final SimpleStringProperty pressione;
    private final SimpleStringProperty note;
    private final SimpleObjectProperty<DatePicker> dataArrivo;
    private final SimpleStringProperty oraArrivo;
    private final SimpleObjectProperty<TipiDiTrasporto> trasporto;
    private final SimpleStringProperty triagista;
 
    public ArrivoInProntoSoccorso(String nominativo, String codice, int sp02, int battiti, String pressione, String note, DatePicker data, String ora, TipiDiTrasporto trasporto, String triagista) {
        this.nominativo = new SimpleStringProperty(nominativo);
        this.codice = new SimpleStringProperty(codice);
        this.saturazione = new SimpleIntegerProperty(sp02);
        this.battiti = new SimpleIntegerProperty(battiti);
        this.pressione = new SimpleStringProperty(pressione);
        this.note = new SimpleStringProperty(note);
        this.dataArrivo = new SimpleObjectProperty(data);
        this.oraArrivo = new SimpleStringProperty(ora);
        this.trasporto = new SimpleObjectProperty(trasporto);
        this.triagista = new SimpleStringProperty(triagista);
    }

    public String getNominativo() {return this.nominativo.get();}
    public String getCodice() {return this.codice.get();}
    public int getSaturazione() {return this.saturazione.get();}
    public int getBattiti() {return this.battiti.get();}
    public String getPressione() {return this.pressione.get();}
    public String getNote() {return this.note.get();}
    public DatePicker getDataArrivo() {return this.dataArrivo.get();}
    public String getOraArrivo() {return this.oraArrivo.get();}
    public TipiDiTrasporto getTrasporto() {return this.trasporto.get();}
    public String getTriagista() {return this.triagista.get();}
    
    public void setNominativo(String nominativo) {this.nominativo.set(nominativo);}
    public void setCodice(String codiceGravita) {this.codice.set(codiceGravita);}
    public void setSaturazione(int spO2) {this.saturazione.set(spO2);}
    public void setBattiti(int battiti) {this.battiti.set(battiti);}
    public void setPressione(String pressione) {this.pressione.set(pressione);}
    public void setNote(String note) {this.note.set(note);}
    public void setDataArrivo(DatePicker data) {this.dataArrivo.set(data);}
    public void setOraArrivo(String orario) {this.oraArrivo.set(orario);}
    public void setTrasporto(TipiDiTrasporto tipologia) {this.trasporto.set(tipologia);}
    public void setTriagista(String triagista) {this.triagista.set(triagista);}
}

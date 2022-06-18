
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

/**
 * Classe che si occupa della gestione della cache (su file binario).
 * In cache vengono salvati tutti i dati presenti nell'ultima riga della tabella
 * poichè quest'ultimi non sono memorizzati sul database.
 */
public class GestoreCacheUltimoArrivo {
    
    private static final String PATH_FILE_CACHE = "./cache.bin";
    
    private final ProntoSoccorso interfacciaGrafica;
    
    public GestoreCacheUltimoArrivo(ProntoSoccorso interfacciaUtente) {
        this.interfacciaGrafica = interfacciaUtente;
    }
    
    /**
     * Salva sulla cache i dati presenti nell'ultima riga della tabella (non salvati sul database)
     */
    public void salvaInCache() {
        DatiUltimoArrivo dati = new DatiUltimoArrivo();
        
        ObservableList<ArrivoInProntoSoccorso> righe = interfacciaGrafica.tabella.getItems();
        ArrivoInProntoSoccorso ultimaRiga = righe.get(righe.size() - 1);
        
        dati.nominativo = ultimaRiga.getNominativo();
        dati.codice = ultimaRiga.getCodice();
        dati.saturazione = ultimaRiga.getSaturazione();
        dati.battiti = ultimaRiga.getBattiti();
        dati.pressione = ultimaRiga.getPressione();
        dati.note = ultimaRiga.getNote();
        dati.dataArrivo = ultimaRiga.getDataArrivo().getValue().toString();
        dati.oraArrivo = ultimaRiga.getOraArrivo();
        dati.trasporto = ultimaRiga.getTrasporto() == null ? null : ultimaRiga.getTrasporto().toString();
        dati.triagista = ultimaRiga.getTriagista();
        
        try
        (
            FileOutputStream fos = new FileOutputStream(PATH_FILE_CACHE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(dati);
        } catch(IOException e) {
            System.err.println("Errore durante il salvataggio in cache: " + e.getMessage());
        }
    }
    
    /**
     * Carica dalla cache e riporta nella tabella i dati che, alla chiusura dell'applicazione,
     * erano presenti nell'ultima riga della tabella (cioè non salvati sul database)
     */
    public void caricaDaCache() {
        if(!new File(PATH_FILE_CACHE).exists())
            return;
        
        try
        (
            FileInputStream fis = new FileInputStream(PATH_FILE_CACHE);
            ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            DatiUltimoArrivo datiInCache = (DatiUltimoArrivo) ois.readObject();
            
            // Inserisce i dati in cache nell'ultima riga della tabella dedicata all'inserimento dei dati
            ObservableList<ArrivoInProntoSoccorso> items = interfacciaGrafica.tabella.getItems();
            ArrivoInProntoSoccorso ultimaRiga = items.get(items.size() - 1);

            ultimaRiga.setNominativo(datiInCache.nominativo);
            ultimaRiga.setCodice(datiInCache.codice);
            ultimaRiga.setSaturazione(datiInCache.saturazione);
            ultimaRiga.setBattiti(datiInCache.battiti);
            ultimaRiga.setPressione(datiInCache.pressione);
            ultimaRiga.setNote(datiInCache.note);
            ultimaRiga.setDataArrivo(new DatePicker(LocalDate.parse(datiInCache.dataArrivo)));
            ultimaRiga.setOraArrivo(datiInCache.oraArrivo);
            ultimaRiga.setTrasporto(datiInCache.trasporto == null ? null : TipiDiTrasporto.valueOf(datiInCache.trasporto.toUpperCase()));
            ultimaRiga.setTriagista(datiInCache.triagista);
            
        } catch(IOException | ClassNotFoundException e) {
            System.err.println("Errore durante il caricamento della cache: " + e.getMessage());
        }
    }
}

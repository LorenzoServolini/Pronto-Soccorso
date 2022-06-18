
import java.io.Serializable;

/**
 * Classe contenente i dati presenti nell'ultima riga della tabella
 * (quindi non salvati sul database) che vengono memorizzati in cache
 */
public class DatiUltimoArrivo implements Serializable {
    
    public String nominativo;
    public String codice;
    public int saturazione;
    public int battiti;
    public String pressione;
    public String note;
    public String dataArrivo;
    public String oraArrivo;
    public String trasporto;
    public String triagista;
    
}


import com.thoughtworks.xstream.XStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe che gestisce il file di configurazione in XML
 */
public class ParametriDiConfigurazione {
    
    private static final String PATH_CONFIG_SCHEMA = "./ConfigSchema.xsd";
    private static final String PATH_FILE_CONFIG = "./Configurazione.xml";
    
    private static ParametriDiConfigurazione istanza;
    
    /**
     * Massimo numero di pazienti (righe) visibili nella tabella
     */
    public int numeroRigheTabella;
    
    /**
     * Numero (di default) di giorni antecedenti alla data odierna
     * di cui mostrare gli arrivi giornalieri nel grafico a barre
     */
    public int numeroGiorniPeriodo;
    
    /** Settings del font */
    public String font;
    public int dimensioneNomeApplicazione;
    public int dimensioneTitoloTabella;
    public int dimensioneTitoloGrafico;
    
    /** Settings per il database */
    public String IPDatabase;
    public int portaDatabase;
    public String usernameDatabase;
    public String passwordDatabase;
    
    /** Settings per il server di log */
    public String IPServerLog;
    public int portaServerLog;
    
    /** Setting del client */
    public String IPClient;

    /**
     * Legge tutti i parametri di configurazione dal file.
     */
    private ParametriDiConfigurazione() {
        try {
            String parametriInXML = new String(Files.readAllBytes(Paths.get(PATH_FILE_CONFIG)));
            
            if(!ValidazioneXML.valida(parametriInXML, PATH_CONFIG_SCHEMA))
                return;
      
            ParametriDiConfigurazione config = (ParametriDiConfigurazione) new XStream().fromXML(parametriInXML);
            
            this.numeroRigheTabella = config.numeroRigheTabella;
            this.numeroGiorniPeriodo = config.numeroGiorniPeriodo;
            this.font = config.font;
            this.dimensioneNomeApplicazione = config.dimensioneNomeApplicazione;
            this.dimensioneTitoloTabella = config.dimensioneTitoloTabella;
            this.dimensioneTitoloGrafico = config.dimensioneTitoloGrafico;
            this.IPDatabase = config.IPDatabase;
            this.portaDatabase = config.portaDatabase;
            this.usernameDatabase = config.usernameDatabase;
            this.passwordDatabase = config.passwordDatabase;
            this.IPServerLog = config.IPServerLog;
            this.portaServerLog = config.portaServerLog;
            this.IPClient = config.IPClient;
        }
        catch(Exception e) {    
            System.err.println("Errore durante il caricamento dei parametri di configurazione: " + e.getMessage());
        }
    }
    
    /**
     * @return {@link ParametriDiConfigurazione istanza} della classe
     */
    public static ParametriDiConfigurazione ottieniConfig() {
        if(istanza == null)
            istanza = new ParametriDiConfigurazione();
        
        return istanza;
    }
}

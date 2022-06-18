
import com.thoughtworks.xstream.XStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe che rappresenta un evento di navigazione da notificare al server di log
 */
public class EventoDiNavigazioneGUI {
    
    public String nomeApplicazione;
    public String IPClient;
    public String dataCorrente;
    public String oraCorrente;
    public String etichettaEvento;

    public EventoDiNavigazioneGUI(String etichettaEvento) {
        LocalDateTime dataOraCorrente = LocalDateTime.now();
        
        this.nomeApplicazione = "ProntoSoccorso";
        this.IPClient = ParametriDiConfigurazione.ottieniConfig().IPClient;
        this.dataCorrente = dataOraCorrente.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.oraCorrente = dataOraCorrente.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.etichettaEvento = etichettaEvento;
    }
    
    /**
     * Serializza l'evento in XML e lo invia al server di log
     */
    public void inviaEvento() {
        ParametriDiConfigurazione config = ParametriDiConfigurazione.ottieniConfig();
        
        String eventoInXML = new XStream().toXML(this);
        
        try (
                Socket socket = new Socket(config.IPServerLog, config.portaServerLog);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
            ) {
            dos.writeUTF(eventoInXML);
        } catch (Exception e) { System.err.println("Errore durante l'invio di un evento di log: " + e.getMessage()); }
    }
}

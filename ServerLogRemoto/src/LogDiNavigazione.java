
import com.thoughtworks.xstream.XStream;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Classe che gestisce il server di log in ascolto
 */
public class LogDiNavigazione {

    private static final String PATH_LOG_SCHEMA = "./EventSchema.xsd";
    private static final String PATH_FILE_LOG = "./logs.xml";

    public static void main(String[] args) {        
        try (ServerSocket servs = new ServerSocket(8080, 100))
        {
            System.out.println("Server in esecuzione...");
            
            while(true) {
                try
                (
                    Socket so = servs.accept();
                    DataInputStream din = new DataInputStream(so.getInputStream());
                )
                {
                    String stringaInXML = din.readUTF();
                    if(!ValidazioneXML.valida(stringaInXML, PATH_LOG_SCHEMA))
                        continue;
                    
                    EventoDiNavigazioneGUI evento = (EventoDiNavigazioneGUI) new XStream().fromXML(stringaInXML);
                    
                    System.out.println("Ricevuto il log relativo all'evento \"" + evento.etichettaEvento + "\" dall'applicazione \"" + evento.nomeApplicazione + "\" (IP: " + evento.IPClient + ") in data " + evento.dataCorrente + " alle ore " + evento.oraCorrente);
                    
                    // Aggiungi in fondo al file di log l'evento ricevuto
                    Files.write(Paths.get(PATH_FILE_LOG), (stringaInXML + "\n\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                    
                } catch (Exception e) { System.err.println("Errore durante le ricezione di un evento di log: " + e.getMessage()); }
            }
        } catch (Exception e) { System.err.println("Errore durante le ricezione di un evento di log: " + e.getMessage()); }
    }
}

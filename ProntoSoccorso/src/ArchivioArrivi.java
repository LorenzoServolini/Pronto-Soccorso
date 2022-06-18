
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.DatePicker;

/**
 * Classe che si occupa dell'interazione col database MySQL dove vengono
 * memorizzati gli arrivi (dopo che è stato premuto il pulsante "INSERISCI")
 */
public class ArchivioArrivi {
    
    /**
     * Ottiene dal database, ordinati per data di inserimento, tutti gli arrivi in pronto soccorso.
     * 
     * @return {@link ArrayList lista} contenente gli {@link ArrivoInProntoSoccorso arrivi}
     * salvati sul database, ordinati per data di inserimento
     */
    public static ArrayList<ArrivoInProntoSoccorso> caricaArrivi() {
        ArrayList<ArrivoInProntoSoccorso> elencoArrivi = new ArrayList<>();
        
        ParametriDiConfigurazione config = ParametriDiConfigurazione.ottieniConfig();
        try
        (
            Connection connection = DriverManager.getConnection
            (
                    "jdbc:mysql://" + config.IPDatabase + ":" + config.portaDatabase + "/archivioarrivi", config.usernameDatabase, config.passwordDatabase
            );

            Statement statement = connection.createStatement();
        )
        {
            ResultSet result = statement.executeQuery
            (
                    "SELECT *"
                    + " FROM arrivi"
                    + " ORDER BY id;"
            );
            

            while (result.next())
            {
                elencoArrivi.add(new ArrivoInProntoSoccorso
                (
                    result.getString("nominativo"),
                    result.getString("codice"),
                    result.getInt("saturazione"),
                    result.getInt("battiti"),
                    result.getString("pressione"),
                    result.getString("note"),
                    new DatePicker(LocalDate.parse(result.getString("data"))),
                    result.getString("ora"),
                    result.getString("trasporto") == null ? null : TipiDiTrasporto.valueOf(result.getString("trasporto").toUpperCase()),
                    result.getString("triagista")
                ));
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return elencoArrivi;
    }
    
    /**
     * Calcola gli arrivi giornalieri, per ogni codice gravità, in un periodo selezionato
     * 
     * @param dataInizio Data di inizio del periodo
     * @param dataFine Data di fine del periodo
     * 
     * @return {@link HashMap} così formata:
     * - Ad ogni colore è associato un array di numeri che corrispondono al numero di
     * accessi (di quel colore) che si sono verificati nei vari giorni all'interno
     * del periodo selezioanto.
     * - Nell'array, all'index = 0, sarà presente il numero di accessi del giorno di
     * inizio del periodo (= "da"); il numero nell'array all'index = 1 corrisponderà
     * al numero di accessi che ci sono stati nel giorno successivo al giorno di
     * inizio del periodo (= da + 1); così via fino ad arrivare al giorno di fine periodo.
     * 
     * ES. (periodo di 5 giorni, colore di interesse: rosso)
     * <code>
     * int[] arrivi = map.get(ColoreGravita.ROSSO);
     * arrivi[0] = arrivi nel giorno di inizio del periodo ("da")
     * arrivi[1] = arrivi nel giorno successivo a quello di inizio del periodo ("da" + 1)
     * ...
     * arrivi[4] = arrivi nel giorno di fine del periodo ("da" + 4) = a
     * </code>
    */
    public static HashMap<ColoreGravita, Integer[]> calcolaArriviGiornalieri(LocalDate dataInizio, LocalDate dataFine) {
        int giorniNelPeriodo = (int) dataInizio.until(dataFine, ChronoUnit.DAYS) + 1;
        HashMap<ColoreGravita, Integer[]> arriviGiornalieri = new HashMap<>();
        
        // Se la data di inizio supera la data di fine restituisce una mappa vuota
        if(giorniNelPeriodo < 0)
            return arriviGiornalieri;
        
        // Di default gli arrivi sono 0
        arriviGiornalieri.put(ColoreGravita.BIANCO, arrayDiInteger(0, giorniNelPeriodo));
        arriviGiornalieri.put(ColoreGravita.VERDE, arrayDiInteger(0, giorniNelPeriodo));
        arriviGiornalieri.put(ColoreGravita.GIALLO, arrayDiInteger(0, giorniNelPeriodo));
        arriviGiornalieri.put(ColoreGravita.ROSSO, arrayDiInteger(0, giorniNelPeriodo));
        
        ParametriDiConfigurazione config = ParametriDiConfigurazione.ottieniConfig();
        try
        (
            Connection connection = DriverManager.getConnection
            (
                    "jdbc:mysql://" + config.IPDatabase + ":" + config.portaDatabase + "/archivioarrivi", config.usernameDatabase, config.passwordDatabase
            );

            PreparedStatement statement = connection.prepareStatement
            (
                    "SELECT codice, data "
                    + "FROM arrivi "
                    + "WHERE (data BETWEEN ? AND ?);"
            );
        )
        {
            statement.setString(1, dataInizio.toString());
            statement.setString(2, dataFine.toString());
            
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String codice = result.getString("codice");
                String dataArrivo = result.getString("data");
                
                // Converte il codice in un colore (rosso, giallo, ...)
                ColoreGravita colore = ColoreGravita.convertiCodiceInGravita(codice);
                if(colore == null)
                    continue; // Il codice non corrisponde a nessun colore, dunque l'arrivo non va conteggiato
                
                // Differenza tra la data di inizio del periodo e la data nel record corrente (data di arrivo del paziente)
                int numeroGiornoNelPeriodo = (int) dataInizio.until(LocalDate.parse(dataArrivo), ChronoUnit.DAYS);
               
                Integer[] totaleArrivi = arriviGiornalieri.get(colore); // Numero di arrivi registrati fino ad ora
                totaleArrivi[numeroGiornoNelPeriodo]++; // Incrementa gli arrivi della data in cui è arrivato il paziente

                // Aggiorna la HashMap
                arriviGiornalieri.replace(colore, totaleArrivi);
            }
        } catch (SQLException e) {System.err.println(e.getMessage());}
        
        return arriviGiornalieri;
    }
    
    /**
     * Salva un nuovo arrivo sul database
     * 
     * @param record Record da inserire nel database
     */
    public static void inserisciArrivo(ArrivoInProntoSoccorso record) {
        
        ParametriDiConfigurazione config = ParametriDiConfigurazione.ottieniConfig();
        try
        (
            Connection connection = DriverManager.getConnection
            (
                    "jdbc:mysql://" + config.IPDatabase + ":" + config.portaDatabase + "/archivioarrivi", config.usernameDatabase, config.passwordDatabase
            );

            PreparedStatement statement = connection.prepareStatement
            (
                    "INSERT INTO arrivi (nominativo, codice, saturazione, battiti, pressione, note, data, ora, trasporto, triagista) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);"
            );
        )
        {
            statement.setString(1, record.getNominativo());
            statement.setString(2, record.getCodice());
            statement.setInt(3, record.getSaturazione());
            statement.setInt(4, record.getBattiti());
            statement.setString(5, record.getPressione());
            statement.setString(6, record.getNote());
            statement.setString(7, record.getDataArrivo().getValue().toString());
            statement.setString(8, record.getOraArrivo());
            statement.setString(9, record.getTrasporto() == null ? null : record.getTrasporto().toString());
            statement.setString(10, record.getTriagista());
            
            statement.executeUpdate();
        } catch (SQLException e) {System.err.println(e.getMessage());}
    }
    
    /**
     * Crea un array di interi della dimensione specificata e con tutti gli
     * elementi pari al valore di default specificato
     * 
     * @param valoreDiDefault Valore di default da assegnare ad ogni elemento dell'array
     * @param dimensione Dimensione dell'array
     * 
     * @return array di interi impostati al valore di default
     */
    private static Integer[] arrayDiInteger(int valoreDiDefault, int dimensione) {
        Integer[] array = new Integer[dimensione];
        
        for(int i = 0; i < dimensione; i++)
            array[i] = valoreDiDefault;
        
        return array;
    }
}

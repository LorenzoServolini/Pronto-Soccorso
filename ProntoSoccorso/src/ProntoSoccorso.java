
import java.time.LocalDate;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Controller applicativo
 */
public class ProntoSoccorso extends Application {
    
    // Elementi interfaccia grafica
    public StatisticheProntoSoccorso grafico;
    public TabellaArrivi tabella;
    private Button inserisciPaziente;
    private Button pulisciUltimaRiga;
    private Label nomeApplicazione;
    private Label titoloTabella;
    private Label titoloGrafico;
    private Label da;
    private Label a;
    public DatePicker dataInizioPeriodo;
    public DatePicker dataFinePeriodo;
    
    @Override
    public void start(Stage stage) {        
        grafico = new StatisticheProntoSoccorso(this);
        tabella = new TabellaArrivi();
        inserisciPaziente = new Button("INSERISCI");
        pulisciUltimaRiga = new Button("CANCELLA");
        nomeApplicazione = new Label("Pronto Soccorso");
        titoloTabella = new Label("Elenco pazienti");
        titoloGrafico = new Label("Arrivi giornalieri");
        da = new Label("Da");
        a = new Label("A");
        dataInizioPeriodo = new DatePicker();
        dataFinePeriodo = new DatePicker();
        
        inserisciPeriodoDefault();
        
        EventoDiNavigazioneGUI evento = new EventoDiNavigazioneGUI("AVVIO");
        evento.inviaEvento();
        
        tabella.popolaTabella();
        grafico.aggiornaGrafico();
        
        GestoreCacheUltimoArrivo gestoreCache = new GestoreCacheUltimoArrivo(this);
        gestoreCache.caricaDaCache();
        
        Scene scene = creaGUI(stage);
        
        associaEventHandler(stage, scene);
    }
    
    /**
     * Inserisce nei date picker del grafico a barre la data odierna e la data
     * corrispondente a G giorni antecedenti, dove G è un parametro specificato
     * nel file di configurazione XML.
     */
    private void inserisciPeriodoDefault() {        
        dataInizioPeriodo.setValue(LocalDate.now().minusDays(ParametriDiConfigurazione.ottieniConfig().numeroGiorniPeriodo - 1));
        dataFinePeriodo.setValue(LocalDate.now());
    }
    
    /**
     * Definisci i gestori per gli eventi che si verificano nella finestra
     * 
     * @param stage Finestra dell'applicazione
     * @param scene Contenuto della finestra
     */
    private void associaEventHandler(Stage stage, Scene scene) {        
        tabella.registraHandlerModifica();
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            GestoreCacheUltimoArrivo gestore = new GestoreCacheUltimoArrivo(this);
            gestore.salvaInCache();
            
            EventoDiNavigazioneGUI evento = new EventoDiNavigazioneGUI("TERMINE");
            evento.inviaEvento();
        });
        
        inserisciPaziente.setOnAction((ActionEvent event) -> {
            tabella.inserisciNuovoArrivo();
            
            grafico.aggiornaGrafico();
            
            EventoDiNavigazioneGUI evento = new EventoDiNavigazioneGUI("INSERISCI");
            evento.inviaEvento();
        });
        
        pulisciUltimaRiga.setOnAction((ActionEvent event) -> {
            tabella.pulisciUltimaRiga();
            
            EventoDiNavigazioneGUI evento = new EventoDiNavigazioneGUI("CANCELLA");
            evento.inviaEvento();
        });
        
        dataInizioPeriodo.setOnAction((ActionEvent event) -> {
            grafico.aggiornaGrafico();
            
            EventoDiNavigazioneGUI evento = new EventoDiNavigazioneGUI("DA");
            evento.inviaEvento();
        });
        
        dataFinePeriodo.setOnAction((ActionEvent event) -> {
            grafico.aggiornaGrafico();
            
            EventoDiNavigazioneGUI evento = new EventoDiNavigazioneGUI("A");
            evento.inviaEvento();
        });
    }
    
    /**
     * Crea la grafica dell'interfaccia utente utilizzando i parametri di configurazione
     * 
     * @param stage Finestra dell'applicazione
     * 
     * @return {@link Scene}
     */
    private Scene creaGUI(Stage stage) {
        ParametriDiConfigurazione config = ParametriDiConfigurazione.ottieniConfig();
        
        nomeApplicazione.setFont(new Font(config.font, config.dimensioneNomeApplicazione));
        nomeApplicazione.setStyle("-fx-text-fill: dodgerblue; -fx-font-weight: bold;");
        
        titoloTabella.setFont(new Font(config.font, config.dimensioneTitoloTabella));
        titoloTabella.setStyle("-fx-font-weight: bold;");
        
        titoloGrafico.setFont(new Font(config.font, config.dimensioneTitoloGrafico));
        titoloGrafico.setStyle("-fx-font-weight: bold;");
        
        HBox areaPulsantiTabella = new HBox();
        areaPulsantiTabella.setAlignment(Pos.CENTER);
        areaPulsantiTabella.setSpacing(50);
        areaPulsantiTabella.getChildren().addAll(inserisciPaziente, pulisciUltimaRiga);
        
        Separator separatore = new Separator();
        separatore.setMaxWidth(1100);
        separatore.setPadding(new Insets(15, 0, 0, 5));
        
        HBox areaDatePickerDaA = new HBox();
        areaDatePickerDaA.setAlignment(Pos.CENTER);
        areaDatePickerDaA.setSpacing(8);
        a.setPadding(new Insets(0, 0, 0, 42));
        areaDatePickerDaA.getChildren().addAll(da, dataInizioPeriodo, a, dataFinePeriodo);
        
        VBox interfacciaUtente = new VBox();
        interfacciaUtente.setPrefWidth(1410);
        interfacciaUtente.setSpacing(10);
        interfacciaUtente.setPadding(new Insets(0, 20, 15, 20));
        interfacciaUtente.setAlignment(Pos.CENTER);
        interfacciaUtente.getChildren().addAll(nomeApplicazione, titoloTabella, tabella, areaPulsantiTabella, separatore, titoloGrafico, grafico, areaDatePickerDaA);
        
        Scene scene = new Scene(interfacciaUtente);
        
        stage.setTitle("ProntoSoccorso");
        stage.setScene(scene);
        stage.show();
        
        return scene;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
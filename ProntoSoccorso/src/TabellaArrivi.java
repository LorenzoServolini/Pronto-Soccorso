import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

/**
 * Classe che gestisce la tabella nell'applicazione
 */
public class TabellaArrivi extends TableView<ArrivoInProntoSoccorso> {
    
    private final ObservableList<ArrivoInProntoSoccorso> listaArrivi = FXCollections.observableArrayList();
    
    public TabellaArrivi() {   
        creaColonne();
        
        // Colora la riga in base al codice gravità
        this.setRowFactory(tableView -> new TableRow<ArrivoInProntoSoccorso>() {
            
            @Override
            protected void updateItem(ArrivoInProntoSoccorso item, boolean empty) {
                super.updateItem(item, empty);
                
                // Controllo richiesto dalla documentazione
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    return;
                }
                
                // L'ultima riga deve essere sempre colorata di bianco (in modo da evidenziare che non è memorizzata sul database)
                if(this.getIndex() == listaArrivi.size() - 1) {
                    this.setStyle("-fx-background-color: " + ColoreGravita.convertiGravitaInColore(ColoreGravita.BIANCO) + ";");
                    return;
                }
                
                // Colora la riga in base al codice gravità
                this.setStyle("-fx-background-color: " + ColoreGravita.convertiCodiceInColore(item.getCodice()) + ";");
            }
        });
        
        this.setFixedCellSize(50);
        this.setPlaceholder(new Label("Nessun arrivo registrato"));
        this.setEditable(true);
        this.setPrefHeight(this.getFixedCellSize() * (ParametriDiConfigurazione.ottieniConfig().numeroRigheTabella + 1) + 44);
        this.setItems(this.listaArrivi); // Imposta gli elementi della observable list come contenuto della tabella
        
        for(TableColumn colonna : this.getColumns()) {
            colonna.setSortable(false);
            colonna.setStyle("-fx-alignment: CENTER; -fx-border-color: black;");
        }
        
        this.setStyle("-fx-selection-bar: transparent; -fx-selection-bar-non-focused: transparent; -fx-text-background-color: black; -fx-border-color: black; -fx-focus-color: black;");
    }
    
    /**
     * Registra i listener per gli eventi lanciati al momento della modifica delle celle nella tabella
     */
    public void registraHandlerModifica() {
        TableColumn colNominativo = this.getColumns().get(0);
        TableColumn colCodice = this.getColumns().get(1);
        TableColumn colSaturazione = this.getColumns().get(2);
        TableColumn colBattiti = this.getColumns().get(3);
        TableColumn colPressione = this.getColumns().get(4);
        TableColumn colNote = this.getColumns().get(5);
        TableColumn colDataArrivo = this.getColumns().get(6);
        TableColumn colOraArrivo = this.getColumns().get(7);
        TableColumn colTrasporto = this.getColumns().get(8);
        TableColumn colTriagista = this.getColumns().get(9);
        
        
        colNominativo.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String> event) {                
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setNominativo(event.getNewValue());
            }
        });
        colCodice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setCodice(event.getNewValue());
            }
        });
        colSaturazione.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, Integer>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, Integer> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setSaturazione(event.getNewValue());
            }
        });
        colBattiti.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, Integer>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, Integer> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setBattiti(event.getNewValue());
            }
        });
        colPressione.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setPressione(event.getNewValue());
            }
        });
        colNote.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setNote(event.getNewValue());
            }
        });
        colDataArrivo.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, DatePicker>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, DatePicker> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setDataArrivo(event.getNewValue());
            }
        });
        colOraArrivo.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setOraArrivo(event.getNewValue());
            }
        });
        colTrasporto.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, TipiDiTrasporto>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, TipiDiTrasporto> event) {
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setTrasporto(event.getNewValue());
            }
        });
        colTriagista.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String>>() {
            
            @Override
            public void handle(TableColumn.CellEditEvent<ArrivoInProntoSoccorso, String> event) {                
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setTriagista(event.getNewValue());
            }
        });
    }
    
    /**
     * Popola la tabella con tutti gli arrivi registrati sul database
     */
    public void popolaTabella() {        
        // Aggiunge gli arrivi alla observable list
        this.listaArrivi.addAll(ArchivioArrivi.caricaArrivi());
        
        // Riga in fondo alla tabella per l'inserimento dei dati
        this.listaArrivi.add(nuovoArrivoVuoto());
        
        // Imposta la scrollbar in modo che sia visibile l'ultima riga della tabella
        this.scrollTo(this.listaArrivi.size() - 1);
    }
    
    /**
     * Salva sul database l'arrivo inserito nell'ultima riga della tabella
     */
    public void inserisciNuovoArrivo() {
        ArchivioArrivi.inserisciArrivo(this.listaArrivi.get(listaArrivi.size() - 1));
        
        this.listaArrivi.add(nuovoArrivoVuoto()); // Nuova riga in fondo alla tabella per l'inserimento dei dati
    }
    
    /**
     * Resetta ai valori di default l'ultima riga nella tabella
     */
    public void pulisciUltimaRiga() {
        this.listaArrivi.set(listaArrivi.size() - 1, nuovoArrivoVuoto());
    }
    
    /**
     * @return {@link ArrivoInProntoSoccorso arrivo} vuoto, con solo il date picker impostato alla data odierna
     */
    private ArrivoInProntoSoccorso nuovoArrivoVuoto() {
        return new ArrivoInProntoSoccorso(null, null, -1, -1, null, null, new DatePicker(LocalDate.now()), null, null, null);
    }

    /**
     * Istanzia tutte le colonne e le aggiunge alla tabella
     */
    private void creaColonne() {
        TableColumn colNominativo = new TableColumn("Nominativo");
        colNominativo.setPrefWidth(120);
        colNominativo.setCellValueFactory(new PropertyValueFactory<>("nominativo"));
        colNominativo.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn colCodice = new TableColumn("Codice");
        colCodice.setPrefWidth(60);
        colCodice.setCellValueFactory(new PropertyValueFactory<>("codice"));
        colCodice.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn colSaturazione = new TableColumn("SpO2");
        colSaturazione.setPrefWidth(60);
        colSaturazione.setCellValueFactory(new PropertyValueFactory<>("saturazione"));
        colSaturazione.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        TableColumn colBattiti = new TableColumn("Battiti");
        colBattiti.setPrefWidth(60);
        colBattiti.setCellValueFactory(new PropertyValueFactory<>("battiti"));
        colBattiti.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        
        TableColumn colPressione = new TableColumn("Pressione");
        colPressione.setPrefWidth(80);
        colPressione.setCellValueFactory(new PropertyValueFactory<>("pressione"));
        colPressione.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn colNote = new TableColumn("Note");
        colNote.setPrefWidth(500);
        colNote.setCellValueFactory(new PropertyValueFactory<>("note"));
        colNote.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn colDataArrivo = new TableColumn("Data arrivo");
        colDataArrivo.setPrefWidth(150);
        colDataArrivo.setCellValueFactory(new PropertyValueFactory<>("dataArrivo"));
        
        TableColumn colOraArrivo = new TableColumn("Ora arrivo");
        colOraArrivo.setPrefWidth(80);
        colOraArrivo.setCellValueFactory(new PropertyValueFactory<>("oraArrivo"));
        colOraArrivo.setCellFactory(TextFieldTableCell.forTableColumn());
        
        TableColumn colTrasporto = new TableColumn("Trasporto");
        colTrasporto.setPrefWidth(100);
        colTrasporto.setCellValueFactory(new PropertyValueFactory<>("trasporto"));
        colTrasporto.setCellFactory(ComboBoxTableCell.forTableColumn(TipiDiTrasporto.values()));
        
        TableColumn colTriagista = new TableColumn("Triagista");
        colTriagista.setPrefWidth(130);
        colTriagista.setCellValueFactory(new PropertyValueFactory<>("triagista"));
        colTriagista.setCellFactory(TextFieldTableCell.forTableColumn());
        
        
        this.getColumns().addAll(colNominativo, colCodice, colSaturazione, colBattiti, colPressione, colNote, colDataArrivo, colOraArrivo, colTrasporto, colTriagista);
    }
}

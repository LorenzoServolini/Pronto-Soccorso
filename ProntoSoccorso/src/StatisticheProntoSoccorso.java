
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * Classe che gestisce il grafico a barre nell'applicazione
 */
public class StatisticheProntoSoccorso extends BarChart<String, Number> {

    private final ProntoSoccorso interfacciaGrafica;
    
    public StatisticheProntoSoccorso(ProntoSoccorso interfacciaUtente) {
        super(new CategoryAxis(), new NumberAxis());
        
        this.interfacciaGrafica = interfacciaUtente;
        
        this.getXAxis().setLabel("Data");
        this.getYAxis().setLabel("Totale arrivi");
       
        this.setPrefHeight(320);
        this.setBarGap(5);
        this.setCategoryGap(30);
        this.setLegendVisible(false);
        
        this.setAnimated(false); // Rimuove l'animazione per evitare che l'autoscaling (asse Y) dia informazioni (proporzionali ma) errate
    }
    
    /**
     * Aggiorna il grafico con i dati presenti sul database
     */
    public void aggiornaGrafico() {
        ObservableList<Series<String, Number>> nuoviDati = FXCollections.observableArrayList();
        
        LocalDate dataInizio = interfacciaGrafica.dataInizioPeriodo.getValue();
        LocalDate dataFine = interfacciaGrafica.dataFinePeriodo.getValue();
        HashMap<ColoreGravita, Integer[]> totaleArriviGiornalieri = ArchivioArrivi.calcolaArriviGiornalieri(dataInizio, dataFine);
        
        for(Map.Entry<ColoreGravita, Integer[]> entry : totaleArriviGiornalieri.entrySet()) {
            ColoreGravita gravita = entry.getKey();
            Integer[] numeroArrivi = entry.getValue();
            
            XYChart.Series serie = new XYChart.Series();
            serie.setName(gravita.toString());
            
            for(int i = 0; i < numeroArrivi.length; i++) {
                XYChart.Data<String, Number> data = new XYChart.Data(dataInizio.plusDays(i).toString(), numeroArrivi[i]);
                serie.getData().add(data);
            }
            
            nuoviDati.add(serie);
        }
        
        // Imposta i dati nel grafico
        this.setData(nuoviDati);

        // Colora le barre del grafico
        for (XYChart.Series<String, Number> series : this.getData())
            for(Data data : series.getData())
                data.getNode().setStyle("-fx-bar-fill: " + ColoreGravita.convertiCodiceInColore(series.getName()) + "; -fx-border-color: black");
    }
}

/**
 * Elenco dei colori che indicano la gravità di un paziente
 */
public enum ColoreGravita {
    ROSSO,
    GIALLO,
    VERDE,
    BIANCO;
    
    /**
     * Converte il codice (che può essere un colore gravità o un codice di intervento)
     * inserito dall'Utente in un {@link ColoreGravita}.
     * Se il codice non è riconosciuto o è uguale a null viene restituito null.
     * 
     * @param codice Codice da tradurre: colore gravità (rosso, giallo ...) o codice di intervento (es. KC01R)
     * 
     * @return {@link ColoreGravita} oppure null
     */
    public static ColoreGravita convertiCodiceInGravita(String codice) {
        if(codice == null)
            return null;
        
        String codiceMaiuscolo = codice.toUpperCase();
        
        
        // Riconoscimento colori gravità
        switch(codiceMaiuscolo) {
            case "ROSSO":
            case "GIALLO":
            case "VERDE":
            case "BIANCO":
                return ColoreGravita.valueOf(codiceMaiuscolo);
        }
        
        
        // Riconoscimento codici intervento: l'ultimo carattere (il 5°) del codice intervento indica il colore
        if(codiceMaiuscolo.length() != 5)
            return null;
        
        switch(codiceMaiuscolo.substring(4)) {
            case "R": return ROSSO;
            case "G": return GIALLO;
            case "V": return VERDE;
            case "B": return BIANCO;
            default: return null;
        }
    }
    

    /**
     * Traduce il codice nel corrispondente colore da utilizzare per colorare le righe nella tabella o nelle barre del grafico.
     * Se il codice non è riconosciuto o è uguale a null viene restituito il colore di default (bianco).
     * 
     * @param codice Codice da convertire: colore gravità (rosso, giallo ...) o codice di intervento (es. KC01R)
     * 
     * @return colore espresso in formato esadecimale
     */
    public static String convertiCodiceInColore(String codice) {
        return convertiGravitaInColore(convertiCodiceInGravita(codice));
    }
    
    /**
     * Traduce la gravità nel corrispondente colore da utilizzare per colorare le righe nella tabella o nelle barre del grafico.
     * Se la gravità è uguale a null viene restituito il colore di default (bianco).
     * 
     * @param gravita {@link ColoreGravita Gravità} da convertire
     * 
     * @return colore espresso in formato esadecimale
     */
    public static String convertiGravitaInColore(ColoreGravita gravita) {
        if(gravita == null)
            return "#FFFFFF";
        
        switch(gravita) {
            case ROSSO: return "#FF9999";
            case GIALLO: return "#FFFF99";
            case VERDE: return "#CCFF99";
            default: return "#FFFFFF";
        }
    }
}

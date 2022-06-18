/**
 * Tipologie di trasporto esistenti
 */
public enum TipiDiTrasporto {
    PEGASO,
    AMBULANZA,
    PRIVATO;
    
    /** 
     * @return il nome della costante enum con la prima lettera maiuscola e il resto delle lettere in minuscolo
     */
    @Override
    public String toString() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Classe che si occupa di validare una stringa XML a partire da uno schema (XSD)
 */
public class ValidazioneXML {
    
   /**
     * Valida la stringa XML specificata seguendo il file XSD specificato
     * 
     * @param xml Stringa XML da validare
     * @param schemaPath Path del file XML Schema (.xsd) su cui basare la validazione
     * 
     * @return true se la validazione va a buon fine, false altrimenti
     */
    public static boolean valida(String xml, String schemaPath) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Document d = db.parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
            Schema s = sf.newSchema(new StreamSource(new File(schemaPath)));
            s.newValidator().validate(new DOMSource(d));
            
            return true;
        }
        catch (ParserConfigurationException | SAXException | IOException e) {
            System.err.println("Errore di validazione: " + e.getMessage());
            
            return false;
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
import java.io.File;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 *
 * @author milloypr
 */
public class CompositionParser {
    
    public Set<Playable> xmlToComposition(File file) {
        return null;
    }
    
    public static Document compositionToXML(Set<Playable> composition) {
        try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.newDocument();

            Element root = document.createElement("composition");
            document.appendChild(root);

            composition.forEach((playable) -> {
                Element playableXML = playable.generateXML(document);
                root.appendChild(playableXML);
            });

            return document;
            
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        }
        return null;
    }
    
    public static void printToOutput(Document document, File file) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            
            transformer.transform(domSource, streamResult);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
            
}

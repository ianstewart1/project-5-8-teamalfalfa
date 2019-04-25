/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
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
import jdk.internal.org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


/**
 *
 * @author milloypr
 */
public class CompositionParser {
    
    public static void run() throws org.xml.sax.SAXException, IOException {
        File input = new File("stuff.xml");
        xmlToComposition(input);
    }
    
    public static Set<Playable> xmlToComposition(File file) throws org.xml.sax.SAXException, IOException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(file);  
            
            Set<Playable> composition = new HashSet<Playable>();
            NodeList nodeList = document.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    
                    //Get information from each node
                    System.out.println(elem.getElementsByTagName("pitch").item(0).getTextContent());
                    
                    
                    //For a note, we are given:
                    //channel, delay, duration, pitch, and track
                    
                    
                    //For a gesture, we are given:
                    //delay, duration
                }
                
            }
        
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } 
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
    
    public static void printToOutput(Document document, OutputStream stream) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(stream);
            
            transformer.transform(domSource, streamResult);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
            
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.scene.paint.Color;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;


/**
 *
 * @author milloypr
 */
public class CompositionParser {
    
    public static Set<Playable> xmlToComposition(File file) throws org.xml.sax.SAXException, IOException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(file);  
            
            //Normalize the XML Structure; It's just too important !!
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getDocumentElement().getChildNodes();            
            Set<Playable> composition = nodeListToComposition(nodeList);
            return composition;
        
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } 
        return null;
    }

    private static Set<Playable> nodeListToComposition(NodeList nodeList) {
        Set<Playable> composition = new HashSet<Playable>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;
                
                if (elem.getTagName() == "gesture") {
                    Gesture newGest = parseGesture(elem);
                    composition.add(newGest);
                }
                else if (elem.getTagName() == "note"){
                    Note newNote = parseNote(elem);
                    composition.add(newNote);
                } else {
                    // throw an exception?
                }
            }
        }
        return composition;
    }
    
    private static Note parseNote(Element elem) {
        int channel = Integer.parseInt(elem.getAttribute("channel"));
        double x_coord = Double.parseDouble(elem.getAttribute("delay"));
        double duration = Double.parseDouble(elem.getAttribute("duration"));
        double y_coord = Constants.HEIGHT   
                - Constants.LINE_SPACING 
                * Double.parseDouble(elem.getAttribute("pitch"));
        int track = Integer.parseInt(elem.getAttribute("track"));
        
        Instrument instrument = Instrument.values()[channel]; //get an instrument with channel/track
        
        Note note = new Note(x_coord, y_coord, duration, instrument);
        
        return note;
    }
    
    
    private static Gesture parseGesture(Element elem) {
        NodeList children = elem.getChildNodes();
        Set<Playable> elements = nodeListToComposition(children);
        return new Gesture(elements);
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
    
    public static void printToFile(Document document, File file) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            transformer.transform(domSource, streamResult);
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
    
    public static String printToString(Document document) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new String());
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            
            transformer.transform(domSource, streamResult);
            
            return sw.toString();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
        return null;
    }
            
}

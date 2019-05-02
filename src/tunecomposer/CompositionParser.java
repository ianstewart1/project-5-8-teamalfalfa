/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunecomposer;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
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
import org.xml.sax.InputSource;


/**
 * Manages conversion between composition and XML. Used in implementation of
 * copy, cut, and paste, as well as save. 
 * @author milloypr
 */
public class CompositionParser {
    
    /**
     * Given an InputSource made from an XML String or File, builds the 
     * corresponding composition of playables.
     * @param source, InputSource of XML String or File
     * @return composition, a set of playables
     * @throws org.xml.sax.SAXException
     * @throws IOException 
     */
    public static Set<Playable> xmlToComposition(InputSource source) throws org.xml.sax.SAXException, IOException {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(source);  
            
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
    
    /**
     * Given a list of nodes in an XML InputSource, returns the corresponding
     * set of playables. Helper function of xmlToComposition
     * @param nodeList a list of nodes from the XML input
     * @return composition, a set of playables
     */
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
    
    /**
     * Given an XML Element, constructs and returns a Note object.
     * @param elem an XML Element containing note information
     * @return note, Note object corresponding to elem
     */
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
    
    /**
     * Given an XML Element, recursively constructs and returns a Gesture object.
     * @param elem an XML Element containing gesture information
     * @return gesture, Gesture object corresponding to elem
     */    
    private static Gesture parseGesture(Element elem) {
        NodeList children = elem.getChildNodes();
        Set<Playable> elements = nodeListToComposition(children);
        return new Gesture(elements);
    }
    
    /**
     * Converts a File to an InputSource
     * @param file File
     * @return InputSource
     * @throws FileNotFoundException 
     */
    public static InputSource fileToInputSource(File file) throws FileNotFoundException {
        Reader reader = new FileReader(file);
        return new InputSource(reader);
    }
    
    /**
     * Converts the contents of a Clipboard to an InputSource
     * @param clipboard Clipboard
     * @return InputSource
     * @throws UnsupportedFlavorException
     * @throws IOException 
     */
    public static InputSource clipboardToInputSource(Clipboard clipboard) 
            throws UnsupportedFlavorException, IOException {
        DataFlavor flavor = DataFlavor.stringFlavor;
        String xml = (String) clipboard.getData(flavor);
        Reader reader = new StringReader(xml);
        return new InputSource(reader);
    }
    
    /**
     * Given a set of Playables, writes an XML document.
     * @param composition, a set of Playables
     * @return document, a Document containing XML information for composition
     */
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
    
    /**
     * Converts an XML Document to a File.
     * @param document an XML Document
     * @param file a File containing identical information to document
     */
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
    
    /**
     * Converts an XML Document to a String.
     * @param document an XML Document
     * @return a string containing identical information to document
     */
    public static String printToString(Document document) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(sw);
            
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

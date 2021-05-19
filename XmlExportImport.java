package sqlP;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class XmlExportImport {

	/**
	 * exports vehicles from db to xml.
	 */
    public void exportVehiclesToXML() {
            try (PreparedStatement stmt = SqlCon.getConnection().prepareStatement(
            		SqlCon.SELECT_BOOKS);
                    ResultSet rs = stmt.executeQuery()) {
                
                // create document object.
                Document doc = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder().newDocument();
                
                // push root element into document object.
                Element rootElement = doc.createElement("Books");
                rootElement.setAttribute("exportDate", LocalDateTime.now().toString());
                doc.appendChild(rootElement);
                
                while (rs.next()) {     // run on all  vehicle records..
                    // create vehicles element.
                    Element book = doc.createElement("Book");
                    
                    // assign key to vehicles.
                    Attr attr = doc.createAttribute("bookId");
                    attr.setValue(rs.getString(1));
                    book.setAttributeNode(attr);
                    
                    // push elements to  vehicle.
                    for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
                        Element element = doc.createElement(
                                rs.getMetaData().getColumnName(i)); // push element to doc.
                        rs.getObject(i); // for wasNull() check..
                        element.appendChild(doc.createTextNode(
                                rs.wasNull() ? "" : rs.getString(i)));  // set element value.
                        book.appendChild(element);  // push element to  vehicle.
                    }
                    
                    // push vehicle to document's root element.
                    rootElement.appendChild(book);
                }
                
                // write the content into xml file
                DOMSource source = new DOMSource(doc);
                File file = new File("projectXML/books.xml");
                file.getParentFile().mkdir(); // create xml folder if doesn't exist.
                StreamResult result = new StreamResult(file);
                TransformerFactory factory = TransformerFactory.newInstance();
                
                // IF CAUSES ISSUES, COMMENT THIS LINE.
                factory.setAttribute("indent-number", 2);
                //
                
                Transformer transformer = factory.newTransformer();
                
                // IF CAUSES ISSUES, COMMENT THESE 2 LINES.
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
               
                transformer.transform(source, result);
                
                JOptionPane.showMessageDialog(null,"vehicles data exported successfully!");
            } catch (SQLException | NullPointerException | ParserConfigurationException
                    | TransformerException e) {
                e.printStackTrace();
            }
    }
    
    /**
     * imports vehicles from xml to db.
     * @param path xml filepath.
     */
    public void importVehiclesFromXML(String path) {
    	boolean f2 = true, f3 = true;
    	try {
			Document doc = DocumentBuilderFactory.newInstance()
								.newDocumentBuilder().parse(new File(path));
			doc.getDocumentElement().normalize();
			NodeList nl = doc.getElementsByTagName("Book");
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					String str = el.getAttribute("bookId");
					int code = Integer.parseInt(str);
					String str2 = el.getElementsByTagName("title").item(0).getTextContent();
					String str3 = el.getElementsByTagName("author").item(0).getTextContent();
					String str4 = el.getElementsByTagName("category").item(0).getTextContent();
					String str5 = el.getElementsByTagName("releaseDate").item(0).getTextContent();
					String str6 = el.getElementsByTagName("fileSize").item(0).getTextContent();
					String str7 = el.getElementsByTagName("filePath").item(0).getTextContent();
					int fileSize = Integer.parseInt(str6);
					boolean flag = checkBook(str2);
					if(flag == false)
					{
						insertTable.insertTableFunc(str2, str3, str4, str5, fileSize,
								str7);
					}
				}
			}
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
    }
    public boolean checkBook(String title) {
    	try {
			PreparedStatement stmt = SqlCon.getConnection().prepareStatement(SqlCon.CHECK_TITLE);
			stmt.setString(1,title);
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
		}catch (SQLException e) {
			 e.printStackTrace();
		}
		return false;

    }
}

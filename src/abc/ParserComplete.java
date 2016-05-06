package abc;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParserComplete {
	StorageClass StorageObject = new StorageClass();
	/**
	 * Parses the contents of the XML file and sets up various data types
	 * @param context : gets the path to the working directory of the project
	 */
	public void parsing(String context)
	{
		StorageObject.flush();  //Clears the data in the Storage Class
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();					//
			Document document = builder.parse(new File(context+"/circuit.xml"));	//necessary objects
			Element root = document.getDocumentElement();							//gets the document root
			System.out.println(root.getNodeName());									//prints the name of the element defined earlier
			NodeList nList = root.getChildNodes();									//get subtags for the root into a Node List(stores the inputs, gates, outputs, connnections tags
			System.out.println("============================");						
			for (int temp = 0; temp < nList.getLength(); temp++)					//loop through the node list
			{
			 Node node = nList.item(temp);											//get an element out of the node list
			 System.out.println("");    //Just a separator
			 if (node.getNodeType() == Node.ELEMENT_NODE)							//If node is of Element type
			 {
				 Element eElement = (Element) node;									//type cast the node into the element type
				 //Inputs tag of XML file
				 if(eElement.getNodeName()=="inputs")
				 {
					 System.out.println(eElement.getNodeName());
					 System.out.println("============================");
					 //Go down one step into tree
					 NodeList nList1 = eElement.getChildNodes();
					 for (int temp1 = 0; temp1 < nList1.getLength(); temp1++)
						{
						 Node node1 = nList1.item(temp1);
						 if (node1.getNodeType() == Node.ELEMENT_NODE)
						 {
							 //Get input attributes
							 Element eElement1 = (Element) node1;
							 StorageObject.setInputs(eElement1.getAttribute("id"));
							 System.out.println("Input "+eElement1.getAttribute("id"));
						 }
						}
				 }
				 //Gates tag of XML file
				 if(eElement.getNodeName()=="gates")
				 {
					 System.out.println(eElement.getNodeName());
					 System.out.println("============================");
					 NodeList nList1 = eElement.getChildNodes();
					 for (int temp1 = 0; temp1 < nList1.getLength(); temp1++)
						{
						 Node node1 = nList1.item(temp1);
						 
						 if (node1.getNodeType() == Node.ELEMENT_NODE)
						 {
							 Element eElement1 = (Element) node1;
							 StorageObject.setGates(eElement1.getAttribute("id"));
							 System.out.println("Gate "+eElement1.getAttribute("id"));
						 }
						}
				 }
				 //Outputs tag of XML file
				 if(eElement.getNodeName()=="outputs")
				 {
					 System.out.println(eElement.getNodeName());
					 System.out.println("============================");
					 NodeList nList1 = eElement.getChildNodes();
					 for (int temp1 = 0; temp1 < nList1.getLength(); temp1++)
						{
						 Node node1 = nList1.item(temp1);
						 if (node1.getNodeType() == Node.ELEMENT_NODE)
						 {
							 Element eElement1 = (Element) node1;
							 System.out.print("Output "+eElement1.getAttribute("id"));
							 NodeList nList2 = eElement1.getChildNodes();
							 for(int temp2 = 0; temp2 < nList2.getLength(); temp2++)
							 {
								 Node node2 = nList2.item(temp2);
								 if(node2.getNodeType()==Node.ELEMENT_NODE)
								 {
									 Element eElement2 = (Element) node2;
									 System.out.print(" with source as "+ eElement2.getElementsByTagName("type").item(0).getTextContent()+" id "+eElement2.getElementsByTagName("id").item(0).getTextContent()+"\n");
									 StorageObject.setOutputs(eElement1.getAttribute("id"), eElement2.getElementsByTagName("type").item(0).getTextContent(), eElement2.getElementsByTagName("id").item(0).getTextContent());
								 }
								 
							 }
							 
						 }
						}
				 }
				 //Connections tag of XML File
				 if(eElement.getNodeName()=="connections")
				 {
					 System.out.println(eElement.getNodeName());
					 System.out.println("============================");
					 NodeList nList1 = eElement.getChildNodes();
					 for (int temp1 = 0; temp1 < nList1.getLength(); temp1++)
						{
						 Node node1 = nList1.item(temp1);
						 if(node1.getNodeType()==node.ELEMENT_NODE)
						 {
							 Element eElement1 = (Element) node1;
							 System.out.print("Gate id "+eElement1.getAttribute("id")+" of type "+eElement1.getAttribute("type")+ "\n");
							 NodeList nList2 = eElement1.getChildNodes();
							 //initiate calling process
							 int counter = 0;
							 String id = eElement1.getAttribute("id");
							 String type = eElement1.getAttribute("type");
							 String SourceType = null;
							 String SourceId = null;
							 String OtherSourceType = null;
							 String OtherSourceId = null;
							 for(int temp2 = 0; temp2<nList2.getLength();temp2++)
							 {
								 Node node2 = nList2.item(temp2);
								 if(node2.getNodeType()==node.ELEMENT_NODE)
								 {
									 Element eElement2 = (Element) node2;
									 System.out.println("source type "+eElement2.getElementsByTagName("type").item(0).getTextContent()+" with id "+eElement2.getElementsByTagName("id").item(0).getTextContent());
									 if(counter==1)
									 {
										 OtherSourceType = eElement2.getElementsByTagName("type").item(0).getTextContent();
										 OtherSourceId = eElement2.getElementsByTagName("id").item(0).getTextContent();
										 counter++;
									 }
									 if(counter==0)
									 {
										 SourceType = eElement2.getElementsByTagName("type").item(0).getTextContent();
										 SourceId = eElement2.getElementsByTagName("id").item(0).getTextContent();
										 counter++;
									 }
								 }
							 }
							 if(counter==1)
							 {
								 StorageObject.setConnections(id, type, SourceType, SourceId);
							 }
							 if(counter==2)
							 {
								 StorageObject.setConnections(id, type, SourceType, SourceId, OtherSourceType, OtherSourceId);
							 }
						 }
						}
				 }
			 }
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

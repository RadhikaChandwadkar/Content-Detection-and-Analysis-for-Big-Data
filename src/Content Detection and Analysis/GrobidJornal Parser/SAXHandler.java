import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class SAXHandler extends DefaultHandler 
{

	boolean bfname = false;

	List<String> authorList = new ArrayList<>();
	String author = "";
	String content = "";

	// Triggered when the start of tag is found.
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
//		System.out.println("in start:"+qName);
		//checking for author tag
		if (qName.equalsIgnoreCase("persname")) {
			author="";
			bfname=false;
		}
		
		
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	//	System.out.println("in end:"+qName);
		if (qName.equalsIgnoreCase("persname")) {
			authorList.add(author);
			System.out.println("added: "+author);
			author="";
		}
		else if (qName.equalsIgnoreCase("forename")) {
			author=author+" "+content;
		}
		
		else if (qName.equalsIgnoreCase("surname")) {
			author=author+" "+content;
		}
	}
	
	
	


	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	
		content = String.copyValueOf(ch, start, length).trim();
		//System.out.println("content"+content);
	}
}//class
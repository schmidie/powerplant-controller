package power_plant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.xml.XMLConstants;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/*
 * PowerPlantController managing the PowerProfile of a power-plant:
 * 	* validate PowerProfile-file against XSD-Schema-File
 * 	* Read a XML PowerProfile-file
 * 	* Print the powerProfile
 */
public class PowerPlantController {
	
	private static PowerProfile powerProfile = null;
	
	
	public static void main(String [] args){
		String profile = args[0];
		String schema = args[1];
		boolean schema_validation = validateProfile(profile,schema);
		if (!schema_validation){
			System.out.println("Schema-Validation NOT succeeded!");
		}
		// read the PowerProfile-File only if the schema validation succeeded
		else{
			System.out.println("Schema-Validation succeeded!");
			readPowerProfile(profile);
			printPowerProfile();
		}
	}
	
	public static void printPowerProfile(){
		if(powerProfile != null){
			System.out.println("PowerProfile");
			System.out.println("--");
			System.out.println("Start: " + powerProfile.getStart());
			System.out.println("End: " + powerProfile.getEnd());
			System.out.println("--");
			for(PowerTimeTuple ptt: powerProfile.getPlan()){
				System.out.println("");
				System.out.println("Time: " + ptt.getTime());
				System.out.println("Power: " + ptt.getPower());
				System.out.println("");
			}
		}
		else{
			System.out.println("No Profile available.");
		}
	}
	
	/*
	 * validateProfile(String xml_power_profile, String schema_file)
	 * returns: 	true if the PowerProfile-file Validation against the XSD-Schema-File succeeded
	 * 		false otherwise
	 */
	public static boolean validateProfile(String xml_power_profile, String schema_file){
		// Validate the XML-PowerProfile against an XSD-Schema
		try {
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new File(schema_file));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new File(xml_power_profile)));
		} catch (SAXException | IOException e) {
			System.out.println("Exception: "+e.getMessage());
			return false;	
		}
		return true;
	}
	
	/*
	 * readPowerProfile(String xml_power_profile)
	 * readPowerProfile parses the given XML-Profile. 
	 * If it is a valid PowerProfile the Profile is stored in powerProfile of the PowerPlantController.
	 * 
	 * returns: true if the xml_power_profile is a valid PowerProfile (see constrains of class PowerProfile)
	 */
	public static boolean readPowerProfile(String xml_power_profile){
		try{
			// Use Stax Parser with the curser-method for parsing the XML-Profile
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader parser = factory.createXMLStreamReader( new FileInputStream(xml_power_profile) );
			int last_power = 0;
			ZonedDateTime last_power_time = null;
			String element_value = "";
			boolean eval_status_ok= true;
			
			while(parser.hasNext()){	
				int event = parser.next();
				switch(event){ 
					case XMLStreamConstants.START_ELEMENT:
						if ("powerProfile".equals(parser.getLocalName())){
							powerProfile = new PowerProfile();
						}
						break;
					case XMLStreamConstants.CHARACTERS:
						element_value = parser.getText().trim();
						break;
					case XMLStreamConstants.END_ELEMENT:
						switch(parser.getLocalName()){
							case "power_kW":
								last_power =  Integer.parseInt(element_value);
								break;
							case "timestamp":
								last_power_time =  ZonedDateTime.parse(element_value, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
								break;
							case "start":
								eval_status_ok = powerProfile.setStart(ZonedDateTime.parse(element_value, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
								break;
							case "end":
								eval_status_ok = powerProfile.setEnd(ZonedDateTime.parse(element_value, DateTimeFormatter.ISO_OFFSET_DATE_TIME));
								break;
							case "power":
								eval_status_ok = powerProfile.pushNextPlanEntry(last_power, last_power_time);
								break;
						}// inner-switch
				}//outer-switch
				// we do not want to go on parsing, if the PowerProfile is not valid! 
				if(!eval_status_ok){
					powerProfile = null;
					return false;
				}
			}//while
		}catch ( XMLStreamException | FileNotFoundException e){
			System.out.println("Exception: "+e.getMessage());
			return false;
		}
		return true;
	} 
	
} // class

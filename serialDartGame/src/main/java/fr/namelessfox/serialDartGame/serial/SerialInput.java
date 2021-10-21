package fr.namelessfox.serialDartGame.serial;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.server.Command;

import fr.namelessfox.serialDartGame.domaine.DartCase;
import fr.namelessfox.serialDartGame.dto.DartCaseDto;
import fr.namelessfox.serialDartGame.repository.DartCaseRepository;
import fr.namelessfox.serialDartGame.service.DartCaseService;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class SerialInput {

	private static Logger logger = LogManager.getLogger(SerialInput.class);
	
	
	private DartCaseService dartCaseService;
	
	private String dataReading = "";
	private String dataParsed = "";
	
	private SerialPort serialPort;
	
	private boolean isWriting = false;
	
	public SerialInput(DartCaseService dartCaseService) {
		this.dartCaseService = dartCaseService;
	}
	
	public void setPort(String portName) {
		serialPort = new SerialPort(portName);
	}
	
	public List<String> getPortNames(){
		return Arrays.asList(SerialPortList.getPortNames());
	}
	
	public void startSerialReading() {
		logger.info("Port : {}", serialPort.getPortName());
	    try {
	        serialPort.openPort();//Open serial port
	        serialPort.setParams(9600, 8, 1, 0);//Set params.
	        
	       
	    }
	    catch (SerialPortException ex) {
	        logger.error(ex);
	    }
	}
	
	public void readData(Consumer<DartCaseDto> consumerSuccess) {
		while(true) {
			
		
		try {
			dataReading = serialPort.readString(1);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if(dataReading != null) {
    		logger.debug(dataReading);
    		if(dataReading.equals("%")) {
    			logger.debug("Start reading");
    			isWriting = true;

    		}
    		if(isWriting) {
    			if(dataReading.equals("$")) {
    				logger.debug("Stop reading");
    				logger.debug("Result: "+dataParsed);
    				logger.debug(Character.getNumericValue(dataParsed.charAt(0)));

    				logger.debug(Character.getNumericValue(dataParsed.charAt(2)));
    				
    				Optional<DartCaseDto> caseInput = dartCaseService.getDartCaseByCoord(Character.getNumericValue(dataParsed.charAt(0)),
    						Character.getNumericValue(dataParsed.charAt(2)));
    				if(caseInput.isPresent()) {
    					logger.info("Hit {} for {} points !", caseInput.get().getLabel(), caseInput.get().getValue());
    					consumerSuccess.accept(caseInput.get());
    				}
    				dataParsed = "";
    				isWriting = false;
    			} else if (!dataReading.equals("%")){
    				dataParsed = dataParsed + dataReading;
    			}
    		}
    	}
		}
	}
}

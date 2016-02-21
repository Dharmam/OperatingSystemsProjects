package batchProcessing;

import org.w3c.dom.Element;

public class WDCommand extends Command  {

	@Override
	public String describe() {
		return inFile;
		// TODO Auto-generated method stub
		
	}

	@Override
	public  void parse(Element elem) throws ProcessException {
		if(elem.getAttributes()== null ) throw new ProcessException("Empty Wd Attribute.");
		else{
			String path = elem.getAttribute("path");
		Command.setPath(path);
		
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	
	

}

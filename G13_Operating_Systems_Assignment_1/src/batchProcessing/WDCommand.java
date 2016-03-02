package batchProcessing;

import java.io.File;

import org.w3c.dom.Element;

public class WDCommand extends Command  {

	@Override
	public String describe() {
		return inFile;
		// TODO Auto-generated method stub

	}

	public  void parse(Element elem) throws ProcessException {
		if(!elem.hasAttributes()) throw new ProcessException("Empty Wd Attribute.");
		else{
			String path = System.getProperty("user.dir") + "/" + elem.getAttribute("path");
			File file = new File(path);
			if (!file.getParentFile().exists()) {
				System.out.println(file.getParent());
				file.getParentFile().mkdirs();
			}
			this.setFilePath(path);

			String id = elem.getAttribute("id");
			this.setId(id);
		}
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}



}

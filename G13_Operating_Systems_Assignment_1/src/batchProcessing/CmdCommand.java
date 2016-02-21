package batchProcessing;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.w3c.dom.Element;

public class CmdCommand extends Command {

	@Override
	public String describe() {
		return inFile;
		// TODO Auto-generated method stub

	}

	@Override
	public void parse(Element elem) throws ProcessException {
		if(!elem.hasAttributes()) throw new ProcessException("CMD command is inavlid.");
		else {

			if(elem.hasAttribute("id"))setId(elem.getAttribute("id"));
			else throw new ProcessException("CMD id is empty.");


			if(elem.hasAttribute("path"))setPath(elem.getAttribute("path"));
			else throw new ProcessException("File path is empty.");

			if(elem.hasAttribute("args"))setArgs(elem.getAttribute("args"));
			else throw new ProcessException("Args empty.");


			if(elem.hasAttribute("in"))setInFile(Batch.lookup.get(elem.getAttribute("in")).getFilePath());

			if(elem.hasAttribute("out"))setOutFile(Batch.lookup.get(elem.getAttribute("out")).getFilePath());
		}
	}



	@Override
	public void execute() {

		try 
		{ 
			System.out.println("Executing Command: cmd "+this.args);
			Process p=Runtime.getRuntime().exec("cmd "+ this.args); 
			p.waitFor(); 
			BufferedReader reader=new BufferedReader(
					new InputStreamReader(p.getInputStream())
					); 

			System.out.println("Writing cmd output in the output file at the " + this.outFile );
			FileWriter filer = new FileWriter(this.outFile);
			PrintWriter	out = new PrintWriter(filer);
			String line; 
			while((line = reader.readLine()) != null) 
			{ 
				out.println(line);
			}
			out.close();
			out.flush();
			filer.close();
		}
		catch(IOException e1) {System.out.println(e1.toString());} 
		catch(InterruptedException e2) {System.out.println(e2.toString());} 

		System.out.println("Done"); 

	}

}

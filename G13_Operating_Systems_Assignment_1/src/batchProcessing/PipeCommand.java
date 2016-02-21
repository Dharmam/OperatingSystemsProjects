package batchProcessing;

import java.io.BufferedReader;
import java.lang.Object;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.apache.commons.io.input.ReaderInputStream;



import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PipeCommand extends Command {
	public String describe()
	{
		return inFile;
	}

	InputStream data = null  ;
	PipedInputStream pin = null;
	PipedOutputStream pout = null;

	@Override
	public void parse(Element elem) throws ProcessException {

		if(!elem.hasAttributes()) throw new ProcessException("PIPE command is inavlid.");
		else {

			if(elem.hasAttribute("id"))setId(elem.getAttribute("id"));
			else throw new ProcessException("PIPE id is empty.");

			if(elem.hasAttribute("path"))setFilePath(elem.getAttribute("path"));
			else throw new ProcessException("File path is empty.");

			if(elem.hasAttribute("args"))setArgs(elem.getAttribute("args"));
			//	else throw new ProcessException("Args empty.");


			if(elem.hasAttribute("in"))setInFile(Batch.lookup.get(elem.getAttribute("in")).getFilePath());

			if(elem.hasAttribute("out"))setOutFile(Batch.lookup.get(elem.getAttribute("out")).getFilePath());
		}
	}

	@Override
	public void execute() throws ProcessException
	{
		try
		{
			//this is input command.
			if(this.outFile == null)
			{
				System.out.println("Executing pipe" + this.args);
				StringBuffer com = new StringBuffer();
				if(this.getId()==null) throw new ProcessException("Invalid CMD command.");
				else	{	
					System.out.println(this.filePath);
					com.append("cmd /c " + this.filePath + " " + this.args + " testApp1.AddLines < " + this.inFile  );
					System.out.println(com.toString());
					Process p=Runtime.getRuntime().exec(com.toString()); 
					p.waitFor(); 
					BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
					pout = new PipedOutputStream();
					pin = new PipedInputStream(pout);
					pout.write(reader.toString().getBytes() );
					System.out.println("Cmd output collected." );

				}		
			}

			else {
				
				System.out.println("Writing cmd output in the output file at the " + this.outFile );
				StringBuffer com = new StringBuffer();
				if(this.getId()==null) throw new ProcessException("Invalid CMD command.");
				else	{	
					System.out.println(this.filePath);
					com.append("cmd /c " + this.filePath + " " + this.args + " testApp1.AddLines < " + pin  );

					Process p=Runtime.getRuntime().exec(com.toString()); 
					p.waitFor(); 

					BufferedReader reader=new BufferedReader(new InputStreamReader(p.getInputStream()));
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
			}
		}

		catch(IOException e1) {System.out.println(e1.toString());} 
		catch(InterruptedException e2) {System.out.println(e2.toString());} 

		System.out.println("Done"); 

	}
}

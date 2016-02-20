package batchProcessing;

public abstract class Command {

	public abstract void describe();
	
	public abstract void parse();
	
	public abstract void execute();
	
}

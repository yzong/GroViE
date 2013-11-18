package de.grogra.grove.test;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class TestDB implements Runnable{

	Thread t;
	String DB_PATH = "C:\\Users\\yong\\db";
	GraphDatabaseService graphDb;
			
	public TestDB()
	{
		 // Create a new, second thread
	      t = new Thread(this, "NeoTest DB Thread");
	      System.out.println("NeoTest DB thread: " + t);
	      t.start(); // Start the thread
	}
	
	@Override
	public void run() {
		
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( DB_PATH );
		registerShutdownHook( graphDb );
	}
	
	public GraphDatabaseService getDB()
	{
		return graphDb;
	}
	
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running application).
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
}

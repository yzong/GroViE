package de.grovie.db;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph;

import de.grovie.exception.GrovieExceptionDbUnrecognizedImpl;

/**
 * This class provides database access to the package de.grovie.data.
 * Blueprints API is used as the java interface to so as to keep 
 * GroViE independent from the underlying graph database implementation.
 * 
 * @author yong
 */
public class GrovieDb {

	//types of database implementation (implementing Blueprints API)
	public enum GrovieDbImpl
	{
		NEO4J,
		INFINITE_GRAPH, //temp not in use
		ORACLE_NOSQL,	//temp not in use
		TITAN			//temp not in use
	}
	
	private final static GrovieDbImpl lGrovieDbImplDefault = GrovieDbImpl.NEO4J; 
	
	private static GrovieDb lInstance;			//singleton instance of GrovieDb
	private static GrovieDbImpl lGrovieDbImpl;	//database implementation (e.g. Neo4j, Titan, etc.)
	
	private Graph lGraph; //instance of database graph
	
	/**
	 * Constructor
	 * @param dbPathAbs
	 * @throws GrovieExceptionDbUnrecognizedImpl
	 */
	private GrovieDb(String dbPathAbs) throws GrovieExceptionDbUnrecognizedImpl
	{
		lGraph = createDb(dbPathAbs, lGrovieDbImplDefault);
		lGrovieDbImpl = lGrovieDbImplDefault;
	}

	/**
	 * Constructor
	 * @param dbPathAbs
	 * @param impl
	 * @throws GrovieExceptionDbUnrecognizedImpl
	 */
	private GrovieDb(String dbPathAbs, GrovieDbImpl impl) throws GrovieExceptionDbUnrecognizedImpl
	{
		try{
			lGraph = createDb(dbPathAbs, impl);
			lGrovieDbImpl = impl;
		}
		catch(GrovieExceptionDbUnrecognizedImpl err)
		{
			lGraph = createDb(dbPathAbs, lGrovieDbImplDefault);
			lGrovieDbImpl = lGrovieDbImplDefault;
		}
	}
	
	/**
	 * Creates database at the specified path using the specified database implementation.
	 * @param dbPathAbs
	 * @param impl
	 * @return instance of graph database
	 * @throws GrovieExceptionDbUnrecognizedImpl
	 */
	private Graph createDb(String dbPathAbs, GrovieDbImpl impl) throws GrovieExceptionDbUnrecognizedImpl
	{
		if(impl==GrovieDbImpl.NEO4J)
			return new Neo4jGraph(dbPathAbs);
		else
			throw new GrovieExceptionDbUnrecognizedImpl("GrovieExceptionDb unrecognized database implementation: " + impl);
	}

	/**
	 * Get singleton instance of GrovieDb
	 * @param dbPathAbs
	 * @return
	 * @throws GrovieExceptionDbUnrecognizedImpl
	 */
	public static GrovieDb getInstance(String dbPathAbs) throws GrovieExceptionDbUnrecognizedImpl {
		if (lInstance == null) {
			lInstance = new GrovieDb(dbPathAbs);
		}
		return lInstance;
	}

	/**
	 * Get singleton instance of GrovieDb
	 * @param dbPathAbs
	 * @param impl
	 * @return instance of GrovieDb
	 * @throws GrovieExceptionDbUnrecognizedImpl
	 */
	public static GrovieDb getInstance(String dbPathAbs, GrovieDbImpl impl) throws GrovieExceptionDbUnrecognizedImpl {
		if (lInstance == null) {
			lInstance = new GrovieDb(dbPathAbs, impl);
		}
		return lInstance;
	}
	
	/**
	 * Get the database implementation type.
	 * @return database implementation type (e.g. Neo4j, Titan, etc.)
	 */
	public static GrovieDbImpl getDbImpl()
	{
		return lGrovieDbImpl;
	}
	
	public Graph getGraph()
	{
		return lGraph;
	}
}

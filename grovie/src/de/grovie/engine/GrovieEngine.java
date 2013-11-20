package de.grovie.engine;

/**
 * This class is the main class representing the visualization engine.
 * It operates in one of three modes - in memory, embedded or server.
 * The default operation is embedded, i.e. the engine runs with a database
 * embedded in the same application as the engine itself. 
 * The engine can also run in memory, i.e. without a database. In this mode,
 * the scene graph is constructed manually instead of synchronizing with the
 * database.
 * In server mode, the engine synchronizes its data with a remote database.
 * @author yong
 *
 */
public class GrovieEngine {

	public enum GrovieEngineMode
	{
		IN_MEM,		//visualization engine without database 
		EMBEDDED, 	//visualization engine with embedded database - default mode
		SERVER		//visualization engine with database server
	}

	private static GrovieEngine lInstance;	//singleton instance of engine

	private final GrovieEngineMode lMode;

	private GrovieEngine()
	{
		lMode = GrovieEngineMode.EMBEDDED;
	} 

	private GrovieEngine(GrovieEngineMode mode)
	{
		this.lMode = mode;
	} 

	public static synchronized GrovieEngine getInstance() {
		if (lInstance == null) {
			lInstance = new GrovieEngine(GrovieEngineMode.EMBEDDED);
		}
		return lInstance;
	}

	public static synchronized GrovieEngine getInstance(GrovieEngineMode mode) {
		if (lInstance == null) {
			lInstance = new GrovieEngine(mode);
		}
		return lInstance;
	}
}

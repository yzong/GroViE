/**
 * 
 */
package de.grogra.grovie.test;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;



/**
 * @author yong
 *
 */
public class TestArchitect {

	public static GraphDatabaseService graphDb;
	public static String DB_PATH = "C:\\Users\\yong\\db";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("TestArchitect Main");
		
		System.out.println("Test Neo4j");
		testDB();
		System.out.println("Test Neo4j End");
		
		
		System.out.println("Test JOGL");
		testGL();
		System.out.println("Test JOGL End");
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
	
	private static void testDB()
	{
		try{
			graphDb = new TestDB().getDB();
		}catch(Exception e)
		{
			System.out.println("testDB failed.");
		}
	}
	
	private static void testGL()
	{
		try{
			GLProfile glprofile = GLProfile.getDefault();
	        GLCapabilities glcapabilities = new GLCapabilities( glprofile );
	        final GLCanvas glcanvas = new GLCanvas( glcapabilities );
	
	        glcanvas.addGLEventListener( new GLEventListener() {
	            
	            @Override
	            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
	                OneTriangle.setup( glautodrawable.getGL().getGL2(), width, height );
	            }
	            
	            @Override
	            public void init( GLAutoDrawable glautodrawable ) {
	            }
	            
	            @Override
	            public void dispose( GLAutoDrawable glautodrawable ) {
	            }
	            
	            @Override
	            public void display( GLAutoDrawable glautodrawable ) {
	                OneTriangle.render( glautodrawable.getGL().getGL2(), glautodrawable.getWidth(), glautodrawable.getHeight() );
	            }
	        });
	
	        final Frame frame = new Frame( "One Triangle AWT" );
	        frame.add( glcanvas );
	        frame.addWindowListener( new WindowAdapter() {
	            public void windowClosing( WindowEvent windowevent ) {
	                frame.remove( glcanvas );
	                frame.dispose();
	                System.exit( 0 );
	            }
	        });
	
	        frame.setSize( 640, 480 );
	        frame.setVisible( true );
		}
		catch(Exception e)
		{
			System.out.println("testGL failed.");
		}
	}
}

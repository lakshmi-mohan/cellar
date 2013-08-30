import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


public class PathPropertyListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String rootPath = sce.getServletContext().getRealPath("/");
        System.setProperty("webroot", rootPath);
        
        System.out.println("Root###########" + rootPath);
		
	}

}

package study;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import servlet.MyFirstServlet;

import java.io.File;

public class TestApplication{
    public static int TOMCAT_PORT = 8080;
    public static String TOMCAT_HOSTNAME = "127.0.0.1";
    public static String WEBAPP_PATH = "src/main";
    public static void main(String[] args) throws LifecycleException {
        run();
    }
    public static void run() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(TOMCAT_PORT);
        tomcat.setHostname(TOMCAT_HOSTNAME);
        // tomcat 信息保存在项目下
        tomcat.setBaseDir(".");
        StandardContext myContext = null;
        System.out.println("file:"+System.getProperty("user.dir"));
        myContext = (StandardContext) tomcat.addWebapp("/tomcat", System.getProperty("user.dir") + File.separator + WEBAPP_PATH);
        myContext.setReloadable(false);
        // 上下文监听器
        myContext.addLifecycleListener(new AprLifecycleListener());
        // 注册servlet
        tomcat.addServlet("/tomcat", "myFirstServlet",new MyFirstServlet());
        // servlet mapping
        myContext.addServletMappingDecoded("/first.do", "myFirstServlet");
        tomcat.start();
        tomcat.getServer().await();
    }
}
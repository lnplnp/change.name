package fr.manuelpayet;

import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import org.codehaus.plexus.util.IOUtil;
import org.hibernate.Session;
import org.hibernate.util.HibernateUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.manuelpayet.jsonobject.Person;
import fr.manuelpayet.name.FirstName;

/**
 * Hello world!
 * 
 */
public class App {

  private static Logger log = LoggerFactory.getLogger(App.class);

  private Long createAndStoreFirstName(String label) {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();

    FirstName firstName = new FirstName(label);
    session.save(firstName);

    session.getTransaction().commit();
    return firstName.getId();
  }

  // private List<?> listEvents() {
  // Session session = HibernateUtil.getSessionFactory().getCurrentSession();
  // session.beginTransaction();
  //
  // List<?> result = session.createQuery("from Event").list();
  // session.getTransaction().commit();
  // return result;
  // }

  public static void main(String[] args) {

    // Create a variable for the connection string.
    String connectionUrl = "jdbc:sqlserver://localhost:1433;" + "databaseName=CeritekQM;user=sa;password=ceritek93";

    // Declare the JDBC objects.
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
      // Establish the connection.
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      con = DriverManager.getConnection(connectionUrl);

      // Create and execute an SQL statement that returns some data.
      String SQL = "SELECT ID, LOGINNAME FROM CKQM_LOGINS";
      stmt = con.createStatement();
      rs = stmt.executeQuery(SQL);

      // Iterate through the data in the result set and display it.
      while (rs.next()) {
        System.out.println(rs.getString("id") + " " + rs.getString("loginname"));
        log.debug("");
      }

      log.info("Essaie via Hibernate");
      App app = new App();
      app.createAndStoreFirstName("");

      ObjectMapper mapper = new ObjectMapper();
      URL url = new URL("http://randomuser.me/g/");
      URLConnection openConnection = url.openConnection();
      InputStream inputStream = openConnection.getInputStream();

      StringWriter writer = new StringWriter();
      IOUtil.copy(inputStream, writer);
      String string = writer.toString();
      log.info(string);

      // Person person = mapper.readValue(string, Person.class);

      JSONObject jsonObject = new JSONObject(inputStream);
      Iterator<?> keys = jsonObject.keys();
      while (keys.hasNext()) {
        String s = (String) keys.next();
        log.info("key : '{}'", s);
      }
      // log.info("person.getClass() : '{}'", person.getClass());
      // log.info("First Name : '{}'", person.getUser().getName().getFirst());

    }
    // Handle any errors that may have occurred.
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (rs != null)
        try {
          rs.close();
        } catch (Exception e) {
        }
      if (stmt != null)
        try {
          stmt.close();
        } catch (Exception e) {
        }
      if (con != null)
        try {
          con.close();
        } catch (Exception e) {
        }
    }
  }
}

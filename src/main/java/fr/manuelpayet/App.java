package fr.manuelpayet;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;

import org.codehaus.plexus.util.IOUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 * 
 */
public class App {

  private static Logger log = LoggerFactory.getLogger(App.class);

  // private Long createAndStoreFirstName(String label) {
  // Session session = HibernateUtil.getSessionFactory().getCurrentSession();
  // session.beginTransaction();
  //
  // FirstName firstName = new FirstName(label);
  // session.save(firstName);
  //
  // session.getTransaction().commit();
  // return firstName.getId();
  // }

  // private List<?> listEvents() {
  // Session session = HibernateUtil.getSessionFactory().getCurrentSession();
  // session.beginTransaction();
  //
  // List<?> result = session.createQuery("from Event").list();
  // session.getTransaction().commit();
  // return result;
  // }

  public static void main(String[] args) {

    log.info("Appel direct à la base de données");
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

    }
    // Handle any errors that may have occurred.
    catch (Exception e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    } finally {
      if (rs != null)
        try {
          log.info("Try closing resultset...");
          rs.close();
          log.info("ResultSet closed.");
        } catch (Exception e) {
          log.warn(e.getMessage());
        }
      if (stmt != null)
        try {
          log.info("Try closing statement...");
          stmt.close();
          log.info("Statement closed.");
        } catch (Exception e) {
          log.warn(e.getMessage());
        }
      if (con != null)
        try {
          log.info("Try closing connection...");
          con.close();
          log.info("Connection closed.");
        } catch (Exception e) {
          log.warn(e.getMessage());
        }
    }

    URL url = null;
    try {
      url = new URL("http://randomuser.me/g/");
    } catch (MalformedURLException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }
    URLConnection openConnection = null;
    try {
      openConnection = url.openConnection();
    } catch (IOException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }
    InputStream inputStream = null;
    try {
      inputStream = openConnection.getInputStream();
    } catch (IOException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }

    StringWriter writer = new StringWriter();
    try {
      IOUtil.copy(inputStream, writer);
    } catch (IOException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }
    String jsonString = writer.toString();
    log.info(jsonString);

    log.info("Utilisation de la librairie org.json");
    orgJson(jsonString);

    // log.info("Essaie via Hibernate");
    // App app = new App();
    // app.createAndStoreFirstName("");

  }

  private static void orgJson(String string) {
    log.info("string '{}'", string);
    JSONObject jsonObject = null;
    try {
      jsonObject = new JSONObject(string);
    } catch (JSONException e) {
      e.printStackTrace();
      log.warn(e.getMessage());
    }
    Iterator<?> keys = jsonObject.keys();
    while (keys.hasNext()) {
      String s = (String) keys.next();
      log.info("key : '{}'", s);
      try {
        JSONObject jsonObject2 = jsonObject.getJSONObject(s);
        log.info("jsonObject2 '{}'", jsonObject2);
        orgJson(jsonObject2.toString());
      } catch (JSONException e) {
        e.printStackTrace();
        log.warn(e.getMessage());
      }
    }
  }
}

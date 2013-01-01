package com.example.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.model.UserCredentials;

/*
 * Chasemaster web service client stack (com.chasemaster.ws.client.*)
 * is generated from WSDL by using CXF.
 */
@Service
public class WhetherServiceImpl implements WhetherService {
   private final static Logger logger = Logger.getLogger(WhetherServiceImpl.class);
   
   @Override
   public String register(User user) {
      logger.debug("In register(): " + user);
      
      // calling web service
      com.chasemaster.ws.client.ChasemasterControllerImplService service = new com.chasemaster.ws.client.ChasemasterControllerImplService();
      com.chasemaster.ws.client.ChasemasterController port = service.getChasemasterControllerImplPort();
      com.chasemaster.ws.client.User u = new com.chasemaster.ws.client.User();
      u.setUsername(user.getUsername());
      u.setPassword(user.getPassword());
      u.setPasswordConfirmation(user.getPasswordConfirmation());
      u.setEMail(user.getEmail());
      
      String retval = port.register(u);
      
      System.out.println(retval);
      logger.debug(retval);
      
      return retval;
   }

   @Override
   public String login(UserCredentials userCredentials) {
      logger.debug("In login(): " + userCredentials);
      
      // calling web service
      com.chasemaster.ws.client.ChasemasterControllerImplService service = new com.chasemaster.ws.client.ChasemasterControllerImplService();
      com.chasemaster.ws.client.ChasemasterController port = service.getChasemasterControllerImplPort();
      com.chasemaster.ws.client.UserCredentials uc = new com.chasemaster.ws.client.UserCredentials();
      uc.setUsername(userCredentials.getUsername());
      uc.setPassword(userCredentials.getPassword());
      
      com.chasemaster.ws.client.Response retval = port.login(uc);
      
      System.out.println(retval.getMessageText() + ", " + retval.getMessageType());
      logger.debug(retval.getMessageText() + ", " + retval.getMessageType());
      
      return retval.getMessageText();
   }

   //
   // public Map<String, String> retrieveGeoData(String cityName) {
   // if (cityName == null || cityName.equals(""))
   // return null;
   //
   // Map<String, String> geoDataMap = null;
   //
   // try {
   // // Calling web service
   // URL url = new URL("http://api.geonames.org/searchJSON?q=" + cityName +
   // "&maxRows=1&username=delazic");
   // HttpURLConnection conn = (HttpURLConnection) url.openConnection();
   // conn.setRequestMethod("GET");
   // conn.setRequestProperty("Accept", "application/json");
   //
   // if (conn.getResponseCode() != 200) {
   // throw new RuntimeException("Failed : HTTP error code : " +
   // conn.getResponseCode());
   // }
   //
   // // TEST: START
   // // BufferedReader br = new BufferedReader(new
   // InputStreamReader(conn.getInputStream()));
   // // StringBuilder sb = new StringBuilder();
   // // String read = br.readLine();
   // // while (read != null) {
   // // // System.out.println(read);
   // // sb.append(read);
   // // read = br.readLine();
   // // }
   // // System.out.println(sb.toString());
   // // TEST: END
   //
   // geoDataMap = null; //JsonParser.parseGeoData(new BufferedReader(new
   // InputStreamReader((conn.getInputStream()))));
   //
   // conn.disconnect();
   // } catch (MalformedURLException e) {
   // e.printStackTrace();
   // } catch (IOException e) {
   // e.printStackTrace();
   // }
   //
   // /* Calling WS using library */
   // // WebService.setUserName("delazic");
   // // ToponymSearchCriteria searchCriteria = new ToponymSearchCriteria();
   // // searchCriteria.setQ(city);
   // // ToponymSearchResult searchResult;
   // // try {
   // // searchResult = WebService.search(searchCriteria);
   // //
   // // for (Toponym toponym : searchResult.getToponyms()) {
   // // System.out.println(toponym.getName() + ", " + toponym.getCountryName()
   // // + ", " + toponym.getLatitude() + ", " + toponym.getLongitude());
   // //
   // // Map<String, String> geoData = new HashMap<String, String>();
   // // geoData.put("name", toponym.getName());
   // // geoData.put("country", toponym.getCountryName());
   // // geoData.put("latitude", Double.toString(toponym.getLatitude()));
   // // geoData.put("longitude", Double.toString(toponym.getLongitude()));
   // // dataList.add(geoData);
   // // }
   // // } catch (Exception e) {
   // // System.out.println(e);
   // // }
   //
   // return geoDataMap;
   // }
   //
   // @Transactional
   // public void updateStatistics(String cityName) {
   // Query query = em.createQuery("FROM City c WHERE c.name = :name");
   // query.setParameter("name", cityName);
   // UserCredentials city = null;
   // try {
   // city = (UserCredentials) query.getSingleResult();
   // } catch (NoResultException nre) {
   // // Ignore this because as per your logic this is ok
   // System.out.println("No data in DB for " + cityName);
   // }
   //
   // if (city == null) {
   // System.out.println("Not found: " + cityName);
   // city = new UserCredentials(cityName, "");
   // } else {
   // System.out.println("Found: " + city);
   // }
   //
   // //city.setCounter(city.getCounter() + 1);
   // em.persist(city);
   // }
   //
   // @Transactional
   // public List<UserCredentials> retrieveStatistics() {
   // CriteriaBuilder cb = em.getCriteriaBuilder();
   // CriteriaQuery<UserCredentials> q = cb.createQuery(UserCredentials.class);
   // //q.from(City.class);
   //
   // Root<UserCredentials> r = q.from(UserCredentials.class);
   // q.select(r);
   // q.orderBy(cb.desc(r.get("counter")));
   //
   // return em.createQuery(q).getResultList();
   // }
}
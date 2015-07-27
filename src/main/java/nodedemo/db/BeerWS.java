package nodedemo.db;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@RequestScoped
@Path("/beers")
public class BeerWS {

	@Inject
	private DBConnection dbConnection;
	
	
	private DBCollection getBeerCollection() {
	       DB db = dbConnection.getDB();
	       DBCollection beerCollection = db.getCollection("beers");
	       return beerCollection;
	   }
	 
	   private Beer populateBeerInformation(DBObject dataValue) {
	       Beer theBeer = new Beer();
	       
	       theBeer.setName(dataValue.get("name").toString());
	       theBeer.setDescription(dataValue.get("name").toString());
	       theBeer.setId(dataValue.get("_id").toString());
	 
	       return theBeer;
	   }
	 
	   // get all of the beers
	   @GET()
	   @Produces("application/json")
	   public List<Beer> getAllBeers() {
	       ArrayList<Beer> allBeersList = new ArrayList<Beer>();
	 
	       DBCollection beers = this.getBeerCollection();
	       DBCursor cursor = beers.find();
	       try {
	           while (cursor.hasNext()) {
	               allBeersList.add(this.populateBeerInformation(cursor.next()));
	           }
	       } finally {
	           cursor.close();
	       }
	 
	       return allBeersList;
	   }
}

package cz.muni.fi.pa165.rest;
 
import java.net.URI;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
/**
 * Note: we are instantiating all the customers and adding them to 
 * an in-memory representation
 *
 */
@Path("/customers")
public class CustomersResource {
 
    private static SortedMap<Integer, CustomerResource> customerDB = new ConcurrentSkipListMap<Integer, CustomerResource>();
  
    private static AtomicInteger idCounter = new AtomicInteger(0);
    
    @Context
    private UriInfo context;
 
    public CustomersResource() {
           
        customerDB.put(idCounter.incrementAndGet(), new CustomerResource(Integer.toString(idCounter.get()), 
                "Isaac", "Newton", "Scientist", "Law of Universal Gravitation"));
        customerDB.put(idCounter.incrementAndGet(), new CustomerResource(Integer.toString(idCounter.get()), 
                "Johannes", "Gutenberg", "Inventor", "printing press"));
        customerDB.put(idCounter.incrementAndGet(), new CustomerResource(Integer.toString(idCounter.get()), 
                "Albert", "Einstein", "Scientist", "Theory of Relativity"));
        customerDB.put(idCounter.incrementAndGet(), new CustomerResource(Integer.toString(idCounter.get()), 
                "Enrico", "Fermi", "Physicist", "Development of Quantum Theory"));
        customerDB.put(idCounter.incrementAndGet(), new CustomerResource(Integer.toString(idCounter.get()), 
                "James", "Watt", "Inventor", "Steam Engine"));
        customerDB.put(idCounter.incrementAndGet(), new CustomerResource(Integer.toString(idCounter.get()), 
                "Guglielmo", "Marconi", "Inventor", "Radio"));
        customerDB.put(idCounter.incrementAndGet(), new CustomerResource(Integer.toString(idCounter.get()), 
                "Werner", "Eisenberg", "Physicist", "Quantum Mechanics"));
        
    }
 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getPlain() {
        StringBuilder returnString = new StringBuilder();
 
        for (CustomerResource customerResource : customerDB.values()) {
            returnString.append(customerResource);
            returnString.append(" ");
        }
 
        return returnString.toString();
    }
 
    @Path("{id}")
    public CustomerResource getCustomerResource(@PathParam("id") Integer id) {
        return customerDB.get(id);
    }
    
    @GET
    @Path("json/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerResource getJson(@PathParam("id") Integer id) {
        return customerDB.get(id);
    }
 
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String getCount() {
        return String.valueOf(customerDB.size());
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(CustomerResource customerResource) {
        customerDB.put(Integer.parseInt(customerResource.getId()), customerResource);
        System.out.println("Created customer " + customerResource.getId());
        return Response.created(URI.create(context.getAbsolutePath() + "/"+ customerResource.getId())).build();
    }
    
}
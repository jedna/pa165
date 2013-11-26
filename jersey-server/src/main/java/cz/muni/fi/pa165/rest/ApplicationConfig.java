package cz.muni.fi.pa165.rest;
 
import java.util.Set;
import javax.ws.rs.core.Application;
 
/**
 * @author xhrubes
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {
 
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(cz.muni.fi.pa165.rest.ServiceResource.class);
        resources.add(cz.muni.fi.pa165.rest.CustomerResource.class);
        resources.add(cz.muni.fi.pa165.rest.CustomersResource.class);
    }
}
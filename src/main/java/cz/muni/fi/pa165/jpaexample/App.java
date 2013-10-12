package cz.muni.fi.pa165.jpaexample;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Hello world!
 *
 */
public class App {

    public static String getCurrentNameInDb(EntityManagerFactory emf, Long id) {
        return emf.createEntityManager().find(Person.class, id).getName();
    }
    
    /**
     * Display persons
     *
     * @param persons list of persons
     */
    public static void dumpPersons(List<Person> persons)
    {
        for (Person person : persons){
            System.out.println(person.toString());
        }
    }
    
    /**
     * Dump persons using Criteria API
     *
     * @param em entity manager
     */
    public static void dumpUsingCriteria(EntityManager em)
    {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> person = cq.from(Person.class);
        cq.select(person);
        TypedQuery<Person> q = em.createQuery(cq);

        dumpPersons(q.getResultList());
    }
    
    /**
     * Dump persons using Typed query
     * 
     * @param em 
     */
    public static void dumpUsingTypedQuery(EntityManager em)
    {
        TypedQuery<Person> query1 = em.createQuery(
                "SELECT p FROM Person p", Person.class);
        dumpPersons(query1.getResultList());
    }
    
    /**
     * Find persons with any children
     * 
     * @param em
     * @return 
     */
    public static List<Person> findParents(EntityManager em)
    {
        return em.createNamedQuery("findParents", Person.class).getResultList();
    }
    
    /**
     * Find count of persons with no children
     * 
     * @param em
     * @return 
     */
    public static Long findCountOfNoParents(EntityManager em)
    {
        String q1 = "SELECT COUNT(p) FROM Person p WHERE p IN (SELECT p.father FROM Person p WHERE p.father IS NOT NULL)"; // Count of fathers
        String q2 = "SELECT COUNT(p) FROM Person p";
        
        return em.createQuery(q2, Long.class).getSingleResult() - em.createQuery(q1, Long.class).getSingleResult();
    }
    
    /**
     * Find persons by birth date
     * 
     * @param em
     * @param date
     * @return 
     */
    public static List<Person> findPersonsByBirth(EntityManager em, Date date)
    {
        String q = "SELECT p FROM Person p WHERE birthdate = :date";
        
        return em.createQuery(q, Person.class).setParameter("date", date, TemporalType.DATE).getResultList();
    }
    
    /**
     * Find persons by name and count their children
     * 
     * @param em
     * @param firstName
     * @return 
     */
    public static List<PersonTO> findPersonsByName(EntityManager em, String firstName)
    {
        String q = "SELECT p FROM Person p WHERE p.firstName = :firstName";
        List<Person> lp = em.createQuery(q, Person.class).setParameter("firstName", firstName).getResultList();
        
        List<PersonTO> lTO = new ArrayList<>();
        
        String q2 = "SELECT COUNT(p) FROM Person p WHERE p.father = :father";
        for (Person p : lp) {
            Long childrenCount = em.createQuery(q2, Long.class).setParameter("father", p).getSingleResult();
            
            PersonTO pTO = new PersonTO();
            pTO.setId(p.getId());
            pTO.setName(p.getFirstName());
            pTO.setChildrenCount(childrenCount);
            
            lTO.add(pTO);
        }
         
        return lTO;
    }

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TestPU");
        EntityManager em = emf.createEntityManager();

        Person person1 = new Person();
        person1.setFirstName("Paul");
        person1.setLastName("Smith");
        person1.setAddress("Botanická 68a, Brno");
        person1.setGender("male");
        person1.setBirthdate(new GregorianCalendar(1977, 10, 10).getTime());

        Person person2 = new Person();
        person2.setFirstName("Paul");
        person2.setLastName("Brown");
        person2.setAddress("Chvalovice 12");
        person2.setGender("male");
        person2.setBirthdate(new GregorianCalendar(1960, 1, 22).getTime());
        
        Trait t1 = new Trait();
        t1.setName("Laziness");
        t1.setPerson(person1);
        
        Trait t2 = new Trait();
        t2.setName("Willingness");
        t2.setPerson(person1);
        
        List<Trait> ts = new ArrayList<Trait>();
        ts.add(t1);
        ts.add(t2);
        
        person1.setTraits(ts);
        
        em.getTransaction().begin();
        em.persist(person1);
        em.persist(person2);
        em.getTransaction().commit();
        
        //dumpUsingCriteria(em);
        
        Person person3 = new Person();
        person3.setFirstName("John");
        person3.setLastName("Novacek");
        person3.setAddress("Prague");
        person3.setGender("male");
        person3.setBirthdate(new GregorianCalendar(1968, 4, 25).getTime());
        
        Person person4 = new Person();
        person4.setFirstName("Petr");
        person4.setLastName("Novacek");
        person4.setAddress("Prague");
        person4.setGender("male");
        person4.setBirthdate(new GregorianCalendar(1986, 4, 25).getTime());
        
        Person person5 = new Person();
        person5.setFirstName("Jane");
        person5.setLastName("Novacek");
        person5.setAddress("Prague");
        person5.setGender("female");
        person5.setBirthdate(new GregorianCalendar(1986, 4, 25).getTime());
        
        person4.setFather(person3);
        person5.setFather(person3);
        
        em.getTransaction().begin();
        em.persist(person3);
        em.persist(person4);
        em.persist(person5);
        em.getTransaction().commit();
        
        System.out.println("############## allRecords");
        dumpUsingCriteria(em);
        System.out.println("%%%%%%%%%% /allRecords");
        
        System.out.println("############## findParents");
        dumpPersons(findParents(em));
        System.out.println("%%%%%%%%%% /findParents");
        
        System.out.println("############## findCountOfNoParents");
        System.out.println(findCountOfNoParents(em));
        System.out.println("%%%%%%%%%% /findCountOfNoParents");
        
        System.out.println("############## findByBirthday");
        Date d = new GregorianCalendar(1960, 1, 22).getTime();
        dumpPersons(findPersonsByBirth(em, d));
        System.out.println("%%%%%%%%%% /findByBirthday");
        
        System.out.println("############## findByName");
        for (PersonTO pTO : findPersonsByName(em, "Paul")) {
            System.out.println(pTO);
        }
        System.out.println("%%%%%%%%%% /findByName");
        
        
        
        em.close();
        emf.close();

    }
}

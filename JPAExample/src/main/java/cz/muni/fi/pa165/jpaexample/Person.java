package cz.muni.fi.pa165.jpaexample;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Jan Hrubeš
 */
@Entity
@Table(name = "SimplePerson")
@NamedQuery(name = "findParents", query = "SELECT p FROM Person p WHERE p IN (SELECT p.father FROM Person p WHERE p.father IS NOT NULL)")
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "givenName", nullable = false)
    private String firstName;
    
    @Column(length = 50, nullable = false)
    private String lastName;
    
    @Column(name="address", nullable = false, length = 100)
    private String address;    
    
    @Column(name = "gender", nullable = false, length = 6)
    private String gender;
    
    @Column(name = "birthdate", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthdate;
    
    @ManyToOne
    @JoinColumn(name="parent_id", nullable = true)
    private Person father;
    
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Trait> traits;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getFather() {
        return father;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Trait> getTraits() {
        return traits;
    }

    public void setTraits(List<Trait> traits) {
        this.traits = traits;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        result = 31 * result + (father != null ? father.hashCode() : 0);
        result = 31 * result + (traits != null ? traits.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (address != null ? !address.equals(person.address) : person.address != null) return false;
        if (birthdate != null ? !birthdate.equals(person.birthdate) : person.birthdate != null) return false;
        if (father != null ? !father.equals(person.father) : person.father != null) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (gender != person.gender) return false;
        if (id != null ? !id.equals(person.id) : person.id != null) return false;
        if (traits != null ? !traits.equals(person.traits) : person.traits != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", gender=" + gender + ", birthdate=" + birthdate + ", father=" + father + ", traits=" + traits + '}';
    }
    
    
    
}

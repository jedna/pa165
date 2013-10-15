package cz.muni.fi.pa165.jpaexample;

/**
 *
 * @author Jan Hrubeš
 */

public class PersonTO {
    
    private Long id;
    private String name;
    private Long childrenCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(Long childrenCount) {
        this.childrenCount = childrenCount;
    }

    @Override
    public String toString() {
        return "PersonTO{" + "id=" + id + ", name=" + name + ", childrenCount=" + childrenCount + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.childrenCount != null ? this.childrenCount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonTO other = (PersonTO) obj;
        return true;
    }
    
}

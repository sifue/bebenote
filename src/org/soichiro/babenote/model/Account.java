package org.soichiro.babenote.model;

import java.io.Serializable;
import java.util.Date;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.User;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.Sort;

@Model(schemaVersion = 1)
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    private User owner;
    
    private Date lastLoginAt;
    
    private Long loginCount;
    
    private Date createdAt;
    
    private ModelRef<Baby> defaultBabyRef = new ModelRef<Baby>(Baby.class);

    @Attribute(persistent = false)
    private InverseModelListRef<Baby, Account> babyListRef =
    	new InverseModelListRef<Baby, Account>(Baby.class,
    			"accountRef", 
    			this,
    			new Sort("createdAt", SortDirection.DESCENDING));
    
    /**
     * Returns the key.
     *
     * @return the key
     */
    public Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key
     *            the key
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /**
     * Returns the version.
     *
     * @return the version
     */
    public Long getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version
     *            the version
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account other = (Account) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param lastLoginAt the lastLoginAt to set
	 */
	public void setLastLoginAt(Date lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	/**
	 * @return the lastLoginAt
	 */
	public Date getLastLoginAt() {
		return lastLoginAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the defaultBabyRef
	 */
	public ModelRef<Baby> getDefaultBabyRef() {
		return defaultBabyRef;
	}

	/**
	 * @return the babyListRef
	 */
	public InverseModelListRef<Baby, Account> getBabyListRef() {
		return babyListRef;
	}

	/**
	 * @param loginCount the loginCount to set
	 */
	public void setLoginCount(Long loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * @return the loginCount
	 */
	public Long getLoginCount() {
		return loginCount;
	}

}

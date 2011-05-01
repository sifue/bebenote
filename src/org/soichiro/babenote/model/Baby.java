package org.soichiro.babenote.model;

import java.io.Serializable;
import java.util.Date;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.InverseModelListRef;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;
import org.slim3.datastore.Sort;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.SortDirection;

@Model(schemaVersion = 1)
public class Baby implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;

    private String name;
    
    private Date birthday;
    
    /**
     * Timezone offset from GMT (min.)
     */
    private Integer timezoneOffset;
    
    private Date createdAt;
    
    private ModelRef<Account> accountRef = new ModelRef<Account>(Account.class);
    
    @Attribute(persistent = false)
    private InverseModelListRef<DayLog, Baby> dayLogListRef = 
    	new InverseModelListRef<DayLog, Baby>(DayLog.class,
    			"babyRef",
    			this,
    			new Sort("date",
    					SortDirection.DESCENDING));
    
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
        Baby other = (Baby) obj;
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
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param timezoneOffset the timezoneOffset to set
	 */
	public void setTimezoneOffset(Integer timezoneOffset) {
		this.timezoneOffset = timezoneOffset;
	}

	/**
	 * @return the timezoneOffset
	 */
	public Integer getTimezoneOffset() {
		return timezoneOffset;
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
	 * @return the accountRef
	 */
	public ModelRef<Account> getAccountRef() {
		return accountRef;
	}

	/**
	 * @return the dayLogListRef
	 */
	public InverseModelListRef<DayLog, Baby> getDayLogListRef() {
		return dayLogListRef;
	}
}

package org.soichiro.babenote.model;

import java.io.Serializable;

import com.google.appengine.api.datastore.Key;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

@Model(schemaVersion = 1)
public class HourLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Attribute(primaryKey = true)
    private Key key;

    @Attribute(version = true)
    private Long version;
    
    private Integer hour;
    
    private Boolean isNurse;
    
    private Boolean isMilk;
    
    private Boolean isCrap;
    
    private Boolean isPiss;
    
    private Boolean isSleep;
    
    private String memo;
    
    private ModelRef<DayLog> dayLogRef = new ModelRef<DayLog>(DayLog.class);

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
        HourLog other = (HourLog) obj;
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
	 * @param hour the hour to set
	 */
	public void setHour(Integer hour) {
		this.hour = hour;
	}

	/**
	 * @return the hour
	 */
	public Integer getHour() {
		return hour;
	}

	/**
	 * @param isNurse the isNurse to set
	 */
	public void setIsNurse(Boolean isNurse) {
		this.isNurse = isNurse;
	}

	/**
	 * @return the isNurse
	 */
	public Boolean getIsNurse() {
		return isNurse;
	}

	/**
	 * @param isMilk the isMilk to set
	 */
	public void setIsMilk(Boolean isMilk) {
		this.isMilk = isMilk;
	}

	/**
	 * @return the isMilk
	 */
	public Boolean getIsMilk() {
		return isMilk;
	}

	/**
	 * @param isCrap the isCrap to set
	 */
	public void setIsCrap(Boolean isCrap) {
		this.isCrap = isCrap;
	}

	/**
	 * @return the isCrap
	 */
	public Boolean getIsCrap() {
		return isCrap;
	}

	/**
	 * @param isPiss the isPiss to set
	 */
	public void setIsPiss(Boolean isPiss) {
		this.isPiss = isPiss;
	}

	/**
	 * @return the isPiss
	 */
	public Boolean getIsPiss() {
		return isPiss;
	}

	/**
	 * @param isSleep the isSleep to set
	 */
	public void setIsSleep(Boolean isSleep) {
		this.isSleep = isSleep;
	}

	/**
	 * @return the isSleep
	 */
	public Boolean getIsSleep() {
		return isSleep;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @return the dayLogRef
	 */
	public ModelRef<DayLog> getDayLogRef() {
		return dayLogRef;
	}
}

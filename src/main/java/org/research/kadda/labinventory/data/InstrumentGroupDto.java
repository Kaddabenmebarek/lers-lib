package org.research.kadda.labinventory.data;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class InstrumentGroupDto implements Serializable {
	
	private static final long serialVersionUID = 6612740926095862548L;

    int id;
    String name;
    
    public InstrumentGroupDto() {
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

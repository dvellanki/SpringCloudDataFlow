package vmware.dataflow.sendgrid;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

    private String name;
    private String locale;
    private String address;
    private String sourcesystem;

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty("sourcesystem")
    public void setSourceSystem(String sourcesystem) {
        this.sourcesystem = sourcesystem;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("sourcesystem")
    public String getSourceSystem() {
        return sourcesystem;
    }

    // Getters and Setters
}
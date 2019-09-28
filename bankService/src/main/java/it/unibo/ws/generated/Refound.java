package it.unibo.ws.generated;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sid" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "sid"
})
@XmlRootElement(name = "refound")
public class Refound {

    @XmlElement(required = true)
    protected String sid;

    /**
     * Gets the value of the sid property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSid() {
        return sid;
    }

    /**
     * Sets the value of the sid property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSid(String value) {
        this.sid = value;
    }

}

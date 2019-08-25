package it.unibo.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Status {
    ACCEPTED,
    NOT_ACCEPTED,
    ABORTED,
    AVAILABLE,
    NOT_AVAILABLE,
    PENDING
}


package ar.edu.utn.frre.dacs.ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ar.edu.utn.frre.dacs.ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Provincia_QNAME = new QName("http://server.ws.dacs.frre.utn.edu.ar/", "provincia");
    private final static QName _ListProvincias_QNAME = new QName("http://server.ws.dacs.frre.utn.edu.ar/", "listProvincias");
    private final static QName _ListProvinciasResponse_QNAME = new QName("http://server.ws.dacs.frre.utn.edu.ar/", "listProvinciasResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ar.edu.utn.frre.dacs.ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ListProvincias }
     * 
     */
    public ListProvincias createListProvincias() {
        return new ListProvincias();
    }

    /**
     * Create an instance of {@link ListProvinciasResponse }
     * 
     */
    public ListProvinciasResponse createListProvinciasResponse() {
        return new ListProvinciasResponse();
    }

    /**
     * Create an instance of {@link Provincia }
     * 
     */
    public Provincia createProvincia() {
        return new Provincia();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Provincia }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.dacs.frre.utn.edu.ar/", name = "provincia")
    public JAXBElement<Provincia> createProvincia(Provincia value) {
        return new JAXBElement<Provincia>(_Provincia_QNAME, Provincia.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListProvincias }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.dacs.frre.utn.edu.ar/", name = "listProvincias")
    public JAXBElement<ListProvincias> createListProvincias(ListProvincias value) {
        return new JAXBElement<ListProvincias>(_ListProvincias_QNAME, ListProvincias.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListProvinciasResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.ws.dacs.frre.utn.edu.ar/", name = "listProvinciasResponse")
    public JAXBElement<ListProvinciasResponse> createListProvinciasResponse(ListProvinciasResponse value) {
        return new JAXBElement<ListProvinciasResponse>(_ListProvinciasResponse_QNAME, ListProvinciasResponse.class, null, value);
    }

}

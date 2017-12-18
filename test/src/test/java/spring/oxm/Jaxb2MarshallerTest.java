package spring.oxm;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext-oxm.xml"})
public class Jaxb2MarshallerTest {
//    private static final Logger logger = LoggerFactory.getLogger(Jaxb2MarshallerTest.class);
//
//    @Autowired
//    private Jaxb2Marshaller jaxb2Marshaller;
//    
//    @Autowired
//    // @Qualifier(value="requestObjectFactory")
//    private ObjectFactory requestObjectFactory;
//
//    @Test
//    public void testMarshal() {
//	try {
//	    // javax.xml.bind.Marshaller marshaller = null;
//	    // package-info.java
//	    // xmlns = {
//	    // @javax.xml.bind.annotation.XmlNs(prefix="qm",namespaceURI="http://piao.qunar.com/2013/QMenpiaoRequestSchema"),
//	    // @javax.xml.bind.annotation.XmlNs(prefix="xsi",namespaceURI="http://www.w3.org/2001/XMLSchema-instance")
//	    // },
//	    // ObjectFactory requestObjectFactory = new ObjectFactory();
//	    Request request = requestObjectFactory.createRequest();
//	    RequestHeader header = requestObjectFactory.createRequestHeader();
//	    GetProductByDistributorRequestBody body = new GetProductByDistributorRequestBody();
//	    // body.setCurrentPage("1");
//	    // body.setPageSize("100");
//	    // body.setResourceId("");
//
//	    request.setHeader(header);
//	    request.setBody(body);
//	    
//	    // TODO use ByteArrayOutputStream os = ...;
//	    
//	    // TODO @XmlNullPolicy(emptyNodeRepresentsNull = true,
//	    // nullRepresentationForXml =
//	    // XmlMarshalNullRepresentation.EMPTY_NODE)
//
//	    Result result = null;;
//	    
//	    
//	    StringWriter writer = new StringWriter();
//	    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//	    writer.write(System.getProperty("line.separator"));
//	    result = new StreamResult(writer);
//	    jaxb2Marshaller.marshal(request, result);
//	    logger.error("{}{}",System.getProperty("line.separator"),writer);
//	    
//
////	    result = new StreamResult(System.err);
////	    jaxb2Marshaller.marshal(request, result);
//	} catch (Exception e) {
//	    logger.error("", e);
//	}
//
//    }

}

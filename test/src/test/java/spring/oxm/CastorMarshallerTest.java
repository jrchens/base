package spring.oxm;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext-oxm.xml"})
public class CastorMarshallerTest {
//    private static final Logger logger = LoggerFactory.getLogger(CastorMarshallerTest.class);
//
//    @Autowired
//    private CastorMarshaller castorMarshaller;
//
//    @Test
//    public void testMarshal() {
//	try {
//	    // javax.xml.bind.Marshaller marshaller = null;
////	    package-info.java
////	    xmlns = {
////			@javax.xml.bind.annotation.XmlNs(prefix="qm",namespaceURI="http://piao.qunar.com/2013/QMenpiaoRequestSchema"),
////			@javax.xml.bind.annotation.XmlNs(prefix="xsi",namespaceURI="http://www.w3.org/2001/XMLSchema-instance")
////		},
//	    ObjectFactory factory = new ObjectFactory();
//	    Request request = factory.createRequest();
//	    RequestHeader header = factory.createRequestHeader();
//	    GetProductByDistributorRequestBody body = factory.createGetProductByDistributorRequestBody();
//
//	    request.setHeader(header);
//	    request.setBody(body);
//
//	    
//	    // TODO remvoe xml declaration (<?xml version="1.0" encoding="UTF-8"?>)
//	    Result result = new StreamResult(System.err);
//	    
//	    castorMarshaller.marshal(request, result);
//	} catch (Exception e) {
//	    logger.error("", e);
//	}
//
//    }

}

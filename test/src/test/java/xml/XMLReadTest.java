package xml;

public class XMLReadTest {

//    private static final Logger logger = LoggerFactory.getLogger(XMLReadTest.class);

    // @Test
    // public void testReadFor() {
    // try {
    // JacksonXmlModule module = new JacksonXmlModule();
    // // and then configure, for example:
    // module.setDefaultUseWrapper(false);
    // XmlMapper xmlMapper = new XmlMapper(module);
    // RequestHeader header = new RequestHeader();
    // String xml = xmlMapper.writeValueAsString(header);
    // logger.info("xml: {}", xml);
    // } catch (Exception e) {
    // logger.error("", e);
    // }
    // }

//    @Test
//    public void testReadFor2() {
//        try {
//            CastorMarshaller cm = new CastorMarshaller();
//            RequestHeader header = new RequestHeader();
//            SAXResult result = new SAXResult();
//            cm.marshal(header, result);
//            logger.info("xml: {}", result);
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//    }
//
//    @Test
//    public void testReadFor3() {
//        try {
//            RequestHeader header = new RequestHeader();
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            XMLEncoder xmlEncoder = new XMLEncoder(baos);
//            xmlEncoder.writeObject(header);
//            xmlEncoder.close();
//
//            String xml = baos.toString();
//            logger.info("xml: {}", xml);
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//    }

//    @Test
//    public void testReadFor4() {
//        try {
//            ObjectFactory factory = new ObjectFactory();
//            Request request = factory.createRequest();
//            RequestHeader header = factory.createRequestHeader();
//            RequestBody body = factory.createGetProductByDistributorRequestBody();
//            // javax.xml.transform.TransformerFactory.newTransformer
//            request.setHeader(header);
//            request.setBody(body);
//
//            JAXBContext context = JAXBContext.newInstance(Request.class, RequestHeader.class, RequestBody.class,
//                    GetProductByDistributorRequestBody.class);
//            // JAXBContext context =
//            // JAXBContext.newInstance(request.getClass());
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setAdapter(new StringAdapter());
//
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.setProperty(Marshaller.JAXB_ENCODING, "utf-8");
//            // marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
//            // marshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders",
//            // "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//
//            StringWriter writer = new StringWriter();
//            marshaller.marshal(request, writer);
//            String result = writer.toString();
//
//            logger.info("xml: {}", result.replaceAll("xsi:nil=\"true\"", ""));
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//    }

    //

}

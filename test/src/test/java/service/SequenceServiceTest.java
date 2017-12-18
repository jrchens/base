package service;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext.xml"})
public class SequenceServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(SequenceServiceTest.class);
//    @Autowired
//    private SequenceService sequenceService;
//
//    @Test
//    public void testNext(){
//	Long seq = sequenceService.next();
//	logger.info("seq: {}",seq);
//    }
//
//    @Test
//    public void testBatchNext(){
//	int batch = 500;
//	for (int i = 0; i < batch; i++) {
//	    sequenceService.next();
//	}
//	// logger.info("count: {}",sequenceService.getCount() == 10);
//    }
}

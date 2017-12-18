package service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext.xml"})
public class SysGroupServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SysGroupServiceTest.class);
//    @Autowired
//    private SysGroupService sysGroupService;

//    @Test
//    public void testDeleteByPrimaryKey() {
    // Integer id = 1201;
    // sysGroupService.deleteByPrimaryKey(id);
//    }

    @Test
    public void testInsert() {
//        logger.info("test insert sys group");
//        SysGroup record = new SysGroup();
//      record.setGroupname("admin");
//      record.setViewname("Admin");
////      record.setGroupname("manager");
////      record.setViewname("Manager");
//        record.setDeleted(false);
//        record.setCrtime(DateTime.now().toDate());
//        record.setCruser("jrchens");
//
//        int aff = sysGroupService.insert(record);
//        logger.info("aff: {},id:{}", aff, record.getId());
    }

    @Test
    public void testSelectByPrimaryKey() {
//        Integer id = 1301;
//        SysGroup record = sysGroupService.selectByPrimaryKey(id);
//        logger.info("record: {}", record);
    }

    @Test
    public void testSelectAll() {
//        List<SysGroup> list = sysGroupService.selectAll();
//        for (SysGroup record : list) {
//            logger.info("record: {}", record);
//        }
    }

    @Test
    public void testUpdateByPrimaryKey() {
//        Integer id = 1301;
//        SysGroup record = sysGroupService.selectByPrimaryKey(id);
//
//        record.setDeleted(true);
//        record.setMduser("jrchens");
//        record.setMdtime(DateTime.now().toDate());
//        int aff = sysGroupService.updateByPrimaryKey(record);
//        logger.info("aff: {}", aff);
    }

    @Test
    public void testQuery() {
//        int pageNum = 1, pageSize = 10;
//        SysGroup query = new SysGroup();
//        query.setViewname("n");
//
//        PageInfo<SysGroup> pi = sysGroupService.query(query, pageNum, pageSize);
//
//        logger.info("pi: {}", pi);
//        List<SysGroup> list = pi.getList();
//        for (SysGroup record : list) {
//            logger.info("record: {}", record);
//        }
    }
}

package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath:applicationContext.xml"})
public class SysUserGroupServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SysUserGroupServiceTest.class);
//    @Autowired
//    private SysUserGroupService sysUserGroupService;

    @Test
    public void testDeleteByPrimaryKey() {
//        Integer id = 1201;
//        sysUserGroupService.deleteByPrimaryKey(id);
    }

    @Test
    public void testInsert() {
//        logger.info("test insert sys user group");
//        SysUserGroup record = new SysUserGroup();
//        record.setGroupname("admin");
//        record.setUsername("admin");
//
//        int aff = sysUserGroupService.insert(record);
//        logger.info("aff: {},id:{}", aff, record.getId());
//
//
//        record.setGroupname("admin");
//        record.setUsername("jrchens");
//        aff = sysUserGroupService.insert(record);
//        logger.info("aff: {},id:{}", aff, record.getId());

    }

//    @Test
//    public void testSelectByPrimaryKey() {
//        Integer id = 1601;
//        SysUserGroup record = sysUserGroupService.selectByPrimaryKey(id);
//        logger.info("record: {}", record);
//    }
//
//    @Test
//    public void testSelectAll() {
//        List<SysUserGroup> list = sysUserGroupService.selectAll();
//        for (SysUserGroup record : list) {
//            logger.info("record: {}", record);
//        }
//    }

//    @Test
//    public void testUpdateByPrimaryKey() {
//        Integer id = 1601;
//        SysUserGroup record = sysUserGroupService.selectByPrimaryKey(id);
//        record.setUsername("xxxx");
//        int aff = sysUserGroupService.updateByPrimaryKey(record);
//        logger.info("aff: {}", aff);
//    }

    @Test
    public void testQueryByGroupname() {
        // int pageNum = 1, pageSize = 10;
        // SysUserGroup query = new SysUserGroup();
        // query.setGroupname("admin");
        // query.setUsername("admin");

        // PageInfo<SysUserGroup> pi = sysUserGroupService.query(query, pageNum,
        // pageSize);
        // logger.info("pi: {}", pi);
        // List<SysUserGroup> list = pi.getList();
        // for (SysUserGroup record : list) {
        // logger.info("record: {}", record);
        // }

//        List<SysUser> sysUserList = sysUserGroupService.queryByGroupname("admin");
//        for (SysUser record : sysUserList) {
//            logger.info("record: {}", record);
//        }
    }

//    @Test
//    public void testQueryByUsername() {
//        List<SysGroup> sysGroupList = sysUserGroupService.queryByUsername("admin");
//        for (SysGroup record : sysGroupList) {
//            logger.info("record: {}", record);
//        }
//    }
//
//    @Test
//    public void testDeleteByUsername() {
//        int aff = sysUserGroupService.deleteByUsername("admin");
//        logger.info("aff: {}", aff);
//    }
//
//    @Test
//    public void testDeleteByGroupname() {
//        int aff = sysUserGroupService.deleteByGroupname("admin");
//        logger.info("aff: {}", aff);
//    }
}

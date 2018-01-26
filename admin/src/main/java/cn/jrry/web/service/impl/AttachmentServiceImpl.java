package cn.jrry.web.service.impl;

import cn.jrry.admin.service.ConfigService;
import cn.jrry.common.exception.ServiceException;
import cn.jrry.web.domain.Attachment;
import cn.jrry.web.mapper.AttachmentMapper;
import cn.jrry.web.service.AttachmentService;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentServiceImpl implements AttachmentService {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);
    private static final String UPLOAD_PATH = "34c10bf0-fe3b-4ff0-846d-66acf338e7fb";
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private ConfigService configService;

    @Override
    public int deleteByPrimaryKey(Long id) {
        try {
            return attachmentMapper.deleteByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("deleteByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int insert(MultipartFile[] files, String tag) {
        Attachment record = null;
        try {

            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            int aff = 0;
            for (MultipartFile file: files
                 ) {

                record = new Attachment();
                String originalFilename = file.getOriginalFilename();
                Long fileSize = file.getSize();
                String fileType = file.getContentType();
                String ext = Files.getFileExtension(originalFilename);
                String savePath = configService.selectByPrimaryKey(UPLOAD_PATH).getCfgValue();
                SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd");
                String pathSuffix = sdf.format(new Date(now.getTime()));
                if (StringUtils.hasText(tag)) {
                    pathSuffix = "/".concat(tag).concat(pathSuffix);
                }
                String fileName = String.valueOf(System.currentTimeMillis()).concat(RandomStringUtils.randomNumeric(6));
                if (StringUtils.hasText(ext)) {
                    fileName = fileName.concat(".").concat(ext);
                }

                File dest = new File(savePath.concat(pathSuffix), fileName);
                File dir = dest.getParentFile();
                if(!dir.exists()){
                    dir.mkdirs();
                }
                file.transferTo(dest);

                String fileSha1 = Hashing.sha1().hashBytes(Files.toByteArray(dest)).toString();
                Attachment clone = attachmentMapper.selectByFileSha1(fileSha1);

                record.setOriginalFileName(originalFilename);

                if (clone != null && clone.getId().longValue() > 0) {
                    record.setFileName(clone.getFileName());
                    record.setFileSize(clone.getFileSize());
                    record.setFileType(clone.getFileType());
                    record.setSavePath(clone.getSavePath());
                    record.setFileSha1(clone.getFileSha1());
                    record.setOwner(user);
                    record.setCruser(clone.getCruser());
                    record.setCrtime(clone.getCrtime());
                    record.setMduser(user);
                    record.setMdtime(now);
                } else {
                    record.setFileName(fileName);
                    record.setFileSize(fileSize);
                    record.setFileType(fileType);
                    record.setSavePath(pathSuffix);
                    record.setFileSha1(fileSha1);
                    record.setOwner(user);
                    record.setCruser(user);
                    record.setCrtime(now);
                }

                aff += attachmentMapper.insert(record);
            }

            return aff;
        } catch (Exception ex) {
            logger.error("insert error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public Attachment selectByPrimaryKey(Long id) {
        try {
            return attachmentMapper.selectByPrimaryKey(id);
        } catch (Exception ex) {
            logger.error("selectByPrimaryKey error {}{}{}", id, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Attachment> selectAll() {
        try {
            return attachmentMapper.selectAll();
        } catch (Exception ex) {
            logger.error("selectAll error {}", ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int updateByPrimaryKey(Attachment record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return attachmentMapper.updateByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("updateByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int removeByPrimaryKey(Attachment record) {
        try {
            String user = SecurityUtils.getSubject().getPrincipal().toString();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            record.setMduser(user);
            record.setMdtime(now);
            return attachmentMapper.removeByPrimaryKey(record);
        } catch (Exception ex) {
            logger.error("removeByPrimaryKey error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public int count(Map<String, Object> record) {
        try {
            return attachmentMapper.count(record);
        } catch (Exception ex) {
            logger.error("count error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }

    @Override
    public List<Attachment> select(Map<String, Object> record) {
        try {
            int page = Integer.parseInt(ObjectUtils.getDisplayString(record.get("page")));
            int rows = Integer.parseInt(ObjectUtils.getDisplayString(record.get("rows")));
            record.put("offset", (page - 1) * rows);
            return attachmentMapper.select(record);

        } catch (Exception ex) {
            logger.error("select error {}{}{}", record, System.lineSeparator(), ex);
            throw new ServiceException(ex.getCause());
        }
    }
}

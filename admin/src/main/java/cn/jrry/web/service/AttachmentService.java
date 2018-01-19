package cn.jrry.web.service;

import cn.jrry.web.domain.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AttachmentService {
    int deleteByPrimaryKey(Long id);

    int insert(MultipartFile[] file,String tag);

    Attachment selectByPrimaryKey(Long id);

    List<Attachment> selectAll();

    int updateByPrimaryKey(Attachment record);

    int removeByPrimaryKey(Attachment record);
    int count(Map<String, Object> record);
    List<Attachment> select(Map<String, Object> record);
}

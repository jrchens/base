package cn.jrry.web.mapper;

import cn.jrry.web.domain.Attachment;

import java.util.List;
import java.util.Map;

public interface AttachmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attachment record);

    Attachment selectByPrimaryKey(Long id);

    List<Attachment> selectAll();

    int updateByPrimaryKey(Attachment record);


    Attachment selectByFileSha1(String fileSha1);
    int removeByPrimaryKey(Attachment record);
    int count(Map<String, Object> record);
    List<Attachment> select(Map<String, Object> record);
}
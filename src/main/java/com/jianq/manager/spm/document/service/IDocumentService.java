package com.jianq.manager.spm.document.service;

import com.jianq.manager.spm.document.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Leo on 2017/9/6
 */
public interface IDocumentService {
    /**
     * 删
     *
     * @param id
     * @return
     */
    Integer deleteByPrimaryKey(Integer id);

    /**
     * 增
     *
     * @param document
     * @return
     */
    Integer insertDocument(Document document);

    /**
     * 查
     *
     * @param id
     * @return
     */
    Document selectByPrimaryKey(Integer id);

    /**
     * 改
     *
     * @param document
     * @return
     */
    Integer updateDocument(Document document);

    /**
     * 查询自己的首层文件夹
     *
     * @param document
     * @return
     */
    List<Document> listOwnDocument(Document document);

    /**
     * 查询
     *
     * @param fid
     * @return
     */
    List<Document> listByFid(Integer fid);

    /**
     * 创建文件夹
     *
     * @param name    名称
     * @param fid     父id
     * @param creater 创建人
     * @return
     */
    Integer saveDir(String name, Integer fid, Integer creater);

    /**
     * 保存文件到指定文件夹
     *
     * @param file
     * @param fid
     * @param creater 创建人
     * @return
     */
    Integer saveFile(MultipartFile file, Integer fid, Integer creater);
}

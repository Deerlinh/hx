package com.jianq.manager.spm.document.controller;

import com.jianq.manager.exception.NotFoundException;
import com.jianq.manager.spm.document.entity.Document;
import com.jianq.manager.spm.document.entity.DocumentLog;
import com.jianq.manager.spm.document.service.IDocumentLogService;
import com.jianq.manager.spm.document.service.IDocumentService;
import com.jianq.manager.spm.user.entity.User;
import com.jianq.manager.spm.user.service.UserService;
import com.jianq.manager.util.NumberUtil;
import com.jianq.manager.util.ReturnUtil;
import com.jianq.manager.util.StaticPropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

/**
 * @author Leo on 2017/9/6
 */
@RestController
@RequestMapping("/**/doc")
public class DocumentController {
    @Autowired
    private IDocumentService documentService;
    @Autowired
    private UserService userService;
    @Autowired
    private IDocumentLogService documentLogService;

    private static final String fileSharePath = StaticPropertiesUtil.getValue("file.hongxing.pic.path");

    /**
     * 获取首层文件夹
     *
     * @param document
     * @param userCode
     * @return
     */
    @RequestMapping("/list")
    public Object listDocument(Document document, String userCode) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        document.setFid(document.getFid() == null ? 0 : document.getFid());
        document.setCreater(user.getUserId());
        //获取自己的首层文件夹
        List<Document> documentList = documentService.listOwnDocument(document);
        return ReturnUtil.success(documentList);
    }

    /**
     * 查找文件(夹)
     *
     * @param fid
     * @param userCode
     * @return
     */
    @RequestMapping("/listByFid")
    public Object listByFid(Integer fid, String userCode) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("用户不存在");
        }
        List<Document> documents = documentService.listByFid(fid);
        return ReturnUtil.success(documents);
    }

    /**
     * 保存文件夹
     *
     * @param name
     * @param fid
     * @param userCode
     * @return
     */
    @RequestMapping("/saveDir")
    public Object saveDir(String name, Integer fid, String userCode) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("用户没用权限");
        }
        Integer documentId = documentService.saveDir(name, fid, user.getUserId());
        if (NumberUtil.isGreaterThan0(documentId)) {
            return ReturnUtil.success(documentId);
        }
        return ReturnUtil.fail("创建文件夹失败");
    }

    /**
     * 重命名
     *
     * @param document
     * @return
     */
    @RequestMapping(value = "/rename")
    public Object renameDocument(Document document, String userCode) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        Integer updateCount = documentService.updateDocument(document);
        if (NumberUtil.isGreaterThan0(updateCount)) {
            documentLogService.insertDocumentLog(new DocumentLog(document.getId(), "重命名为:" + document.getName(), user.getUserId()));
            return ReturnUtil.success("修改成功");
        }
        return ReturnUtil.fail("修改失败");
    }

    /**
     * 删除
     *
     * @param id 主键
     * @return
     */
    @RequestMapping(value = "/delete")
    public Object deleteDocument(Integer id, String userCode) {
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("请重新登录");
        }
        Integer deleteCount = documentService.deleteByPrimaryKey(id);
        if (NumberUtil.isGreaterThan0(deleteCount)) {
            documentLogService.insertDocumentLog(new DocumentLog(id, "删除文件", user.getUserId()));
            return ReturnUtil.success("删除成功");
        }
        return ReturnUtil.fail("删除失败");
    }

    /**
     * 文件上传
     *
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object uploadFile(MultipartFile file, Integer fid, String userCode) throws IOException {
        if (file.isEmpty()) {
            return ReturnUtil.fail("上传文件不存在");
        }
        User user = userService.getUser(userCode);
        if (user == null) {
            return ReturnUtil.fail("用户没用权限");
        }
        Integer documentId = documentService.saveFile(file, fid, user.getUserId());
        if (NumberUtil.isGreaterThan0(documentId)) {
            return ReturnUtil.success(documentId);
        }
        return ReturnUtil.fail("上传文件失败");
    }

    /**
     * 文件下载
     *
     * @param id
     * @return
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public Object downloadFile(Integer id) throws FileNotFoundException, UnsupportedEncodingException {
        Document document = documentService.selectByPrimaryKey(id);
        if (document == null) {
            throw new NotFoundException();
        }
        File file = new File(fileSharePath, document.getUuidname());
        if (!file.exists()) {
            throw new NotFoundException();
        }

        FileInputStream inputStream = new FileInputStream(file);
        String fileName = document.getName();
        fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(document.getContexttype())).contentLength(file.length())
                .header("Content-Disposition", "attachment;filename=\"" + fileName + "\"")
                .body(new InputStreamResource(inputStream));
    }
}

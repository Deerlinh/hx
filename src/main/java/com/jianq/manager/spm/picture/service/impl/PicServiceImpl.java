package com.jianq.manager.spm.picture.service.impl;

import com.jianq.manager.spm.constant.FileConstant;
import com.jianq.manager.spm.picture.dao.PicMapper;
import com.jianq.manager.spm.picture.entity.Pic;
import com.jianq.manager.spm.picture.service.PicService;
import com.jianq.manager.util.FileBase64ConvertUitl;
import com.jianq.manager.util.NumberUtil;
import com.jianq.manager.util.StaticPropertiesUtil;
import com.jianq.manager.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

/**
 * @author Leo on 2017/10/27
 */
@Service
public class PicServiceImpl implements PicService {
    private static final Log LOG = LogFactory.getLog(PicServiceImpl.class);
    private static final String PIC_PATH = StaticPropertiesUtil.getValue("file.hongxing.pic.path");

    @Autowired
    private PicMapper picMapper;

    @Override
    public Integer delete(Integer id) {
        Integer count = null;
        if (id != null) {
            try {
                count = picMapper.deleteByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 deletePic 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Integer insert(Pic pic) {
        Integer count = null;
        if (pic != null) {
            try {
                count = picMapper.insertSelective(pic);
            } catch (Exception e) {
                LOG.error("方法 insert 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public Pic selectById(Integer id) {
        Pic pic = null;
        if (id != null) {
            try {
                pic = picMapper.selectByPrimaryKey(id);
            } catch (Exception e) {
                LOG.error("方法 selectById 异常,{}", e);
            }
        }
        return pic;
    }

    @Override
    public Integer update(Pic pic) {
        Integer count = null;
        if (pic != null) {
            try {
                count = picMapper.updateByPrimaryKeySelective(pic);
            } catch (Exception e) {
                LOG.error("方法 updatePic 异常,{}", e);
            }
        }
        return count;
    }

    @Override
    public String saveBase64Pic(String base64pic) {
        String fileUrl = null;
        Integer count = null;
        if (StringUtil.isNotEmpty(base64pic)) {
            //如果没有则创建文件夹
            File dir = new File(PIC_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String extName = FileConstant.DEFAULT_PIC_SUFFIX;
            String fileName = UUID.randomUUID().toString() + extName;
            Pic pic = new Pic(PIC_PATH, fileName);
            try {
                FileBase64ConvertUitl.decoderBase64File(base64pic, PIC_PATH + fileName);
                count = this.insert(pic);
            } catch (Exception e) {
                LOG.error("方法 saveBase64Pic 异常,{}", e);
            }
            if (NumberUtil.isGreaterThan0(count)) {
                fileUrl = "/preview/" + fileName;
            }
        }
        return fileUrl;
    }
}

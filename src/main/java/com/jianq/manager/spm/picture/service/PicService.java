package com.jianq.manager.spm.picture.service;


import com.jianq.manager.spm.picture.entity.Pic;

/**
 * @author Leo on 2017/10/27
 */
public interface PicService {
    Integer delete(Integer id);

    Integer insert(Pic pic);

    Pic selectById(Integer id);

    Integer update(Pic pic);

    String saveBase64Pic(String base64pic);
}

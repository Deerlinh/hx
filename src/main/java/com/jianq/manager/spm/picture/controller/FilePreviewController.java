package com.jianq.manager.spm.picture.controller;

import com.jianq.manager.exception.NotFoundException;
import com.jianq.manager.util.StaticPropertiesUtil;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/**/preview")
public class FilePreviewController {

    private String filePath = StaticPropertiesUtil.getValue("file.hongxing.pic.path");

    @RequestMapping("/{fileName:.+}")
    public ResponseEntity<InputStreamResource> previewFile(@PathVariable String fileName) throws IOException {
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            throw new NotFoundException();
        }
        FileInputStream inputStream = new FileInputStream(file);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(new InputStreamResource(inputStream));
    }


}

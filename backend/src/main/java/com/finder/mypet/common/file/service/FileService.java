package com.finder.mypet.common.file.service;

import com.finder.mypet.common.advice.exception.CustomException;
import com.finder.mypet.common.response.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    @Value("${file.dir}")
    private String fileDir;

    public String save(MultipartFile multipartFile) {

        int extIdx = multipartFile.getOriginalFilename().lastIndexOf(".");
        String extension = multipartFile.getOriginalFilename().substring(extIdx+1);

        String filePath = fileDir + UUID.randomUUID() + "." + extension;

        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new CustomException(ResponseCode.FILE_CAN_NOT_SAVE);
        }
        return filePath;
    }

    public void delete(String filePath) {
        File file = new File(filePath);

        if(!file.exists()) return;

        if(!file.delete()) throw new CustomException(ResponseCode.FILE_CAN_NOT_DELETE);
    }
}

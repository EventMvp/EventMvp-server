package com.eventhive.eventHive.File.Service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.eventhive.eventHive.File.Service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class FileServiceImpl implements FileService {
    private final Cloudinary cloudinary;

    private static final List<String> ALLOWED_FILE_TYPES = Arrays.asList("image/jpeg", "image/png");

    public FileServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    private boolean isImageFile(MultipartFile file) {
        return ALLOWED_FILE_TYPES.contains(file.getContentType());
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        if (!isImageFile(file)){
            throw new IllegalArgumentException("File must be an image (JPEG, PNG, GIF)");
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }
}

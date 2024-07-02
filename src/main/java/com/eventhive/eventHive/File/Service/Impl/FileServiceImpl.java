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

    private String extractPublicId(String fileUrl) {
        // Example URL: "http://res.cloudinary.com/dvgaak6d6/image/upload/v1719932160/event-image/wthb6oqgwrhlzuwxd27n.png"
        String[] parts = fileUrl.split("/");
        // Join the parts after the 'upload/' part
        StringBuilder publicIdBuilder = new StringBuilder();
        boolean isPublicId = false;
        for (String part : parts) {
            if (isPublicId) {
                publicIdBuilder.append(part).append("/");
            }
            if (part.equals("upload")) {
                isPublicId = true;
            }
        }
        // Remove the trailing slash and file extension
        String publicIdWithExtension = publicIdBuilder.toString();
        if (publicIdWithExtension.endsWith("/")) {
            publicIdWithExtension = publicIdWithExtension.substring(0, publicIdWithExtension.length() - 1);
        }
        return publicIdWithExtension.split("\\.")[0];
    }

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        if (!isImageFile(file)){
            throw new IllegalArgumentException("File must be an image (JPEG, PNG, GIF)");
        }
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "event-image"));
        return uploadResult.get("url").toString();
    }

    @Override
    public void deleteFile(String fileUrl) throws IOException {
        String publicId = extractPublicId(fileUrl);
        cloudinary.uploader().destroy(publicId, ObjectUtils.asMap());

    }
}

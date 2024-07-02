package com.eventhive.eventHive.File.Controller;

import com.eventhive.eventHive.File.Service.FileService;
import com.eventhive.eventHive.Response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFileImage(@RequestParam("file") MultipartFile file){
        try {
            return Response.successResponse("Upload successfully", fileService.uploadFile(file));
        } catch (IOException e){
            return Response.failedResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Upload failed", null);
        }
    }
}

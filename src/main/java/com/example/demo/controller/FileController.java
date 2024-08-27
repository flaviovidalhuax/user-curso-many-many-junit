package com.example.demo.controller;

import com.example.demo.entity.FileEntity;
import com.example.demo.response.ResponceFile;
import com.example.demo.response.ResposeMessage;
import com.example.demo.servicio.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/fileManager")
public class FileController {
    @Autowired
    private FileService service;

    @PostMapping("/upload")
    public ResponseEntity<ResposeMessage> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        service.store(file);
        return ResponseEntity.status(HttpStatus.OK).body(new ResposeMessage("Archivo subido exitosamente"));
    }
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Long id) throws FileNotFoundException {
        FileEntity fileEntity=service.getAllFile(id).get();
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_TYPE, fileEntity.getType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=/"+fileEntity.getName()+"/" )
                .body(fileEntity.getData());

    }


    @GetMapping("/file")
    public ResponseEntity<List<ResponceFile>> getListFile(){
        List<ResponceFile> fileList=service.getAllFile();
        return ResponseEntity.status(HttpStatus.OK).body(fileList);
    }

}

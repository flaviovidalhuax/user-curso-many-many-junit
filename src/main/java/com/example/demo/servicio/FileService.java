package com.example.demo.servicio;

import com.example.demo.entity.FileEntity;
import com.example.demo.entity.Usuario;
import com.example.demo.repository.FileRepository;
import com.example.demo.response.ResponceFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
   public FileEntity store(MultipartFile file) throws IOException{
        String filename= StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileEntity =FileEntity.builder()
                .name(filename)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();
        return fileRepository.save(fileEntity);
    }

    public Optional<FileEntity> getAllFile(Long id) throws FileNotFoundException {
       Optional<FileEntity> file =fileRepository.findById(id);
       if (file.isPresent()){
           return file;
       }
       throw new FileNotFoundException();
    }
    public List<ResponceFile> getAllFile(){
        return fileRepository.findAll().stream()
               .map((FileEntity DBfile) -> {
                   String fileDownloadUry = ServletUriComponentsBuilder.fromCurrentContextPath()
                           .path("api/fileManager/file/")
                           .path(DBfile.getId().toString())
                           .toUriString();

                   return ResponceFile.builder()
                           .name(DBfile.getName())
                           .url(fileDownloadUry)
                           .type(DBfile.getType())
                           .size(DBfile.getData().length).build();

               }).collect(Collectors.toList());
    }
}

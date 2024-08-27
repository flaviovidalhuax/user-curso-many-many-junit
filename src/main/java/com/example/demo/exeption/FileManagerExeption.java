package com.example.demo.exeption;

import com.example.demo.response.ResponceFile;
import com.example.demo.response.ResposeMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
public class FileManagerExeption extends ResponseEntityExceptionHandler  {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeExeption(MaxUploadSizeExceededException exception) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResposeMessage("over size: muy grande exede los 2 MB"));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ResposeMessage> handleFindNotFoundExeption( FileNotFoundException exp){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResposeMessage("el archivo solicitado no se encontro o no es disponible"));
    }

}

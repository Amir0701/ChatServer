package com.lambakean.representation.controller;

import com.lambakean.data.model.Image;
import com.lambakean.domain.service.ImageService;
import com.lambakean.representation.dto.ImageDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    private ImageService imageService;

    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ImageDto> create(@RequestBody ImageDto imageDto){
        return ResponseEntity.ok(imageService.create(imageDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageDto> getById(@RequestParam Long id){
        return ResponseEntity.ok(imageService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImageDto> delete(@RequestParam Long id){
        return ResponseEntity.ok(imageService.delete(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@RequestParam Long id){
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        ImageDto imageDto = imageService.getById(id);
        File file = new File(imageDto.getUrl());
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
            System.out.println("DDDDD");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}

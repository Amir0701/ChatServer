package com.lambakean.representation.controller;

import com.lambakean.domain.service.ImageService;
import com.lambakean.representation.dto.ImageDto;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

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
    public ResponseEntity<ImageDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(imageService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ImageDto> delete(@PathVariable Long id){
        return ResponseEntity.ok(imageService.delete(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Object> download(@PathVariable Long id){
        try {
            ImageDto imageDto = imageService.getById(id);

            HttpHeaders header = new HttpHeaders();

            String fileName = Arrays.stream(imageDto.getUrl().split("/")).reduce((__, cur) -> cur).get();
            Optional<String> ext = Arrays.stream(fileName.split("\\.")).reduce((__, cur) -> cur);
            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

            if (ext.isPresent()) {
                switch (ext.get().toLowerCase()) {
                    case "png": { mediaType = MediaType.IMAGE_PNG; break; }
                    case "gif": { mediaType = MediaType.IMAGE_GIF; break; }
                    case "jpeg": { mediaType = MediaType.IMAGE_JPEG; break; }
                }
            }


            header.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fileName));
            header.add("Cache-Control", "no-cache, no-store, must-revalidate");
            header.add("Pragma", "no-cache");
            header.add("Expires", "0");

            File file = new File(imageDto.getUrl());
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

            return ResponseEntity.ok()
                    .headers(header)
                    .contentLength(file.length())
                    .contentType(mediaType)
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

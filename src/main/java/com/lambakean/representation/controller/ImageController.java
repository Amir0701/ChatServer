package com.lambakean.representation.controller;

import com.lambakean.data.model.Image;
import com.lambakean.domain.service.ImageService;
import com.lambakean.representation.dto.ImageDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

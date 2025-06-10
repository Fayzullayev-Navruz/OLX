package uz.pdp.securitytest.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.Attachment;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.payload.CategoryAndChildCategoryRecord;
import uz.pdp.securitytest.payload.CategoryDto;
import uz.pdp.securitytest.repository.AttachmentRepository;
import uz.pdp.securitytest.repository.CategoryRepository;
import uz.pdp.securitytest.service.CategoryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@MultipartConfig
@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:63342")
public class CategoryController {

    private final CategoryService categoryService;


    public CategoryController( CategoryService categoryService) {

        this.categoryService = categoryService;
    }
    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CREATE_CATEGORY.name(), " +
            "T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")

    @PostMapping("/upload-image")
    public ApiResponse uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        Attachment update = categoryService.update(image);
        return new ApiResponse<>(HttpStatus.OK.toString(), update);
        }

    @GetMapping
    public ApiResponse read() {
        List<CategoryDto> read = categoryService.read();
        return new ApiResponse<>(HttpStatus.OK.toString(), read);
    }

    @GetMapping("/string")
    public ApiResponse readString() {
        Collection<CategoryAndChildCategoryRecord> categoryAndChildCategoryRecords = categoryService.readString();
        return new ApiResponse<>(HttpStatus.OK.toString(), categoryAndChildCategoryRecords);
    }
    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CREATE_CATEGORY.name(), " +
            "T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")

    @PostMapping("/create")
    public ApiResponse create(@RequestParam("name") String name,
                                              @RequestParam(value = "image",required = false) MultipartFile image) throws IOException {
        CategoryDto categoryDto = categoryService.create(name, image);
        return new ApiResponse<>(HttpStatus.CREATED.toString(), categoryDto);
    }
    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).EDIT_CATEGORY.name(), " +
            "T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")

    @PutMapping("/update/{id}")
    public ApiResponse edit(@PathVariable Integer id,
                                            @RequestParam("name") String name,
                                            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        CategoryDto edit = categoryService.edit(id, name, image);
        return new ApiResponse<>(HttpStatus.OK.toString(), edit);
    }
    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name(), " +
            "T(uz.pdp.securitytest.enums.PermissionEnum).DELETE_CATEGORY.name())")

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        CategoryDto delete = categoryService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.toString(), delete);
    }
}

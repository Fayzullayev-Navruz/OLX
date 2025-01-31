package uz.pdp.securitytest.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.securitytest.entity.Attachment;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.entity.Product;
import uz.pdp.securitytest.payload.CategoryDto;
import uz.pdp.securitytest.payload.ProductDTO;
import uz.pdp.securitytest.repository.AttachmentRepository;
import uz.pdp.securitytest.repository.CategoryRepository;
import uz.pdp.securitytest.repository.ProductRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@MultipartConfig
@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:63342")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final String imagePath = "C:\\Users\\User\\IdeaProjects\\home123456\\";

    public CategoryController(CategoryRepository categoryRepository, AttachmentRepository attachmentRepository) {
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
    }

//    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).VIEW_CATEGORY.name(), T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping
    public List<CategoryDto>read() {
        ModelAndView modelAndView = new ModelAndView("category");
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDto> categoriesDto = new ArrayList<>();
        for (Category category : categories) {
            String filePath = category.getAttachment().getId().toString();
//            filePath=filePath.replaceAll(imagePath,"");
//            filePath=filePath.replaceAll("images/","");
            CategoryDto categoryDto = new CategoryDto(category.getId(), category.getName(), filePath);
            categoriesDto.add(categoryDto);

        }

        return categoriesDto;
    }

//    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CREATE_CATEGORY.name(), T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @PostMapping("/create")
    public void create(@RequestParam("name") String name,
                       @RequestParam("image") MultipartFile image) throws IOException {
        Category category = new Category();
        category.setName(name);
        if (!image.isEmpty()) {
            String contentType = image.getContentType();
            String originalFilename = image.getOriginalFilename();
            String filePath = imagePath + UUID.randomUUID() +
                    ".png";

            Files.copy(image.getInputStream(), Path.of(filePath));
            Attachment attachment = attachmentRepository.save(new Attachment(originalFilename, contentType, filePath, image.getSize()));
            category.setAttachment(attachment);
        }

        categoryRepository.save(category);
     read();
    }

  //  @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).EDIT_CATEGORY.name(), T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @PostMapping("/update")
    public void edit(@RequestBody CategoryDto categoryDto,
                       @RequestParam(required = false) MultipartFile image) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getId());
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDto.getName());


            if (image != null && !image.isEmpty()) {
                String contentType = image.getContentType();
                String originalFilename = image.getOriginalFilename();
                String filePath = imagePath + UUID.randomUUID() + "_" + originalFilename;

                Files.copy(image.getInputStream(), Path.of(filePath));
                Attachment attachment = attachmentRepository.save(new Attachment(originalFilename, contentType, filePath, image.getSize()));
                category.setAttachment(attachment);
            }

            categoryRepository.save(category);
        }

        read();
    }

   // @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).DELETE_CATEGORY.name(), T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
        read();
    }
}

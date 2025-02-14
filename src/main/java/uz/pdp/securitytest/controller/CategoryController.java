package uz.pdp.securitytest.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.securitytest.entity.Attachment;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.payload.CategoryDto;
import uz.pdp.securitytest.repository.AttachmentRepository;
import uz.pdp.securitytest.repository.CategoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    private final String imagePath = "D:\\IdeaProjects\\OLX_updated\\images";

    public CategoryController(CategoryRepository categoryRepository, AttachmentRepository attachmentRepository) {
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Attachment> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        String fileName = UUID.randomUUID() + ".png";
        String filePath = imagePath + fileName;

        Files.copy(image.getInputStream(), Path.of(filePath));

        Attachment attachment = attachmentRepository.save(new Attachment(fileName, image.getContentType(), filePath, image.getSize()));

        return ResponseEntity.ok(attachment);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> read() {
        List<CategoryDto> categoriesDto = categoryRepository.findAll().stream().map(category -> {
            String imageUrl = "http://localhost:8080/images/" + category.getAttachment().getFilePath();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getId());
            categoryDto.setAttachmentId(category.getAttachment().getId());
            categoryDto.setImageUrl(imageUrl);
            return categoryDto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(categoriesDto);
    }

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@RequestParam("name") String name,
                                              @RequestParam("image") MultipartFile image) throws IOException {
        Category category = new Category();
        category.setName(name);

        Attachment attachment = null;
        if (!image.isEmpty()) {
            String fileName = UUID.randomUUID() + ".png";
            String filePath = imagePath + fileName;
            Files.copy(image.getInputStream(), Path.of(filePath));
            attachment = attachmentRepository.save(new Attachment(fileName, image.getContentType(), filePath, image.getSize()));
            category.setAttachment(attachment);
        }

        categoryRepository.save(category);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        categoryDto.setAttachmentId(attachment != null ? attachment.getId() : null);
        categoryDto.setImageUrl(attachment != null ? "http://localhost:8080/images/" + attachment.getFilePath() : null);

        return ResponseEntity.ok(categoryDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryDto> edit(@PathVariable Integer id,
                                            @RequestParam("name") String name,
                                            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(name);

            Attachment attachment = null;
            if (image != null && !image.isEmpty()) {
                String fileName = UUID.randomUUID() + ".png";
                String filePath = imagePath + fileName;
                Files.copy(image.getInputStream(), Path.of(filePath));
                attachment = attachmentRepository.save(new Attachment(fileName, image.getContentType(), filePath, image.getSize()));
                category.setAttachment(attachment);
            }

            categoryRepository.save(category);

            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setAttachmentId(attachment != null ? attachment.getId() : category.getAttachment().getId());
            categoryDto.setImageUrl(attachment != null ? "http://localhost:8080/images/" + attachment.getFilePath() : "http://localhost:8080/images/" + category.getAttachment().getFilePath());

            return ResponseEntity.ok(categoryDto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        categoryRepository.findById(id).ifPresent(categoryRepository::delete);
        return ResponseEntity.ok("Category deleted successfully!");
    }
}

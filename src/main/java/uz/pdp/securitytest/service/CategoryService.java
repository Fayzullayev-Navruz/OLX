package uz.pdp.securitytest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.Attachment;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.payload.CategoryAndChildCategoryRecord;
import uz.pdp.securitytest.payload.CategoryDto;
import uz.pdp.securitytest.repository.AttachmentRepository;
import uz.pdp.securitytest.repository.CategoryRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final AttachmentRepository attachmentRepository;
    private final CategoryRepository categoryRepository;
    private final String imagePath = "C:\\Users\\User\\IdeaProjects\\OLXk\\images\\";

    public CategoryService(AttachmentRepository attachmentRepository, CategoryRepository categoryRepository) {
        this.attachmentRepository = attachmentRepository;
        this.categoryRepository = categoryRepository;
    }

    public Attachment update(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new RuntimeException("image is null");
        }

        String fileName = UUID.randomUUID() + ".png";
        String filePath = imagePath + fileName;

        Files.copy(image.getInputStream(), Path.of(filePath));

        Attachment attachment = attachmentRepository.save(new Attachment(fileName, image.getContentType(), filePath, image.getSize()));
        return attachmentRepository.save(attachment);

    }

    public List<CategoryDto> read() {
        List<CategoryDto> categoriesDto = categoryRepository.findAll().stream().map(category -> {
            String imageUrl = "http://localhost:8080/images/" + category.getAttachment().getFilePath();
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(category.getName());
            categoryDto.setId(category.getId());
            categoryDto.setAttachmentId(category.getAttachment().getId());
            categoryDto.setImageUrl(imageUrl);
            return categoryDto;
        }).collect(Collectors.toList());

        return categoriesDto;
    }

    public Collection<CategoryAndChildCategoryRecord> readString() {
        ArrayList<CategoryAndChildCategoryRecord> categoryAndChildCategoryRecords = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            categoryAndChildCategoryRecords.add(new CategoryAndChildCategoryRecord(category.getId(), category.getName()));
        });
        return categoryAndChildCategoryRecords;
    }

    public CategoryDto create(String name, MultipartFile image) throws IOException {
        Category category = new Category();
        category.setName(name);

        Attachment attachment = null;
        if (image!=null&&!image.isEmpty()) {
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

        return categoryDto;
    }

    public CategoryDto edit(Integer id, String name, MultipartFile image) throws IOException {
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

            return categoryDto;
        }

        else throw new RuntimeException("category not found");
    }

    public CategoryDto delete(Integer id) {
        Optional<Category> byId = categoryRepository.findById(id);
        if (byId.isPresent()) {
            Category category = byId.get();
            categoryRepository.delete(byId.get());
            CategoryDto categoryDto = new CategoryDto();
           categoryDto.setId(category.getId());
           categoryDto.setName(category.getName());
           categoryDto.setAttachmentId(category.getAttachment().getId());
           categoryDto.setImageUrl(category.getAttachment().getFilePath());
           return categoryDto;
        }
        else throw new RuntimeException("category not found");
    }
}

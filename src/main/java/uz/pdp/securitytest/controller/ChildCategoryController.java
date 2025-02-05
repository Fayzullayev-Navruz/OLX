package uz.pdp.securitytest.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.entity.ChildCategory;
import uz.pdp.securitytest.payload.ChildCategoryDto;
import uz.pdp.securitytest.repository.CategoryRepository;
import uz.pdp.securitytest.repository.ChildCategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@MultipartConfig
@RestController
@RequestMapping("/child_category")
@CrossOrigin(origins = "http://localhost:63342")
public class ChildCategoryController {
    private final ChildCategoryRepository childCategoryRepository;
    private final CategoryRepository categoryRepository;

    public ChildCategoryController(ChildCategoryRepository childCategoryRepository, CategoryRepository categoryRepository) {
        this.childCategoryRepository = childCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/{Id}")
    public ResponseEntity<List<ChildCategoryDto>> read(@PathVariable Integer Id) {
        List<ChildCategory> byCategoryId = childCategoryRepository.findByCategory_Id(Id);
        ArrayList<ChildCategoryDto> childCategoryDtos = new ArrayList<>();
        for (ChildCategory childCategory : byCategoryId) {
            ChildCategoryDto childCategoryDto = new ChildCategoryDto();
            childCategoryDto.setId(childCategory.getId());
            childCategoryDto.setName(childCategory.getName());
            childCategoryDto.setCategoryId(childCategory.getCategory().getId());
            childCategoryDtos.add(childCategoryDto);
        }

        return ResponseEntity.ok(childCategoryDtos);
    }

    @PostMapping("/create")
    public ResponseEntity<ChildCategoryDto> create(@RequestParam("name") String name, @RequestParam("parentId") Integer parentId) {
        Optional<Category> categoryOpt = categoryRepository.findById(parentId);
        if (categoryOpt.isPresent()) {
            ChildCategory childCategory = new ChildCategory();
            childCategory.setName(name);
            childCategory.setCategory(categoryOpt.get());
            childCategoryRepository.save(childCategory);

            ChildCategoryDto dto = new ChildCategoryDto();
            dto.setId(childCategory.getId());
            dto.setName(childCategory.getName());
            dto.setCategoryId(parentId);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChildCategoryDto> update(@PathVariable Integer id, @RequestParam("name") String name, @RequestParam("parentId") Integer parentId) {
        Optional<ChildCategory> optionalCategory = childCategoryRepository.findById(id);
        Optional<Category> parentCategory = categoryRepository.findById(parentId);

        if (optionalCategory.isPresent() && parentCategory.isPresent()) {
            ChildCategory childCategory = optionalCategory.get();
            childCategory.setName(name);
            childCategory.setCategory(parentCategory.get());
            childCategoryRepository.save(childCategory);

            ChildCategoryDto dto = new ChildCategoryDto();
            dto.setId(childCategory.getId());
            dto.setName(childCategory.getName());
            dto.setCategoryId(parentId);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (childCategoryRepository.existsById(id)) {
            childCategoryRepository.deleteById(id);
            return ResponseEntity.ok("Category deleted successfully!");
        }
        return ResponseEntity.notFound().build();
    }
}


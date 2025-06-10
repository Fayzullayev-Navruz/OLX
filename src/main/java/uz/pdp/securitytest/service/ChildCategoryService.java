package uz.pdp.securitytest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.entity.ChildCategory;
import uz.pdp.securitytest.payload.CategoryAndChildCategoryRecord;
import uz.pdp.securitytest.payload.ChildCategoryDto;
import uz.pdp.securitytest.repository.CategoryRepository;
import uz.pdp.securitytest.repository.ChildCategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChildCategoryService {
    private final  ChildCategoryRepository childCategoryRepository;
    private final CategoryRepository categoryRepository;

    public ChildCategoryService(ChildCategoryRepository childCategoryRepository, CategoryRepository categoryRepository) {
        this.childCategoryRepository = childCategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ChildCategoryDto> read(Integer Id) {
        List<ChildCategory> byCategoryId = childCategoryRepository.findByCategory_Id(Id);
        ArrayList<ChildCategoryDto> childCategoryDtos = new ArrayList<>();
        for (ChildCategory childCategory : byCategoryId) {
            ChildCategoryDto childCategoryDto = new ChildCategoryDto();
            childCategoryDto.setId(childCategory.getId());
            childCategoryDto.setName(childCategory.getName());
            childCategoryDto.setCategoryId(childCategory.getCategory().getId());
            childCategoryDtos.add(childCategoryDto);
        }

        return childCategoryDtos;
    }

    public List<CategoryAndChildCategoryRecord> readString(Integer categoryValue) {
        List<CategoryAndChildCategoryRecord> list = new ArrayList<>();
        List<ChildCategory> byCategoryId = childCategoryRepository.findByCategory_Id(categoryValue);
        for (ChildCategory childCategory : byCategoryId) {
            list.add(new CategoryAndChildCategoryRecord(childCategory.getId(), childCategory.getName()));
        }
        return list;
    }

    public ChildCategoryDto create(String name, Integer parentId) {
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
         return dto;
        }
    else throw new RuntimeException("Category not found");
    }

    public ChildCategoryDto update(Integer id, String name, Integer parentId) {
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
        return dto;
        }
     else throw new RuntimeException("Category or childCategory not found");
    }

    public ChildCategoryDto delete(Integer id) {
        if (childCategoryRepository.existsById(id)) {
            Optional<ChildCategory> byId = childCategoryRepository.findById(id);
            if (byId.isEmpty()) {
                throw new RuntimeException("Category not found");
            }
            ChildCategory childCategory = byId.get();
            ChildCategoryDto childCategoryDto = new ChildCategoryDto();
            childCategoryDto.setId(id);
            childCategoryDto.setCategoryId(childCategory.getCategory().getId());
            childCategoryDto.setName(childCategory.getName());
            childCategoryRepository.deleteById(id);
            return childCategoryDto;

        }
  else throw new RuntimeException("childCategory not found");
    }
}

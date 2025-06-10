package uz.pdp.securitytest.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.entity.ChildCategory;
import uz.pdp.securitytest.payload.CategoryAndChildCategoryRecord;
import uz.pdp.securitytest.payload.ChildCategoryDto;
import uz.pdp.securitytest.repository.CategoryRepository;
import uz.pdp.securitytest.repository.ChildCategoryRepository;
import uz.pdp.securitytest.service.ChildCategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@MultipartConfig
@RestController
@RequestMapping("/child_category")
@CrossOrigin(origins = "http://localhost:63342")
public class ChildCategoryController {
    private final ChildCategoryService childCategoryService;

    public ChildCategoryController(ChildCategoryService childCategoryService) {
        this.childCategoryService = childCategoryService;
    }

    @GetMapping("/{Id}")
    public ApiResponse read(@PathVariable Integer Id) {
        List<ChildCategoryDto> read = childCategoryService.read(Id);
        return new ApiResponse<>(HttpStatus.OK.toString(), read);
    }

    @GetMapping("/stringchild/{categoryValue}")
    public ApiResponse readString(@PathVariable Integer categoryValue) {

        List<CategoryAndChildCategoryRecord> categoryAndChildCategoryRecords = childCategoryService.readString(categoryValue);
        return new ApiResponse<>(HttpStatus.OK.toString(),categoryAndChildCategoryRecords);
    }


    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @PostMapping("/create")
    public ApiResponse create(@RequestParam("name") String name, @RequestParam("parentId") Integer parentId) {
        ChildCategoryDto childCategoryDto = childCategoryService.create(name, parentId);
        return new ApiResponse<>(HttpStatus.CREATED.toString(), childCategoryDto);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable Integer id, @RequestParam("name") String name, @RequestParam("parentId") Integer parentId) {
        ChildCategoryDto update = childCategoryService.update(id, name, parentId);
        return new ApiResponse<>(HttpStatus.OK.toString(), update);
    }

    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name())")
    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        ChildCategoryDto delete = childCategoryService.delete(id);
        return new ApiResponse<>(HttpStatus.OK.toString(), delete);
    }
}


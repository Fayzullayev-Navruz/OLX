package uz.pdp.securitytest.controller;

import jakarta.servlet.annotation.HttpMethodConstraint;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.*;
import uz.pdp.securitytest.payload.ProductDTO;
import uz.pdp.securitytest.repository.*;
import uz.pdp.securitytest.service.ProductService;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:63342")

public class ProductController {
    private final ProductService productService;
    private final String path = "C:\\Users\\User\\IdeaProjects\\OLXk\\images\\";

    public ProductController(CategoryRepository categoryRepository, ChildCategoryRepository childCategoryRepository, AttachmentRepository attachmentRepository, ManagerRepository managerRepository, UncheckADDSRepository uncheckADDSRepository, ProductService productService) {
        this.productService = productService;
    }

//    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name(), " +
//            "T(uz.pdp.securitytest.enums.PermissionEnum).VIEW_PRODUCT.name())")
    @GetMapping("/{childCategoryId}")
    public ApiResponse read(@PathVariable Integer childCategoryId) {

        List<ProductDTO> productDTOS = productService.getProductDTOS(childCategoryId);

        return new ApiResponse<>(HttpStatus.OK.toString(), productDTOS);
    }


    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name(), " +
            "T(uz.pdp.securitytest.enums.PermissionEnum).CREATE_PRODUCT.name())")
    @PostMapping("/add")
    public ApiResponse create(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("category") Integer categoryId,
            @RequestParam("childCategory") Integer childCategoryId,
            @RequestParam(value = "image",required = false) MultipartFile image
    ) throws IOException {
        ProductDTO productDTO = productService.create(name, description, price, categoryId, childCategoryId, image);
        return new ApiResponse<>(HttpStatus.CREATED.toString(), productDTO);
    }


    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name(), " +
            "T(uz.pdp.securitytest.enums.PermissionEnum).EDIT_PRODUCT.name())")
    @PostMapping("/update")
    public ApiResponse update(@RequestParam Integer id, @RequestParam String name, @RequestParam Double price, @RequestParam(required = false) MultipartFile image) {
        ProductDTO update = productService.update(id, name, price, image);
        return new ApiResponse<>(HttpStatus.OK.toString(), update);
    }

    @PreAuthorize("hasAnyAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CRUD_ALL.name(), " +
            "T(uz.pdp.securitytest.enums.PermissionEnum).DELETE_PRODUCT.name())")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        productService.delete(id);
    }

    @GetMapping("/myOwn")
    public ApiResponse readAll() {
        List<ProductDTO> productDTOS = productService.readAll();
        return new ApiResponse<>(HttpStatus.OK.toString(), productDTOS);
    }

    @GetMapping("/one/{productId}")
    public ApiResponse readOne(@PathVariable Integer productId) {
        ProductDTO productDTO = productService.readOne(productId);
        return new ApiResponse<>(HttpStatus.OK.toString(), productDTO);
    }
}

package uz.pdp.securitytest.controller;

import jakarta.servlet.annotation.HttpMethodConstraint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uz.pdp.securitytest.entity.*;
import uz.pdp.securitytest.payload.ProductDTO;
import uz.pdp.securitytest.repository.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final ManagerRepository managerRepository;
    private final UncheckADDSRepository uncheckADDSRepository;
    private final String path = "src/main/resources/static/images/";

    public ProductController(ProductRepository productRepository, CategoryRepository categoryRepository, AttachmentRepository attachmentRepository, ManagerRepository managerRepository, UncheckADDSRepository uncheckADDSRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.attachmentRepository = attachmentRepository;
        this.managerRepository = managerRepository;
        this.uncheckADDSRepository = uncheckADDSRepository;
    }

    //    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).VIEW_PRODUCT.name())")
    @GetMapping("/{categoryId}")
    public List<ProductDTO> read(@PathVariable Integer categoryId) {

        List<Product> matchingProducts = productRepository.findAll().stream().filter(product -> product.getCategory().getId().equals(categoryId)).collect(Collectors.toList());
        List<ProductDTO> productDTOS = new ArrayList<>();
        matchingProducts.forEach(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            String filePath = product.getAttachment().getFilePath();
            filePath = filePath.replaceAll(path, "");
            filePath = filePath.replaceAll("images/", "");
            productDTO.setImageUrl(filePath);
            productDTO.setCategory(product.getCategory());
            productDTOS.add(productDTO);
        });

        return productDTOS;
    }

    //    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).CREATE_PRODUCT.name())")
    @PostMapping("/add/{categoryId}")
    public void create(
            @PathVariable Integer categoryId,
            @RequestBody @Valid ProductDTO product1,
            BindingResult bindingResult) throws IOException {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = new Product(product1.getName(), (long) Math.round(product1.getPrice()), category);
        String path1 = path + UUID.randomUUID() + ".png";
        Product save = productRepository.save(product);
        Managers lazy = managerRepository.findLazy();
        UncheckADDS uncheckADDS = new UncheckADDS();
        uncheckADDS.setManagers(lazy);
        uncheckADDS.setProduct(product);
        uncheckADDSRepository.save(uncheckADDS);
        read(product1.getId());
    }


    //    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).EDIT_PRODUCT.name())")
    @PostMapping("update")
    public void update(@RequestParam Integer id, @RequestParam String name, @RequestParam Double price, @RequestParam(required = false) MultipartFile image) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(name);
        product.setPrice(Math.round(price));

        if (image != null) {
            String contentType = image.getContentType();
            long size = image.getSize();
            String originalFilename = image.getOriginalFilename();
            String path1 = path + UUID.randomUUID() + originalFilename;
            Attachment save1 = attachmentRepository.save(new Attachment(originalFilename, contentType, path1, size));
            product.setAttachment(save1);

        }
        productRepository.save(product);
        List<Product> all = productRepository.findAll();

        read(product.getId());
    }

    //    @PreAuthorize("hasAuthority(T(uz.pdp.securitytest.enums.PermissionEnum).DELETE_PRODUCT.name())")
    @GetMapping("/delete/{id}")
    public void delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Product> byId = productRepository.findById(id);
        Product product = byId.get();
        productRepository.delete(byId.get());
        read(byId.get().getCategory().getId());
        redirectAttributes.addFlashAttribute("message", "Product deleted successfully!");


        read(product.getId());

    }
}

package uz.pdp.securitytest.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.securitytest.ApiResponse;
import uz.pdp.securitytest.entity.*;
import uz.pdp.securitytest.payload.ProductDTO;
import uz.pdp.securitytest.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ChildCategoryRepository childCategoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final ManagerRepository managerRepository;
    private final UncheckADDSRepository uncheckADDSRepository;
    private final String path = "C:\\Users\\User\\IdeaProjects\\OLXk\\images\\";

    public ProductService(ProductRepository productRepository, ChildCategoryRepository childCategoryRepository, AttachmentRepository attachmentRepository, ManagerRepository managerRepository, UncheckADDSRepository uncheckADDSRepository) {
        this.productRepository = productRepository;
        this.childCategoryRepository = childCategoryRepository;
        this.attachmentRepository = attachmentRepository;
        this.managerRepository = managerRepository;
        this.uncheckADDSRepository = uncheckADDSRepository;
    }

    public List<ProductDTO> getProductDTOS(Integer childCategoryId) {

            List<Product> matchingProducts = productRepository.findAll().stream().filter(product -> product.getChildCategory().getId().equals(childCategoryId)).collect(Collectors.toList());
            List<ProductDTO> productDTOS = new ArrayList<>();
            matchingProducts.forEach(product -> {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(product.getId());
                productDTO.setName(product.getName());
                productDTO.setPrice(product.getPrice());
                productDTO.setDescription(product.getDescription());
                String filePath = product.getAttachment().getFilePath();
                filePath = "http://localhost:8080/" + filePath;
                productDTO.setImageUrl(filePath);
                productDTO.setChildCategory(product.getChildCategory());
                productDTO.setAttachmentId(product.getAttachment().getId());
                productDTOS.add(productDTO);
            });
            return productDTOS;
        }

    public ProductDTO create(String name, String description, double price, Integer categoryId, Integer childCategoryId, MultipartFile image) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();
        ChildCategory childCategory = childCategoryRepository.findById(childCategoryId).get();

        Product product = new Product();
        product.setUser(user);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setChildCategory(childCategory);

        if (image != null) {
            String contentType = image.getContentType();
            long size = image.getSize();
            String originalFilename = image.getOriginalFilename();
            String path1 = path + UUID.randomUUID() + originalFilename;
            Attachment save1 = attachmentRepository.save(new Attachment(originalFilename, contentType, path1, size));
            product.setAttachment(save1);
            Files.copy(image.getInputStream(), Path.of(path1));

        }
        productRepository.save(product);
        Managers lazy = managerRepository.findLazy();
        UncheckADDS uncheckADDS = new UncheckADDS();
        uncheckADDS.setManagers(lazy);
        uncheckADDS.setProduct(product);
        uncheckADDSRepository.save(uncheckADDS);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        productDTO.setAttachmentId(product.getAttachment().getId());
        productDTO.setChildCategory(childCategory);
        productDTO.setUserPhone(user.getPhoneNumber());
        return productDTO;
    }

    public ProductDTO update(Integer id, String name, Double price, MultipartFile image) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)auth.getPrincipal();

        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (user.getId() != product.getUser().getId()) {
            throw new RuntimeException("you not user which add product");
        }

        product.setName(name);
        product.setPrice(price);

        if (image != null) {
            String contentType = image.getContentType();
            long size = image.getSize();
            String originalFilename = image.getOriginalFilename();
            String path1 = path + UUID.randomUUID() + originalFilename;
            Attachment save1 = attachmentRepository.save(new Attachment(originalFilename, contentType, path1, size));
            product.setAttachment(save1);

        }
        productRepository.save(product);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(name);
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setAttachmentId(product.getAttachment().getId());
        productDTO.setChildCategory(product.getChildCategory());
        productDTO.setUserPhone(user.getPhoneNumber());
        return productDTO;

    }

    public ProductDTO delete(Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        Product product = byId.get();
        if (Objects.equals(product.getUser().getId(), user.getId())) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setPrice(product.getPrice());
            productDTO.setAttachmentId(product.getAttachment().getId());
            productDTO.setChildCategory(product.getChildCategory());
            productDTO.setUserPhone(user.getPhoneNumber());
            productRepository.delete(product);
            return productDTO;

        }
        else throw new RuntimeException("you do not have permission to delete this product");
    }

    public List<ProductDTO> readAll() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<Product> products = productRepository.findbyUserID(user.getId());
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.forEach(product -> {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setDescription(product.getDescription());
            String filePath = product.getAttachment().getFilePath();
            productDTO.setImageUrl(filePath);
            productDTO.setChildCategory(product.getChildCategory());
            productDTO.setAttachmentId(product.getAttachment().getId());
            productDTOS.add(productDTO);
        });
        return productDTOS;
    }

    public ProductDTO readOne(Integer productId) {

        Optional<Product> byId = productRepository.findById(productId);
        Product product = byId.get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        String filePath = product.getAttachment().getFilePath();
        productDTO.setImageUrl(filePath);
        productDTO.setAttachmentId(product.getAttachment().getId());
        productDTO.setChildCategory(product.getChildCategory());
        productDTO.setUserPhone(product.getUser().getPhoneNumber());
        return productDTO;
    }
}

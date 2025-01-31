package uz.pdp.securitytest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.pdp.securitytest.entity.Product;
import uz.pdp.securitytest.entity.UncheckADDS;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.entity.UserMessage;
import uz.pdp.securitytest.payload.ProductDTO;
import uz.pdp.securitytest.payload.UserMessageDto;
import uz.pdp.securitytest.repository.ManagerRepository;
import uz.pdp.securitytest.repository.ProductRepository;
import uz.pdp.securitytest.repository.UncheckADDSRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/uncheck")
public class UncheckADDSController {

    private final UncheckADDSRepository uncheckADDSRepository;
    private final ManagerRepository managerRepository;
    private final ProductRepository productRepository;

    public UncheckADDSController(UncheckADDSRepository uncheckADDSRepository, ManagerRepository managerRepository, ProductRepository productRepository) {
        this.uncheckADDSRepository = uncheckADDSRepository;
        this.managerRepository = managerRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductDTO> getOwnWork() {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        List<UncheckADDS> byManagersId = uncheckADDSRepository.findByManagers_Id(user.getId());

        for (UncheckADDS uncheckADDS : byManagersId) {
            Product product = uncheckADDS.getProduct();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setCategory(product.getCategory());
            productDTO.setDescription(product.getDescription());
            productDTO.setIs_check(product.getIs_check());
            productDTOS.add(productDTO);

        }
        return productDTOS;
    }

    @PostMapping
    public void add(@RequestBody ProductDTO productDTO) {
        Optional<Product> byId = productRepository.findById(productDTO.getId());
        if (byId.isPresent()) {
            Product product = byId.get();
            product.setIs_check(product.getIs_check());
            productRepository.save(product);
            UncheckADDS uncheckADDS = uncheckADDSRepository.findbyProductID(product.getId());
            uncheckADDS.setDeleted(true);
        }
        getOwnWork();


    }
}

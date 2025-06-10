package uz.pdp.securitytest.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.pdp.securitytest.entity.Managers;
import uz.pdp.securitytest.entity.Product;
import uz.pdp.securitytest.entity.UncheckADDS;
import uz.pdp.securitytest.entity.User;
import uz.pdp.securitytest.payload.ProductDTO;
import uz.pdp.securitytest.repository.ManagerRepository;
import uz.pdp.securitytest.repository.ProductRepository;
import uz.pdp.securitytest.repository.UncheckADDSRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class UncheckedADDSService {
    private final ManagerRepository managerRepository;
    private final ProductRepository productRepository;
    private  final UncheckADDSRepository uncheckADDSRepository;

    public UncheckedADDSService(ManagerRepository managerRepository, ProductRepository productRepository, UncheckADDSRepository uncheckADDSRepository) {
        this.managerRepository = managerRepository;
        this.productRepository = productRepository;
        this.uncheckADDSRepository = uncheckADDSRepository;
    }

    public List<ProductDTO> getOwnWork() {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Managers byManagerId = managerRepository.findByManagerId(user.getId());
        List<UncheckADDS> byManagersId = uncheckADDSRepository.findByManagers_Ide(byManagerId.getId());

        for (UncheckADDS uncheckADDS : byManagersId) {
            Product product = uncheckADDS.getProduct();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setChildCategory(product.getChildCategory());
            productDTO.setDescription(product.getDescription());
            productDTO.setIs_check(product.getIs_check());
            productDTO.setAttachmentId(product.getAttachment().getId());
            productDTOS.add(productDTO);

        }
        return productDTOS;
    }

    public List<ProductDTO> getOwnCheckedWork() {
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        Managers byManagerId = managerRepository.findByManagerId(user.getId());
        List<UncheckADDS> byManagersId = uncheckADDSRepository.findByManagers_IdeandChecked(byManagerId.getId());

        for (UncheckADDS uncheckADDS : byManagersId) {
            Product product = uncheckADDS.getProduct();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setPrice(product.getPrice());
            productDTO.setChildCategory(product.getChildCategory());
            productDTO.setDescription(product.getDescription());
            productDTO.setIs_check(product.getIs_check());
            productDTO.setAttachmentId(product.getAttachment().getId());
            productDTOS.add(productDTO);

        }
        return productDTOS;
    }

    public ProductDTO add(ProductDTO productDTO) {
        Optional<Product> byId = productRepository.findById(productDTO.getId());
        if (byId.isPresent()) {
            Product product = byId.get();
            product.setIs_check(productDTO.getIs_check());
            productRepository.save(product);
            UncheckADDS uncheckADDS = uncheckADDSRepository.findbyProductID(product.getId());
            uncheckADDS.setDeleted(true);
        }
        return productDTO;
    }
}

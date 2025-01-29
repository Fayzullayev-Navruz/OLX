package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.securitytest.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

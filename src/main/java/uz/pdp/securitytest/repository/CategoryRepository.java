package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.securitytest.entity.Category;
import uz.pdp.securitytest.entity.Product;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

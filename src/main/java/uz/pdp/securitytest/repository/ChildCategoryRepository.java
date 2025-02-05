package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.securitytest.entity.ChildCategory;

import java.util.List;

public interface ChildCategoryRepository extends JpaRepository<ChildCategory, Integer> {



    List <ChildCategory>findByCategory_Id(Integer categoryId);
}

package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.securitytest.entity.ChildCategory;

import java.util.List;

@Repository
public interface ChildCategoryRepository extends JpaRepository<ChildCategory,Integer> {
    List<ChildCategory> findByCategory_Id(Integer id);
}

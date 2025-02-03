package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.securitytest.entity.ChildCategory;
@Repository
public interface ChildCategoryRepository extends JpaRepository<ChildCategory,Integer> {
}

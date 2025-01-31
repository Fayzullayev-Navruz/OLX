package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.securitytest.entity.UncheckADDS;

import java.util.List;

public interface UncheckADDSRepository extends JpaRepository<UncheckADDS, Integer> {

    List<UncheckADDS> findByManagers_Id(Integer managersId);

    @Query("select p from UncheckADDS  p where p.product.id=:productId")
    UncheckADDS findbyProductID(Integer productId);
}

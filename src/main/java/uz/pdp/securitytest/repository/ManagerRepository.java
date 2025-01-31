package uz.pdp.securitytest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.securitytest.entity.Managers;

public interface ManagerRepository extends JpaRepository<Managers,Integer> {
    @Query("select m from  Managers  m order by m.uncheckedAdds limit 1")
    Managers findLazy();
}

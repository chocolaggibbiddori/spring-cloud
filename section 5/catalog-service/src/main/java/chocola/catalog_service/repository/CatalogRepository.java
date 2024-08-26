package chocola.catalog_service.repository;

import chocola.catalog_service.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {

    Optional<CatalogEntity> findByProductId(String productId);
}

package chocola.catalog_service.service;

import chocola.catalog_service.entity.CatalogEntity;
import chocola.catalog_service.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}

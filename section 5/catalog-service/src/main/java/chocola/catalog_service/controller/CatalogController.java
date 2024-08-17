package chocola.catalog_service.controller;

import chocola.catalog_service.service.CatalogService;
import chocola.catalog_service.vo.ResponseCatalog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;
    private final ModelMapper mapper = new ModelMapper();

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return "It's working in Catalog-Service on PORT %s".formatted(request.getLocalPort());
    }

    @GetMapping("/catalogs")
    @ResponseStatus(HttpStatus.OK)
    public List<ResponseCatalog> getCatalogs() {
        return catalogService.getAllCatalogs().stream()
                .map(ue -> mapper.map(ue, ResponseCatalog.class))
                .toList();
    }
}

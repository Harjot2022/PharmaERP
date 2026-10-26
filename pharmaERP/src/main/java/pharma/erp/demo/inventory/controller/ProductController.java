package pharma.erp.demo.inventory.controller;

import pharma.erp.demo.inventory.dto.ProductRequest;
import pharma.erp.demo.inventory.dto.ProductResponse;
import pharma.erp.demo.inventory.entity.Product;
import pharma.erp.demo.inventory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductResponse.fromEntity(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable UUID id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(ProductResponse.fromEntity(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(
            @RequestParam(required = false) String name) {

        List<Product> products = (name != null && !name.isBlank())
                ? productService.search(name)
                : productService.getAll();

        List<ProductResponse> response = products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequest request) {

        Product updated = productService.update(id, request);
        return ResponseEntity.ok(ProductResponse.fromEntity(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

package pharma.erp.demo.inventory.service;

import pharma.erp.demo.inventory.dto.ProductRequest;
import pharma.erp.demo.inventory.entity.Product;
import pharma.erp.demo.inventory.exception.DuplicateSkuException;
import pharma.erp.demo.inventory.exception.ProductNotFoundException;
import pharma.erp.demo.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(ProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateSkuException(request.getSku());
        }
        String generatedSku = generateSku(request.getName(), request.getManufacturer());
        if (productRepository.existsBySku(request.getSku())) {
        throw new DuplicateSkuException(request.getSku());
        }
    
        System.out.println("Generated SKU: " + generatedSku); // Debugging line
        Product product = Product.builder()
                                .sku(generatedSku)
                                .name(request.getName())
                                .unit(request.getUnit())
                                .packSize(request.getPackSize())
                                .mrp(request.getMrp())
                                .genericName(request.getGenericName())
                                .brand(request.getBrand())
                                .manufacturer(request.getManufacturer())
                                .category(request.getCategory())
                                .hsnCode(request.getHsnCode())
                                .build();
        return productRepository.save(product);
    }

    private String generateSku(String name, String manufacturer) {
        
        String strength = extractStrength(name);        // pull digits+unit, e.g. "500"
        String namePart = normalize(name, 4);            // first 4 letters, uppercase, alnum only
        String mfrPart  = normalize(manufacturer, 3);     // first 3 letters
        return namePart + strength + mfrPart;             // e.g. "PARA500CIP"
    }

    private String normalize(String input, int maxLen) {
        if (input == null || input.isBlank()) {
            return "X".repeat(maxLen); // Fallback pad string if field is empty
        }
        
        // Strip non-alphanumeric and convert to uppercase
        String cleaned = input.replaceAll("[^A-Za-z0-9]", "").toUpperCase();
        
        // Truncate to maxLen if too long, or pad right with 'X' if too short
        if (cleaned.length() >= maxLen) {
            return cleaned.substring(0, maxLen);
        } else {
            return String.format("%-" + maxLen + "s", cleaned).replace(' ', 'X');
        }
    }

    private String extractStrength(String name) {
        if (name == null || name.isBlank()) {
            return "";
        }

        // Regex to match digits (including decimals) followed by common pharma units
        // Matches: 500mg, 2.5 ml, 10g, 400mcg, 100
        Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?\\s*(?:mg|g|ml|mcg|caps|tabs)?)\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);

        if (matcher.find()) {
            // Strip out internal whitespaces (e.g., "500 mg" -> "500MG")
            return matcher.group(1).replaceAll("\\s+", "").toUpperCase();
        }

        return ""; // Return empty string if no strength digits are detected
    }

    public Product getById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> search(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product update(UUID id, ProductRequest request) {
        Product product = getById(id);

        if (!product.getSku().equals(request.getSku())
                && productRepository.existsBySku(request.getSku())) {
            throw new DuplicateSkuException(request.getSku());
        }

        product.setName(request.getName());
        product.setGenericName(request.getGenericName());
        product.setBrand(request.getBrand());
        product.setManufacturer(request.getManufacturer());
        product.setCategory(request.getCategory());
        product.setHsnCode(request.getHsnCode());
        product.setUnit(request.getUnit());
        product.setPackSize(request.getPackSize());
        product.setMrp(request.getMrp());

        return productRepository.save(product);
    }

    public void delete(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
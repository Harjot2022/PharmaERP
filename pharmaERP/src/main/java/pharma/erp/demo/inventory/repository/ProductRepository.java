package pharma.erp.demo.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pharma.erp.demo.inventory.entity.Batch;
import pharma.erp.demo.inventory.entity.Product;
import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);

    List<Product> findByCategory(String category);

    List<Product> findByManufacturer(String manufacturer);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Batch> findByProductIdOrderByExpiryDateAsc(UUID productId);

    @Query("SELECT b FROM Batch b " +
            " WHERE b.product.id = :productId" +
            " AND b.quantityAvailable > 0 " +
            "ORDER BY b.expiryDate Asc")
    List<Batch> findFefoAllocationsByProductd(@Param("productId") UUID productId);
}

package pharma.erp.demo.inventory.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "generic_name", nullable = false)
    private String genericName;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "category")
    private String category;

    @Column(name = "hsn_code" , nullable = false )
    private String hsnCode;

    @Pattern(regexp = "STRIP|BOX|BOTTLE", message = "Unit must be either STRIP, BOX, or BOTTLE")
    @Column(name = "unit", nullable = false)  
    private String unit;

    @Column(name = "pack_size", nullable = false)
    private Integer packSize;

    @Column(name = "mrp", precision = 10, scale = 2)
    private BigDecimal mrp;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}

package pharma.erp.demo.inventory.entity;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Batches", uniqueConstraints = {
        @UniqueConstraint(name = "uq_product_batch", columnNames = { "batch_number, product_id" })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Batch {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "UUID")
    private Product product;

    @Column(name = "batch_number", nullable = false)
    private String batchNumber;

    @Min(1)
    @Max(12)
    @Column(name = "manufacturing_month", nullable = false)
    private Integer manufacturingMonth;

    @Min(2000)
    @Column(name = "manufacturing_year", nullable = false)
    private Integer manufacturingYear;

    @Min(1)
    @Max(12)
    @Column(name = "expiry_month", nullable = false)
    private Integer expiryMonth;

    @Min(2000)
    @Column(name = "expiry_year", nullable = false)
    private Integer expiryYear;

    @Column(name = "quantity_received", nullable = false)
    private Integer quantityReceived;

    @Column(name = "quantity_available", nullable = false)
    private Integer quantityavailable;

    @Column(name = "purchase_price", precision = 10, scale = 2, nullable = false)
    private BigDecimal purchasePrice;

    @Column(name = "mrp", precision = 10, scale = 2, nullable = false)
    private BigDecimal mrp;

    @Version
    @Column(nullable = false)
    @Builder.Default
    private Integer version = 0;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

}

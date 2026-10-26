package pharma.erp.demo.inventory.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class BatchResponse {
    private UUID id;
    private UUID productId;
    private String productName; // denormalized, e.g. "Paracetamol 500mg"
    private String sku; // denormalized, e.g. "PARA500-CIP-S10"
    private String manufacturer; // denormalized
    private String batchNumber;
    private Integer manufacturingMonth;
    private Integer manufacturingYear;
    private Integer expiryMonth;
    private Integer expiryYear;
    private LocalDate expiryDate; // the generated column, exposed for FEFO sorting/display
    private Integer quantityReceived;
    private Integer quantityAvailable;
    private BigDecimal purchasePrice;
    private BigDecimal mrp;
    private Integer version;
    private Instant createdAt;
    private Instant updatedAt;
}

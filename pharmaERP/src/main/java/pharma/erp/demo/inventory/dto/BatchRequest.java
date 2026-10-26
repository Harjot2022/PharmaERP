package pharma.erp.demo.inventory.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BatchRequest {

    @NotBlank(message = "Product ID cannot be blank")
    private String product_id;

    @NotBlank(message = "Batch number cannot be blank")
    @Size(max = 50)
    private String batchNumber;

    @Min(1)
    @Max(12)
    @NotNull(message = "Manufacturing month cannot be blank")
    private Integer manufacturingMonth;

    @Min(2000)
    @Max(9999)
    @NotNull(message = "Manufacturing year cannot be blank")
    private Integer manufacturingYear;

    @Min(1)
    @Max(12)
    @NotNull(message = "Expiry month cannot be blank")
    private Integer expiryMonth;

    @Min(2000)
    @Max(9999)
    @NotNull(message = "Expiry year cannot be blank")
    private Integer expiryYear;

    @Min(1)
    @NotNull(message = "Quantity received cannot be null")
    private Integer quantityReceived;

    @NotNull(message = "Purchase price cannot be null")
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal purchasePrice;

    @NotNull(message = "mrp cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "mrp must be greater than 0")
    private BigDecimal mrp;

    public boolean ifExpiryYearIsGreaterThanManufacturingYear() {
        if (expiryYear == null || manufacturingYear == null) {
            return false;
        }
        return (expiryYear > manufacturingYear)
                || (expiryYear.equals(manufacturingYear) && expiryMonth > manufacturingMonth);
    }
}

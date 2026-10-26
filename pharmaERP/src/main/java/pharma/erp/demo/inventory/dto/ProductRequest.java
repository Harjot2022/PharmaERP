package pharma.erp.demo.inventory.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductRequest {
    
    private String sku;
    
    @NotBlank(message = "Name cannot be blank")
    private String name;

    private String genericName;

    private String brand;

    @NotBlank(message = "Manufacturer cannot be blank")
    private String manufacturer;

    private String category;

    @NotBlank(message = "hsnCode cannot be blank")
    private String hsnCode;

    @NotBlank(message = "Unit cannot be blank")
    private String unit;
    
    @NotNull(message = "Pact size is required")
    private Integer packSize;

    @NotNull(message = "MRP cannot be null")
    @Positive(message = "MRP must be a positive value")
    private BigDecimal mrp;

}

package pharma.erp.demo.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import pharma.erp.demo.inventory.entity.Product;
import lombok.Data;

@Data
public class ProductResponse {
    
    private UUID id;
    private String sku;
    private String name;
    private String genericName;
    private String brand;
    private String manufacturer;
    private String category;
    private String hsnCode;
    private String unit;
    private Integer packSize;
    private BigDecimal mrp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse fromEntity(Product p) {
        ProductResponse r = new ProductResponse();
        r.id = p.getId();
        r.sku = p.getSku();
        r.name = p.getName();
        r.genericName = p.getGenericName();
        r.brand = p.getBrand();
        r.manufacturer = p.getManufacturer();
        r.category = p.getCategory();
        r.hsnCode = p.getHsnCode();
        r.unit = p.getUnit();
        r.packSize = p.getPackSize();
        r.mrp = p.getMrp();
        r.createdAt = p.getCreatedAt();
        r.updatedAt = p.getUpdatedAt();
        return r;
    }

}


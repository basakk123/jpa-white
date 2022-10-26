package site.metacoding.white.util;

import org.junit.jupiter.api.Test;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // 스프링에서 db -> rs -> 엔티티 (전략 : 디폴트 생성자를 호출한 뒤 setter)
@Setter
@Getter
class Product {
    private Integer id;
    private String name;
    private Integer price;
    private Integer qty;
    private String mcp; // 제조사

    @Builder
    public Product(Integer id, String name, Integer price, Integer qty, String mcp) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.mcp = mcp;
    }

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setQty(product.getQty());
        return productDto;
    }
}

@NoArgsConstructor
@Setter
@Getter
class ProductDto {
    private String name;
    private Integer price;
    private Integer qty;

    public Product toEntity() {
        return Product.builder().name(name).price(price).qty(qty).build();
    }

    public ProductDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.qty = product.getQty();
    }

}

public class MapperTest {

    @Test
    public void 고급매핑하기() {
        // toEntity, toDto 만들어서 매핑하면 편하지 않을까? (재활용)
        ProductDto productDto = new ProductDto();
        productDto.setName("바나나");
        productDto.setPrice(1000);
        productDto.setQty(100);
        Product product = productDto.toEntity();
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getQty());
        product.setName("딸기");
        productDto = product.toDto(product);
        System.out.println(productDto.getName());
    }

    @Test
    public void 매핑하기1() {
        // 1. Product 객체 생성(디폴트)
        Product product = new Product();
        // 2. 값 넣기
        product.setId(1);
        product.setName("바나나");
        product.setPrice(1000);
        product.setQty(100);
        product.setMcp("삼성");
        // 3. ProductDto 객체 생성(디폴트)
        ProductDto productDto = new ProductDto();
        // 4. Product -> ProductDto로 옮기기
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setQty(product.getQty());
        // 5. ProductDto -> Product 변경
        Product p2 = new Product();
        p2.setName(productDto.getName());
        p2.setPrice(productDto.getPrice());
        p2.setQty(productDto.getQty());
    }
}

package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Lesson implements Entity {
    private Long id;
    private String sku; 
}

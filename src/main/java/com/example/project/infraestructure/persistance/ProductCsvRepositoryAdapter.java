package com.example.project.infraestructure.persistance;

import com.example.project.application.mapper.ProductMapper;
import com.example.project.application.port.out.ProductRepository;
import com.example.project.domain.model.Product;
import com.example.project.infraestructure.dto.ProductDto;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("csv")
public class ProductCsvRepositoryAdapter implements ProductRepository {

    private final String dataFilePath;
    private final DateTimeFormatter dateTimeFormatter;
    private final String CSV_HEADER;

    private final ProductMapper productMapper;
    public ProductCsvRepositoryAdapter(ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.dataFilePath = "data/products.csv";
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        this.CSV_HEADER = "id,title,description,price,currency,availableQuantity,status,category,subcategory," +
                "sellerId,sellerName,listingType,freeShipping,shippingCost,weight,width,height,length," +
                "images,attributes,views,sales,rating,ratingCount,createdAt,updatedAt,lastSoldAt,condition," +
                "brand,model,sku,barcode,tags,warranty,returnPolicy";
        initializeDataFile();
    }

    private void initializeDataFile() {
        try {
            Path path = Paths.get(dataFilePath);
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                Files.createFile(path);
                try (PrintWriter writer = new PrintWriter(new FileWriter(dataFilePath))) {
                    writer.println(CSV_HEADER);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing CSV data file", e);
        }
    }

    @Override
    public Product save(Product product) {
        try {
            List<ProductDto> products = loadAllProducts();
            
            if (product.getId() != null) {
                products = products.stream()
                        .map(p -> p.getId().equals(product.getId()) ? productMapper.toDto(product) : p)
                        .collect(Collectors.toList());
            } else {
                ProductDto productDto = productMapper.toDto(product);
                productDto.setId(UUID.randomUUID().toString());
                products.add(productDto);
            }
            
            saveAllProducts(products);
            return product.getId() != null ? product : 
                   productMapper.toDomain(products.get(products.size() - 1));
            
        } catch (Exception e) {
            throw new RuntimeException("Error saving product to CSV", e);
        }
    }

    @Override
    public Optional<Product> findById(String id) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> p.getId().equals(id))
                    .findFirst()
                    .map(productMapper::toDomain);
        } catch (Exception e) {
            throw new RuntimeException("Error finding product by id: " + id, e);
        }
    }

    @Override
    public Optional<Product> findByTitle(String title) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> p.getTitle() != null && p.getTitle().equals(title))
                    .findFirst()
                    .map(productMapper::toDomain);
        } catch (Exception e) {
            throw new RuntimeException("Error finding product by title: " + title, e);
        }
    }

    @Override
    public List<Product> findAll() {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .map(productMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding all products", e);
        }
    }

    private List<ProductDto> loadAllProducts() throws IOException {
        File file = new File(dataFilePath);
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        
        List<ProductDto> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                
                if (!line.trim().isEmpty()) {
                    ProductDto product = parseCsvLine(line);
                    if (product != null) {
                        products.add(product);
                    }
                }
            }
        }
        
        return products;
    }

    private void saveAllProducts(List<ProductDto> products) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(dataFilePath))) {
            writer.println(CSV_HEADER);
            for (ProductDto product : products) {
                writer.println(convertToCsvLine(product));
            }
        }
    }

    private ProductDto parseCsvLine(String line) {
        try {
            String[] fields = parseCsvFields(line);
            if (fields.length < 35) {
                return null;
            }
            
            ProductDto product = new ProductDto();
            product.setId(parseString(fields[0]));
            product.setTitle(fields[1]);
            product.setDescription(fields[2]);
            product.setPrice(parseBigDecimal(fields[3]));
            product.setCurrency(fields[4]);
            product.setAvailableQuantity(parseInteger(fields[5]));
            product.setStatus(parseProductStatus(fields[6]));
            product.setCategory(fields[7]);
            product.setSubcategory(fields[8]);
            product.setSellerId(parseString(fields[9]));
            product.setSellerName(fields[10]);
            product.setListingType(parseListingType(fields[11]));
            product.setFreeShipping(parseBoolean(fields[12]));
            product.setShippingCost(parseBigDecimal(fields[13]));
            product.setWeight(parseDouble(fields[14]));
            product.setWidth(parseDouble(fields[15]));
            product.setHeight(parseDouble(fields[16]));
            product.setLength(parseDouble(fields[17]));
            product.setImages(parseStringList(fields[18]));
            product.setAttributes(parseStringList(fields[19]));
            product.setViews(parseInteger(fields[20]));
            product.setSales(parseInteger(fields[21]));
            product.setRating(parseDouble(fields[22]));
            product.setRatingCount(parseInteger(fields[23]));
            product.setCreatedAt(parseLocalDateTime(fields[24]));
            product.setUpdatedAt(parseLocalDateTime(fields[25]));
            product.setLastSoldAt(parseLocalDateTime(fields[26]));
            product.setCondition(parseProductCondition(fields[27]));
            product.setBrand(fields[28]);
            product.setModel(fields[29]);
            product.setSku(fields[30]);
            product.setBarcode(fields[31]);
            product.setTags(parseStringList(fields[32]));
            product.setWarranty(fields[33]);
            product.setReturnPolicy(fields[34]);
            
            return product;
        } catch (Exception e) {
            System.err.println("Error parsing CSV line: " + line + " - " + e.getMessage());
            return null;
        }
    }

    private String convertToCsvLine(ProductDto product) {
        return String.join(",",
                escapeCsvField(String.valueOf(product.getId())),
                escapeCsvField(product.getTitle()),
                escapeCsvField(product.getDescription()),
                escapeCsvField(product.getPrice() != null ? product.getPrice().toString() : ""),
                escapeCsvField(product.getCurrency()),
                escapeCsvField(String.valueOf(product.getAvailableQuantity())),
                escapeCsvField(product.getStatus() != null ? product.getStatus().toString() : ""),
                escapeCsvField(product.getCategory()),
                escapeCsvField(product.getSubcategory()),
                escapeCsvField(String.valueOf(product.getSellerId())),
                escapeCsvField(product.getSellerName()),
                escapeCsvField(product.getListingType() != null ? product.getListingType().toString() : ""),
                escapeCsvField(String.valueOf(product.getFreeShipping())),
                escapeCsvField(product.getShippingCost() != null ? product.getShippingCost().toString() : ""),
                escapeCsvField(product.getWeight() != null ? product.getWeight().toString() : ""),
                escapeCsvField(product.getWidth() != null ? product.getWidth().toString() : ""),
                escapeCsvField(product.getHeight() != null ? product.getHeight().toString() : ""),
                escapeCsvField(product.getLength() != null ? product.getLength().toString() : ""),
                escapeCsvField(convertStringListToCsv(product.getImages())),
                escapeCsvField(convertStringListToCsv(product.getAttributes())),
                escapeCsvField(String.valueOf(product.getViews())),
                escapeCsvField(String.valueOf(product.getSales())),
                escapeCsvField(String.valueOf(product.getRating())),
                escapeCsvField(String.valueOf(product.getRatingCount())),
                escapeCsvField(product.getCreatedAt() != null ? product.getCreatedAt().format(dateTimeFormatter) : ""),
                escapeCsvField(product.getUpdatedAt() != null ? product.getUpdatedAt().format(dateTimeFormatter) : ""),
                escapeCsvField(product.getLastSoldAt() != null ? product.getLastSoldAt().format(dateTimeFormatter) : ""),
                escapeCsvField(product.getCondition() != null ? product.getCondition().toString() : ""),
                escapeCsvField(product.getBrand()),
                escapeCsvField(product.getModel()),
                escapeCsvField(product.getSku()),
                escapeCsvField(product.getBarcode()),
                escapeCsvField(convertStringListToCsv(product.getTags())),
                escapeCsvField(product.getWarranty()),
                escapeCsvField(product.getReturnPolicy())
        );
    }

    // Métodos helper para parsing CSV
    private String[] parseCsvFields(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }

    private String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    private String convertStringListToCsv(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return String.join(";", list);
    }

    private List<String> parseStringList(String field) {
        if (field == null || field.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return Arrays.asList(field.split(";"));
    }

    // Métodos de parsing para tipos específicos
    private String parseString(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value;
    }

    private Integer parseInteger(String value) {
        return (value == null || value.trim().isEmpty()) ? null : Integer.parseInt(value);
    }

    private Double parseDouble(String value) {
        return (value == null || value.trim().isEmpty()) ? null : Double.parseDouble(value);
    }

    private BigDecimal parseBigDecimal(String value) {
        return (value == null || value.trim().isEmpty()) ? null : new BigDecimal(value);
    }

    private Boolean parseBoolean(String value) {
        return (value == null || value.trim().isEmpty()) ? null : Boolean.parseBoolean(value);
    }

    private LocalDateTime parseLocalDateTime(String value) {
        return (value == null || value.trim().isEmpty()) ? null : LocalDateTime.parse(value, dateTimeFormatter);
    }

    private com.example.project.domain.common.enums.ProductStatus parseProductStatus(String value) {
        return (value == null || value.trim().isEmpty()) ? null : 
               com.example.project.domain.common.enums.ProductStatus.valueOf(value);
    }

    private com.example.project.domain.common.enums.ListingType parseListingType(String value) {
        return (value == null || value.trim().isEmpty()) ? null : 
               com.example.project.domain.common.enums.ListingType.valueOf(value);
    }

    private com.example.project.domain.common.enums.ProductCondition parseProductCondition(String value) {
        return (value == null || value.trim().isEmpty()) ? null : 
               com.example.project.domain.common.enums.ProductCondition.valueOf(value);
    }

    // Métodos adicionales útiles
    public boolean deleteById(String id) {
        try {
            List<ProductDto> products = loadAllProducts();
            boolean removed = products.removeIf(p -> p.getId().equals(id));
            if (removed) {
                saveAllProducts(products);
            }
            return removed;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting product by id: " + id, e);
        }
    }

    public List<Product> findByCategory(String category) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> p.getCategory() != null && p.getCategory().equals(category))
                    .map(productMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by category: " + category, e);
        }
    }

    public List<Product> findBySellerId(Long sellerId) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> p.getSellerId() != null && p.getSellerId().equals(sellerId))
                    .map(productMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by seller id: " + sellerId, e);
        }
    }

    public long count() {
        try {
            return loadAllProducts().size();
        } catch (Exception e) {
            throw new RuntimeException("Error counting products", e);
        }
    }

    @Override
    public List<Product> findByKeyword(String keyword) {
        try {
            List<ProductDto> products = loadAllProducts();
            return products.stream()
                    .filter(p -> matchesKeyword(p, keyword))
                    .map(productMapper::toDomain)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by keyword: " + keyword, e);
        }
    }

    @Override
    public int countByKeyword(String keyword) {
        try {
            List<ProductDto> products = loadAllProducts();
            return (int) products.stream()
                    .filter(p -> matchesKeyword(p, keyword))
                    .count();
        } catch (Exception e) {
            throw new RuntimeException("Error counting products by keyword: " + keyword, e);
        }
    }

    private boolean matchesKeyword(ProductDto product, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return false;
        }
        
        String lowerKeyword = keyword.toLowerCase();
        
        // Buscar en campos principales que permiten identificar el producto fácilmente
        return (product.getTitle() != null && product.getTitle().toLowerCase().contains(lowerKeyword)) ||
               (product.getCategory() != null && product.getCategory().toLowerCase().contains(lowerKeyword)) ||
               (product.getSubcategory() != null && product.getSubcategory().toLowerCase().contains(lowerKeyword)) ||
               (product.getBrand() != null && product.getBrand().toLowerCase().contains(lowerKeyword)) ||
               (product.getDescription() != null && product.getDescription().toLowerCase().contains(lowerKeyword)) ||
               (product.getTags() != null && product.getTags().stream()
                       .anyMatch(tag -> tag != null && tag.toLowerCase().contains(lowerKeyword))) ||
               (product.getModel() != null && product.getModel().toLowerCase().contains(lowerKeyword)) ||
               (product.getSku() != null && product.getSku().toLowerCase().contains(lowerKeyword));
    }
}

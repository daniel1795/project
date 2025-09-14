package com.example.project.domain.model;

import com.example.project.domain.common.enums.ListingType;
import com.example.project.domain.common.enums.ProductCondition;
import com.example.project.domain.common.enums.ProductStatus;
import com.example.project.domain.common.valueobjects.Rating;
import com.example.project.domain.common.valueobjects.Stock;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Product {
    
    private final String id;
    private final String title;
    private final String description;
    private final BigDecimal price;
    private final String currency;
    private final Stock stock;
    private final ProductStatus status;
    private final String category;
    private final String subcategory;
    private final String sellerId;
    private final String sellerName;
    private final ListingType listingType;
    private final Boolean freeShipping;
    private final BigDecimal shippingCost;
    private final Double weight;
    private final Double width; 
    private final Double height; 
    private final Double length;
    private final List<String> images;
    private final List<String> attributes;
    private final Integer views;
    private final Integer sales;
    private final Rating rating;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime lastSoldAt;
    private final ProductCondition condition;
    private final String brand;
    private final String model;
    private final String sku;
    private final String barcode; 
    private final List<String> tags;
    private final String warranty;
    private final String returnPolicy;
    
    // Constructor patron Builder
    private Product(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.price = builder.price;
        this.currency = builder.currency;
        this.stock = builder.stock;
        this.status = builder.status;
        this.category = builder.category;
        this.subcategory = builder.subcategory;
        this.sellerId = builder.sellerId;
        this.sellerName = builder.sellerName;
        this.listingType = builder.listingType;
        this.freeShipping = builder.freeShipping;
        this.shippingCost = builder.shippingCost;
        this.weight = builder.weight;
        this.width = builder.width;
        this.height = builder.height;
        this.length = builder.length;
        this.images = builder.images;
        this.attributes = builder.attributes;
        this.views = builder.views;
        this.sales = builder.sales;
        this.rating = builder.rating;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.lastSoldAt = builder.lastSoldAt;
        this.condition = builder.condition;
        this.brand = builder.brand;
        this.model = builder.model;
        this.sku = builder.sku;
        this.barcode = builder.barcode;
        this.tags = builder.tags;
        this.warranty = builder.warranty;
        this.returnPolicy = builder.returnPolicy;
    }
        
    // Builder pattern
    public static Builder newBuilder(String id) {
        return new Builder(id);
    }
    
    public static class Builder {
        private final String id;
    private String title;
    private String description;
    private BigDecimal price;
    private String currency;
    private Stock stock;
    private ProductStatus status;
    private String category;
    private String subcategory;
    private String sellerId;
    private String sellerName;
    private ListingType listingType;
    private Boolean freeShipping;
    private BigDecimal shippingCost;
    private Double weight;
    private Double width; 
    private Double height; 
    private Double length;
    private List<String> images;
    private List<String> attributes;
    private Integer views;
    private Integer sales;
    private Rating rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastSoldAt;
    private ProductCondition condition;
    private String brand;
    private String model;
    private String sku;
    private String barcode; 
    private List<String> tags;
    private String warranty;
    private String returnPolicy;
    
        private Builder(String id) {
        this.id = id;
            this.views = 0;
            this.sales = 0;
            this.rating = Rating.empty();
            this.createdAt = LocalDateTime.now();
        }
        
        public Builder basicInfo(String title, String description, BigDecimal price, String currency) {
            this.title = title;
            this.description = description;
            this.price = price;
            this.currency = currency;
            return this;
        }
        
        public Builder categorization(String category, String subcategory) {
            this.category = category;
            this.subcategory = subcategory;
            return this;
        }
        
        public Builder seller(String sellerId, String sellerName) {
            this.sellerId = sellerId;
            this.sellerName = sellerName;
            return this;
        }
        
        public Builder listing(ListingType listingType, Boolean freeShipping) {
            this.listingType = listingType;
            this.freeShipping = freeShipping;
            return this;
        }
        
        public Builder condition(ProductCondition condition) {
            this.condition = condition;
            return this;
        }
        
        public Builder stock(int quantity) {
            this.stock = Stock.of(quantity);
            return this;
        }
        
        public Builder status(ProductStatus status) {
            this.status = status;
            return this;
        }
        
        public Builder shipping(BigDecimal shippingCost) {
            this.shippingCost = shippingCost;
            return this;
        }
        
        public Builder dimensions(Double weight, Double width, Double height, Double length) {
            this.weight = weight;
            this.width = width;
            this.height = height;
            this.length = length;
            return this;
        }
        
        public Builder media(List<String> images, List<String> attributes) {
            this.images = images;
            this.attributes = attributes;
            return this;
        }
        
        public Builder identification(String sku, String barcode, String brand, String model) {
            this.sku = sku;
            this.barcode = barcode;
            this.brand = brand;
            this.model = model;
            return this;
        }
        
        public Builder policies(String warranty, String returnPolicy, List<String> tags) {
            this.warranty = warranty;
            this.returnPolicy = returnPolicy;
            this.tags = tags;
            return this;
        }
        
        public Builder views(Integer views) {
            this.views = views;
            this.updatedAt = LocalDateTime.now();
            return this;
        }
        
        public Builder sales(Integer sales) {
            this.sales = sales;
            this.updatedAt = LocalDateTime.now();
            return this;
        }
        
        public Builder lastSoldAt(LocalDateTime lastSoldAt) {
            this.lastSoldAt = lastSoldAt;
            return this;
        }
        
        public Builder rating(Rating rating) {
            this.rating = rating;
            this.updatedAt = LocalDateTime.now();
            return this;
        }
        
        public Product build() {
            // Validaciones de negocio
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Product title is required");
            }
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Product price must be greater than zero");
            }
            if (sellerId == null) {
                throw new IllegalArgumentException("Seller ID is required");
            }
            
            return new Product(this);
        }
    }
    
    // Métodos de negocio - Clean Code principles
    public boolean isAvailable() {
        return stock.isAvailable() && ProductStatus.ACTIVE.equals(status);
    }
    
    public boolean hasStock() {
        return stock.hasStock();
    }
    
    public boolean isFreeShipping() {
        return freeShipping != null && freeShipping;
    }
    
    // Métodos inmutables - MUCHO MÁS SIMPLES
    public Product recordView() {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.getQuantity())
                .status(this.status)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .views((this.views == null) ? 1 : this.views + 1)
                .build();
    }
    
    public Product recordSale() {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.getQuantity())
                .status(this.status)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .sales((this.sales == null) ? 1 : this.sales + 1)
                .lastSoldAt(LocalDateTime.now())
                .build();
    }
    
    public Product addRating(double newRating) {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.getQuantity())
                .status(this.status)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .rating(this.rating.addRating(newRating))
                .build();
    }
    
    public Product reduceStock(int quantity) {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.reduce(quantity).getQuantity())
                .status(this.status)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .build();
    }
    
    public Product addStock(int quantity) {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.add(quantity).getQuantity())
                .status(this.status)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .build();
    }
    
    public Product updateBasicInfo(String title, String description, BigDecimal price) {
        return newBuilder(this.id)
                .basicInfo(title, description, price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.getQuantity())
                .status(this.status)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .build();
    }
    
    public Product updateCategory(String category, String subcategory) {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(category, subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.getQuantity())
                .status(this.status)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .build();
    }
    
    public Product updateCondition(ProductCondition condition) {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(condition)
                .stock(this.stock.getQuantity())
                .status(this.status)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .build();
    }
    
    public Product pauseProduct() {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.getQuantity())
                .status(ProductStatus.PAUSED)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .build();
    }
    
    public Product activateProduct() {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.getQuantity())
                .status(ProductStatus.ACTIVE)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .build();
    }
    
    public Product closeProduct() {
        return newBuilder(this.id)
                .basicInfo(this.title, this.description, this.price, this.currency)
                .categorization(this.category, this.subcategory)
                .seller(this.sellerId, this.sellerName)
                .listing(this.listingType, this.freeShipping)
                .condition(this.condition)
                .stock(this.stock.getQuantity())
                .status(ProductStatus.CLOSED)
                .shipping(this.shippingCost)
                .dimensions(this.weight, this.width, this.height, this.length)
                .media(this.images, this.attributes)
                .identification(this.sku, this.barcode, this.brand, this.model)
                .policies(this.warranty, this.returnPolicy, this.tags)
                .build();
    }
    
    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getCurrency() { return currency; }
    public int getAvailableQuantity() { return stock.getQuantity(); }
    public ProductStatus getStatus() { return status; }
    public String getCategory() { return category; }
    public String getSubcategory() { return subcategory; }
    public String getSellerId() { return sellerId; }
    public String getSellerName() { return sellerName; }
    public ListingType getListingType() { return listingType; }
    public Boolean getFreeShipping() { return freeShipping; }
    public BigDecimal getShippingCost() { return shippingCost; }
    public Double getWeight() { return weight; }
    public Double getWidth() { return width; }
    public Double getHeight() { return height; }
    public Double getLength() { return length; }
    public List<String> getImages() { return images; }
    public List<String> getAttributes() { return attributes; }
    public Integer getViews() { return views; }
    public Integer getSales() { return sales; }
    public double getRating() { return rating.getValue(); }
    public int getRatingCount() { return rating.getCount(); }
    public Rating getRatingObject() { return rating; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getLastSoldAt() { return lastSoldAt; }
    public ProductCondition getCondition() { return condition; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public Stock getStock() { return stock; }
    public boolean getAvailable() { return stock.isAvailable(); }
    public String getSku() { return sku; }
    public String getBarcode() { return barcode; }
    public List<String> getTags() { return tags; }
    public String getWarranty() { return warranty; }
    public String getReturnPolicy() { return returnPolicy; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", status=" + status +
                ", sellerId=" + sellerId +
                '}';
    }
}

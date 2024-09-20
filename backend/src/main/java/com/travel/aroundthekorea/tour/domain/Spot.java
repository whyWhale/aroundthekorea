package com.travel.aroundthekorea.tour.domain;

import java.time.LocalDateTime;

import org.springframework.batch.item.Chunk;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "spots")
@Entity
public class Spot extends Chunk<Spot> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content_id")
    private Integer contentId;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "image1")
    private String image1;

    @Column(name = "image2")
    private String image2;

    @Column(name = "category")
    private String category;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    protected Spot() {
    }

    public Spot(String title, Integer contentId, String address, String latitude, String longitude, String image1, String image2, String category) {
        this.title = title;
        this.contentId = contentId;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.image1 = image1;
        this.image2 = image2;
        this.category = category;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getContentId() {
        return contentId;
    }

    public String getAddress() {
        return address;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

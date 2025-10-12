package com.evdealer.ev_dealer_management.car.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "car_image", schema = "dbo")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_url")
    private String fileUrl;
}

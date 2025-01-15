package com.bruno.stockmanager.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String reportType; // PERFORMANCE, ALLOCATION

    @Column(nullable = false)
    private LocalDateTime generatedAt;

    @Column(nullable = false)
    private String filePath;
}

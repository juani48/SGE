package com.bsoftware.sge.model;

import java.time.LocalDate;
import java.util.List;

import com.bsoftware.sge.auxiliar.FileState;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class File {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(nullable = false) 
    private String cover;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private FileState state;

    @Column(nullable = false) 
    private LocalDate creation;
    
    @Column(nullable = false) 
    private LocalDate modification;
    
    @ManyToOne()
    @JoinColumn(name = "modification_user_id", nullable = false)
    private ApplicationUser modificationUser;
    
    @OneToMany(mappedBy = "file", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Procedure> procedures = new java.util.ArrayList<>();

}

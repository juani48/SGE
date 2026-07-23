package com.bsoftware.sge.model;

import java.time.LocalDate;

import com.bsoftware.sge.auxiliar.ProcedureState;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Procedure  {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProcedureState state;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false) 
    private LocalDate creation;
    
    @Column(nullable = false) 
    private LocalDate modification;
    
    @ManyToOne()
    @JoinColumn(name = "modification_user_id", nullable = false)
    private ApplicationUser modificationUser;

    @ManyToOne()
    @JoinColumn(name = "proceeding_id", nullable = false)
    private File proceeding;
}

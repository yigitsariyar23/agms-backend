package com.agms.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.agms.backend.model.users.DeanOfficer;
import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FacultyList")
public class FacultyList {
    @Id
    private String facultyListId;

    @Column(nullable = false)
    private Timestamp creationDate;

    @Column(nullable = false)
    private String faculty;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isFinalized = false;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "deanOfficerId", nullable = false)
    private DeanOfficer deanOfficer;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "tGraduationListId", nullable = false)
    private GraduationList graduationList;

    @OneToMany(mappedBy = "facultyList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DepartmentList> departmentLists;
}
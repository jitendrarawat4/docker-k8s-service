package entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_department", indexes = {
                @Index(columnList = "dept_name", name = "IDX_TBL_DEPARTMENTS_DEPT_NAME")
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "dept_name", name = "UNIQ_DEPT_NAME")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Department {

    @Id
    @Column(name = "dept_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer departmentNumber;

    @Column(name = "dept_name")
    @NotBlank
    private String departmentName;
}

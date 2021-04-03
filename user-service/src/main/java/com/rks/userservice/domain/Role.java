package com.rks.userservice.domain;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        @NaturalId
        @Column(length = 60)
        private RoleName name;

        public Role() {
        }
        public Role(RoleName name) {
            this.name = name;
        }
}

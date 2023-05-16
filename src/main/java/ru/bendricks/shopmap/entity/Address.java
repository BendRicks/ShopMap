package ru.bendricks.shopmap.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    @JsonBackReference
    private Shop shop;

    @Column(name = "city", nullable = false, length = 45)
    private String city;

    @Column(name = "district", nullable = false, length = 45)
    private String district;

    @Column(name = "street", nullable = false, length = 45)
    private String street;

    @Column(name = "building", nullable = false, length = 45)
    private String building;

    @Column(name = "room", nullable = false, length = 45)
    private String room;

    @Override
    public String toString() {
        return "Address{" +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}

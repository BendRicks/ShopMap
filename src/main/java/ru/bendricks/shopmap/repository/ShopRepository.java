package ru.bendricks.shopmap.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.bendricks.shopmap.entity.Shop;
import ru.bendricks.shopmap.entity.ShopStatus;
import ru.bendricks.shopmap.entity.User;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    Integer countByCreator(User user);
    @Query(value = "select distinct sh from Shop sh inner join fetch Address adr on sh.id=adr.shop.id where sh.shopStatus=:status and (sh.name like %:nameLike% or sh.description like %:nameLike% or adr.city like %:nameLike% or adr.district like %:nameLike% or adr.street like %:nameLike% or adr.building like %:nameLike% or adr.room like %:nameLike%)")
    List<Shop> findAllByString(@Param("nameLike") String nameLike, @Param("status") ShopStatus status, @Param("pageable") Pageable pageable);

    @Query(value = "select count(distinct sh) from Shop sh inner join fetch Address adr on sh.id=adr.shop.id where sh.shopStatus=:status and (sh.name like %:nameLike% or sh.description like %:nameLike% or adr.city like %:nameLike% or adr.district like %:nameLike% or adr.street like %:nameLike% or adr.building like %:nameLike% or adr.room like %:nameLike%)")
    Integer getMatchesAmount(@Param("nameLike") String nameLike, @Param("status") ShopStatus status);

}

package com.example.demo.domain.distribution.repository;

import com.example.demo.domain.distribution.entity.Item;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemJpaRepository extends JpaRepository<Item, Long> {

    //조회 시점부터 배터적 Lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Item i where i.id = :id")
    Optional<Item> findByIdWithPessimisticLock(@Param("id") Long id);

    //db의 값을 원자적으로 차감
    @Modifying
    @Query("update Item i set i.stock = i.stock - :num where i.stock>= :num and i.id = :id")
    int updateItemStockById(@Param("id") Long id, @Param("num") int num);

    //낙관적 락을 위한 조회
    Optional<Item> findById(Long id);
}

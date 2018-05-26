package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.BannerType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerTypeRepository extends CrudRepository<BannerType, String> {

    @Query(value = "select count(*) from BannerType")
    Integer getCount();

    BannerType findByType(Integer type);

}
package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.BannerBean;
import com.sumzupp.backendapp.entities.Banner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends CrudRepository<Banner, String> {

    @Query(value = "SELECT new com.sumzupp.backendapp.beans.BannerBean(b.id, b.title, b.description, b.bannerImageUrl, b.deepLink, b.bannerType.type) FROM Banner b WHERE b.active = true")
    List<BannerBean> fetchActiveBanners();

}
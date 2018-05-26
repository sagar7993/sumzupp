package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.PostBean;
import com.sumzupp.backendapp.entities.Post;
import com.sumzupp.backendapp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, String> {

    @Transactional
    @Query(value = "select new com.sumzupp.backendapp.beans.PostBean(p.id , p.postText, p.postImageUrl, p.commentCount, p.upvoteCount, p.downvoteCount, p.createdAt, p.user.id, p.user.name, p.user.profilePicUrl) from Post p")
    Page<PostBean> fetchAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update Post p set p.user = :primaryUser where p.user = :secondaryUser")
    void updateUserPosts(@Param("primaryUser") User primaryUser, @Param("secondaryUser") User secondaryUser);
}

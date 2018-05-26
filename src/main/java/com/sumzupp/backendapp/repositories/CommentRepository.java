package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.CommentBean;
import com.sumzupp.backendapp.entities.Comment;
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
public interface CommentRepository extends PagingAndSortingRepository<Comment, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.CommentBean(c.id, c.commentText, c.createdAt, c.user.id, c.user.name, c.user.profilePicUrl) from Comment c where c.post = :post")
    Page<CommentBean> findByPost(@Param("post") Post post, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update Comment c set c.user = :primaryUser where c.user = :secondaryUser")
    void updateUserComments(@Param("primaryUser") User primaryUser, @Param("secondaryUser") User secondaryUser);
}

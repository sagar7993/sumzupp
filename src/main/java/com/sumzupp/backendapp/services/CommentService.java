package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.CommentBean;
import com.sumzupp.backendapp.beans.CommentsBean;
import com.sumzupp.backendapp.beans.DataSaveResultBean;
import com.sumzupp.backendapp.entities.Comment;
import com.sumzupp.backendapp.entities.Post;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.repositories.CommentRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Service
public class CommentService {
    private static final String TAG = "CommentService : ";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    @Transactional(rollbackFor = {Exception.class})
    public DataSaveResultBean save(CommentBean commentBean) {
        DataSaveResultBean dataSaveResultBean = new DataSaveResultBean();

        Post retrievedPost = postService.findById(commentBean.getPostId());

        if (retrievedPost == null) {
            errorLogger.error(TAG + "Error in finding Post with id : " + commentBean.getPostId());

            dataSaveResultBean.setStatus(0);
            dataSaveResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return dataSaveResultBean;
        }

        User retrievedUser = userService.findById(commentBean.getUserId());

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + commentBean.getUserId());

            dataSaveResultBean.setStatus(0);
            dataSaveResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return dataSaveResultBean;
        }

        Comment comment = new Comment(commentBean, retrievedPost, retrievedUser);

        try {
            commentRepository.save(comment);

            dataSaveResultBean.setId(comment.getId());

            try {
                retrievedPost.setCommentCount(retrievedPost.getCommentCount() + 1);
                entityManager.merge(retrievedPost);

                dataSaveResultBean.setStatus(1);
                dataSaveResultBean.setMessage(Constants.COMMENT_SAVED);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in updating commentCount for Post id : " + retrievedPost.getId() + " with error : " + e.getMessage());

                dataSaveResultBean.setStatus(0);
                dataSaveResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                return dataSaveResultBean;
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving Comment for User id : " + commentBean.getUserId() + " with error : " + e.getMessage());

            dataSaveResultBean.setStatus(0);
            dataSaveResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return dataSaveResultBean;
        }

        return dataSaveResultBean;
    }

    public CommentsBean findByPost(String postId, Pageable pageable) {
        CommentsBean postDetailsBean = new CommentsBean();

        Post retrievedPost = postService.findById(postId);

        if (retrievedPost == null) {
            errorLogger.error(TAG + "Error in finding Post with id : " + postId);

            postDetailsBean.setStatus(0);
            postDetailsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return postDetailsBean;
        }

        postDetailsBean.setCommentsPage(commentRepository.findByPost(retrievedPost, pageable));
        postDetailsBean.setStatus(1);
        return postDetailsBean;
    }

    public void updateUserComments(User primaryUser, User secondaryUser) {
        commentRepository.updateUserComments(primaryUser, secondaryUser);
    }
}

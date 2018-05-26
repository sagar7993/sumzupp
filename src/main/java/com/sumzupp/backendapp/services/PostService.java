package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.PostBean;
import com.sumzupp.backendapp.beans.PostVoteBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.entities.Post;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.entities.Vote;
import com.sumzupp.backendapp.entities.VoteType;
import com.sumzupp.backendapp.repositories.PostRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Service
public class PostService {
    private static final String TAG = "PostService : ";

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private VoteTypeService voteTypeService;

    @Autowired
    private VoteService voteService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Post findById(String id) {
        return postRepository.findOne(id);
    }

    public void saveAll(List<Post> posts) throws Exception {
        postRepository.save(posts);
    }

    @Transactional(rollbackFor = {Exception.class})
    public StatusBean save(PostBean postBean) {
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userService.findById(postBean.getUserId());

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + postBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        Post post = new Post(postBean, retrievedUser);

        try {
            postRepository.save(post);

            if (!CollectionUtils.isEmpty(postBean.getPostTags())) {
                try {
                    postTagService.savePostTags(post.getId(), postBean.getTagTypes());
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in saving PostTags for  : " + postBean.getUserId() + " with error : " + e.getMessage());

                    statusBean.setStatus(0);
                    statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                    return statusBean;
                }
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving Post for User id : " + postBean.getUserId() + " with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        statusBean.setStatus(1);
        statusBean.setMessage(Constants.POST_SAVED);
        return statusBean;
    }

    public StatusBean vote(PostVoteBean postVoteBean) {
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userService.findById(postVoteBean.getUserId());

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + postVoteBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        Post retrievedPost = postRepository.findOne(postVoteBean.getPostId());

        if (retrievedPost == null) {
            errorLogger.error(TAG + "Error in finding Post for id : " + postVoteBean.getPostId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        VoteType retrievedVoteType = voteTypeService.findByType(postVoteBean.getVoteType());

        if (retrievedVoteType == null) {
            errorLogger.error(TAG + "Error in finding VoteType for type : " + postVoteBean.getVoteType());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        Vote vote = new Vote(retrievedUser, retrievedPost, retrievedVoteType);

        try {
            voteService.save(vote);

            statusBean.setStatus(1);
            statusBean.setMessage(getVoteSavedMessage(postVoteBean.getVoteType()));
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving VoteType with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        return statusBean;
    }

    private String getVoteSavedMessage(int voteType) {
        switch (voteType) {
            case 1:
                return Constants.UPVOTED;
            case 2:
                return Constants.DOWNVOTED;
            case 3:
                return Constants.LIKED;
            case 4:
                return Constants.UNLIKED;
            default:
                return Constants.VOTED;
        }
    }

    public Page<PostBean> fetchAll(Pageable pageable) {
        return postRepository.fetchAll(pageable);
    }

    public void updateUserPosts(User primaryUser, User secondaryUser) {
        postRepository.updateUserPosts(primaryUser, secondaryUser);
    }
}

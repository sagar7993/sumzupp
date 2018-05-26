package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.Post;
import com.sumzupp.backendapp.entities.PostTag;
import com.sumzupp.backendapp.entities.TagType;
import com.sumzupp.backendapp.repositories.PostTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 05-Jun-17.
 */

@Service
public class PostTagService {
    private static final String TAG = "PostTagService : ";

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private TagTypeService tagTypeService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public void savePostTags(String postId, List<Integer> postTagTypes) throws Exception {
        Post post = postService.findById(postId);

        List<PostTag> postTags = new ArrayList<>();

        for (Integer postTagType : postTagTypes) {
            TagType retrievedTagType = tagTypeService.findByType(postTagType);

            if (retrievedTagType == null) {
                errorLogger.error("Error in finding TagType with type : " + postTagType);
                return;
            }

            postTags.add(new PostTag(post, retrievedTagType));
        }

        postTagRepository.save(postTags);
    }
}

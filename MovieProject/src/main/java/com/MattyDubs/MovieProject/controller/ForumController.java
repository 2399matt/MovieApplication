package com.MattyDubs.MovieProject.controller;


import com.MattyDubs.MovieProject.entity.CustomUser;
import com.MattyDubs.MovieProject.entity.Likes;
import com.MattyDubs.MovieProject.entity.Post;
import com.MattyDubs.MovieProject.entity.Reply;
import com.MattyDubs.MovieProject.security.MyUserDetails;
import com.MattyDubs.MovieProject.service.LikesServiceImpl;
import com.MattyDubs.MovieProject.service.PostService;
import com.MattyDubs.MovieProject.service.ReplyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ForumController is responsible for mapping all forum-related endpoints and actions.
 */
@Controller
@RequestMapping("/forum")
public class ForumController {

    //TODO Add validation on inputs. Probably should setup a requestMatcher for forums LOL.

    private final PostService postService;
    private final ReplyService replyService;
    private final LikesServiceImpl likesServiceImpl;

    @Autowired
    public ForumController(PostService postService, ReplyService replyService, LikesServiceImpl likesServiceImpl) {
        this.postService = postService;
        this.replyService = replyService;
        this.likesServiceImpl = likesServiceImpl;
    }

    /**
     * Mapping for the forum homepage. Forum posts are displayed on page load.
     *
     * @return The forum homepage.
     */
    @GetMapping("/home")
    public String homePage() {
        return "/forumPages/forumHome";
    }

    /**
     * forums endpoint used to list all the existing posts and their replies on the forum homepage.
     *
     * @param model The model.
     * @return The forum fragment for listing all posts.
     */
    @GetMapping("/forums")
    public String showForums(Model model) {
        model.addAttribute("forums", postService.findAllContent());
        return "/forumPages/forums :: forumFrag";
    }

    /**
     * viewPost endpoint, used to display a single post and all of its replies.
     *
     * @param id    id of the post to be shown.
     * @param model The model.
     * @return PostView fragment, which replaces the list of posts with the single post and its replies.
     */
    @GetMapping("/viewPost")
    public String showPost(@RequestParam("id") int id, Model model) {
        // n+1 Post post = postService.findById(id);
        Post post = postService.findPostUserAndReplies(id);
        model.addAttribute("post", post);
        return "/forumPages/PostView :: postView";
    }

    /**
     * postForm endpoint, used to display the form for creating a new post.
     *
     * @param model The model.
     * @return The form fragment for making a new post.
     */
    @GetMapping("postForm")
    public String postForm(Model model) {
        model.addAttribute("post", new Post());
        return "/forumPages/postForm :: postForm";
    }

    /**
     * savePost method used to save a new post to the database. postForm endpoint sends the POST.
     *
     * @param post        The post to be saved.
     * @param userDetails Current logged-in user.
     * @param model       The model.
     * @return The forum fragment for listing all posts.
     */
    @PostMapping("/savePost")
    public String savePost(@Valid @ModelAttribute("post") Post post, BindingResult br, @AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        if (br.hasErrors()) {
            return "/forumPages/postForm :: postForm";
        } else {
            CustomUser user = userDetails.getUser();
            postService.savePostForPage(user, post);
            Likes like = new Likes();
            likesServiceImpl.saveLikes(like, user, post);
            model.addAttribute("forums", postService.findAllContent());
            return "/forumPages/forums :: forumFrag";
        }
    }

    /**
     * replyForm method used to display the form for creating a new reply.
     *
     * @param postId The id of the post to which the reply is being made.
     * @param model  The model.
     * @return The form fragment for making a new reply.
     */
    @GetMapping("/replyForm")
    public String replyForm(@RequestParam(required = false, name = "badReply") boolean badReply, @RequestParam("postId") int postId, Model model) {
        model.addAttribute("badReply", badReply);
        model.addAttribute("reply", new Reply());
        model.addAttribute("postId", postId);
        return "/forumPages/replyForm :: replyForm";
    }

    /**
     * saveReply method used to save a new reply to the database.
     *
     * @param reply       The reply to be saved.
     * @param postId      The post that the reply belongs to.
     * @param model       The model.
     * @param userDetails Instance of current user.
     * @return The postView fragment, which displays the post and its replies.
     */
    @PostMapping("/saveReply")
    public String saveReply(@ModelAttribute("reply") Reply reply,
                            @RequestParam("postId") int postId, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        if (reply.getComment().isEmpty() || reply.getComment().length() > 255) {
            model.addAttribute("post", postService.findPostUserAndReplies(postId));
            model.addAttribute("badReply", true);
            return "/forumPages/PostView :: postView";
        }
        CustomUser user = userDetails.getUser();
        Post currPost = postService.findPostUserAndReplies(postId);
        replyService.saveReplyForPage(reply, user, currPost);
        model.addAttribute("post", currPost);
        return "/forumPages/PostView :: postView";
    }

    //TODO CHANGING TO CHECK WITH PRINCIPAL, MIGHT BREAK <<<<<<<<<<<<<<<<<<<<<

    @GetMapping("/upVote")
    public String upVote(@RequestParam(name = "id") int id, Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
        Post currPost = postService.findPostUserAndReplies(id);
        CustomUser user = userDetails.getUser();
        if (likesServiceImpl.findByUserAndPost(user, currPost)) {
            Likes like = new Likes();
            likesServiceImpl.saveLikes(like, user, currPost);
            currPost.addVote();
            postService.update(currPost);
        }
        model.addAttribute("post", currPost);
        return "/forumPages/PostView :: postView";
    }

    @GetMapping("/likes")
    public String likedPosts(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        CustomUser user = userDetails.getUser();
        List<Post> posts = likesServiceImpl.findPostsLikedByUser(user);
        model.addAttribute("forums", posts);
        return "/forumPages/forums :: forumFrag";

    }
}

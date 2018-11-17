package com.lee.pojo;

import javax.persistence.*;

@Table(name = "blog_vote")
public class BlogVote {
    @Column(name = "blog_id")
    private Long blogId;

    @Column(name = "vote_id")
    private Long voteId;

    /**
     * @return blog_id
     */
    public Long getBlogId() {
        return blogId;
    }

    /**
     * @param blogId
     */
    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    /**
     * @return vote_id
     */
    public Long getVoteId() {
        return voteId;
    }

    /**
     * @param voteId
     */
    public void setVoteId(Long voteId) {
        this.voteId = voteId;
    }
}
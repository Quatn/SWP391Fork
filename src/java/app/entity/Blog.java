/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.entity;

/**
 *
 * @author QuanNM
 */
public class Blog {
    Integer BlogId;
    Integer UserId;
    Integer BlogCategoryId;
    String BlogTitle;
    String UpdatedTime; 
    String PostText;
    String PostBrief;
    String PostThumbnail;

    public Blog() {
    }

    public Blog(Integer BlogId, Integer UserId, Integer BlogCategoryId, String BlogTitle, String UpdatedTime, String PostBrief) {
        this.BlogId = BlogId;
        this.UserId = UserId;
        this.BlogCategoryId = BlogCategoryId;
        this.BlogTitle = BlogTitle;
        this.UpdatedTime = UpdatedTime;
        this.PostBrief = PostBrief;
    }
    
    public Blog(Integer BlogId, Integer UserId, Integer BlogCategoryId, String BlogTitle, String UpdatedTime, String PostBrief, String PostThumbnail) {
        this.BlogId = BlogId;
        this.UserId = UserId;
        this.BlogCategoryId = BlogCategoryId;
        this.BlogTitle = BlogTitle;
        this.UpdatedTime = UpdatedTime;
        this.PostBrief = PostBrief;
        this.PostThumbnail = PostThumbnail;
    }

    public Integer getBlogId() {
        return BlogId;
    }

    public void setBlogId(Integer BlogId) {
        this.BlogId = BlogId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer UserId) {
        this.UserId = UserId;
    }

    public Integer getBlogCategoryId() {
        return BlogCategoryId;
    }

    public void setBlogCategoryId(Integer BlogCategoryId) {
        this.BlogCategoryId = BlogCategoryId;
    }

    public String getBlogTitle() {
        return BlogTitle;
    }

    public void setBlogTitle(String BlogTitle) {
        this.BlogTitle = BlogTitle;
    }

    public String getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(String UpdatedTime) {
        this.UpdatedTime = UpdatedTime;
    }

    public String getPostText() {
        return PostText;
    }

    public void setPostText(String PostText) {
        this.PostText = PostText;
    }

    public String getPostBrief() {
        return PostBrief;
    }

    public void setPostBrief(String PostBrief) {
        this.PostBrief = PostBrief;
    }

    public String getPostThumbnail() {
        return PostThumbnail;
    }

    public void setPostThumbnail(String PostThumbnail) {
        this.PostThumbnail = PostThumbnail;
    }

    @Override
    public String toString() {
        return "Blog{" + "BlogId=" + BlogId + ", UserId=" + UserId + ", BlogCategoryId=" + BlogCategoryId + ", BlogTitle=" + BlogTitle + ", UpdatedTime=" + UpdatedTime + ", PostText=" + PostText + ", PostBrief=" + PostBrief + '}';
    }
}

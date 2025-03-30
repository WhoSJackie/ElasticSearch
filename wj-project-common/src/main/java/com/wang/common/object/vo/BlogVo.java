package com.wang.common.object.vo;

import com.wang.common.object.entity.BlogSort;
import com.wang.common.object.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class BlogVo extends BaseVo<BlogVo> {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一oid【自动递增】
     */
    private Integer oid;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 博客简介
     * updateStrategy = FieldStrategy.IGNORED ：表示更新时候忽略非空判断
     */
    private String summary;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 标签uid
     */
    private String tagUid;

    /**
     * 博客分类UID
     */
    private String blogSortUid;

    /**
     * 博客点击数
     */
    private Integer clickCount;

    /**
     * 博客收藏数
     */
    private Integer collectCount;

    /**
     * 标题图片UID
     */
    private String fileUid;

    /**
     * 管理员UID
     */
    private String adminUid;

    /**
     * 是否发布
     */
    private String isPublish;

    /**
     * 是否原创
     */
    private String isOriginal;

    /**
     * 如果原创，作者为管理员名
     */
    private String author;

    /**
     * 文章出处
     */
    private String articlesPart;

    /**
     * 推荐级别，用于首页推荐
     * 0：正常
     * 1：一级推荐(轮播图)
     * 2：二级推荐(top)
     * 3：三级推荐 ()
     * 4：四级 推荐 (特别推荐)
     */
    private Integer level;

    /**
     * 排序字段，数值越大，越靠前
     */
    private Integer sort;

    /**
     * 是否开启评论(0:否， 1:是)
     */
    private String openComment;

    /**
     * 文章类型【0 博客， 1：推广】
     */
    private String type;

    /**
     * 外链【如果是推广，那么将跳转到外链】
     */
    private String outsideLink;


    // 以下字段不存入数据库，封装为了方便使用


    private List<Tag> tagList;


    private List<String> photoList;


    private BlogSort blogSort;


    private String blogSortName;


    private String photoUrl;


    private Integer praiseCount;


    private String copyright;

    /**
     * 博客等级关键字，仅用于getList
     */
    private String levelKeyword;

    /**
     * 使用Sort字段进行排序 （0：不使用， 1：使用），默认为0
     */
    private Integer useSort;

    /**
     * OrderBy排序字段（desc: 降序）
     */
    private String orderByDescColumn;

    /**
     * OrderBy排序字段（asc: 升序）
     */
    private String orderByAscColumn;

    public BlogVo(){
        this.useSort = 0;
    }

}

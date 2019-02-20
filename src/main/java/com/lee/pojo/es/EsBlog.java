package com.lee.pojo.es;

import com.lee.vo.BlogVo;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import javax.persistence.Id;
import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * elasticSearch博客索引类
 *
 * 索引(index) = 数据库(databases)
 * 类型(type) = 表(table)
 * 文档(document) = 行(row)
 * 一个 Elasticsearch 集群可以包含多个 索引(数据库),相应的每个索引可以包含多个 类型(表) 。这些不同的类型存储着多个 文档(一条记录),每个文档又有多个 属性(字段) 。
 *
 * 1、text 数据类型不能用来排序和聚合，可分词！keyword 类型字段用来检索过滤、排序和聚合，不可分词！
 * 2、index=true (默认值)字段需要被搜索,否则不能用于搜索
 * 3、store=true (默认为false) 不管设置为true or false, es都将为我们存储这些field，意义：是否在 _source 之外在独立存储一份
 * 详见https://blog.csdn.net/helllochun/article/details/52136954
 */
@Data
@Document(indexName = "wx_blog", type = "wx_blog")  //指定索引和类型
public class EsBlog implements Serializable {
    private static final long serialVersionUID = 1L;

    // 文档 ID
    @Id
    private String id; // 文档 ID = blogId
    @Field(type = FieldType.Long)
    private Long blogId; // Blog 实体的 id

    //text 数据类型不能用来排序和聚合，可分词！  analyzer：存入时的分词器  searchAnalyzer：搜索时的分词器
    @Field(index= true,type = FieldType.text,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String title;
    @Field(index= true,type = FieldType.text,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String summary;
    @Field(index= true,type = FieldType.text,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String content;
    @Field(index= true,type = FieldType.text,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String category;


    //====================================不做全文检索字段===================================
    @Field(type = FieldType.keyword, index = false) //keyword 类型字段用来检索过滤、排序和聚合，不可分词！
    private String userName;
    @Field(type = FieldType.text, index = false)
    private String avatar;
    @Field(type = FieldType.text, index = false)
    private String imageList;

    @Field(type = FieldType.text, index = false)  // 日期类型
    private String createTime;

    @Field(type = FieldType.Integer, index = false)
    private Integer markSize = 0; // 收藏量
    @Field(type = FieldType.Integer, index = false)
    private Integer commentSize = 0;  // 评论量
    @Field(type = FieldType.Integer, index = false)
    private Integer voteSize = 0;  // 点赞量

    // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
    protected EsBlog() {
    }

    public EsBlog(BlogVo blogVo){
        this.id = blogVo.getId().toString();
        this.blogId = blogVo.getId();
        this.title = blogVo.getTitle();
        this.summary = blogVo.getSummary();
        this.content = blogVo.getContent();
        this.category = blogVo.getCategory();
        this.userName = blogVo.getUserName();
        this.avatar = blogVo.getAvatar();
        this.imageList = blogVo.getImageList();
        //日期格式Date->String
        this.createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(blogVo.getCreateTime());
        this.markSize = blogVo.getMarkSize();
        this.commentSize = blogVo.getCommentSize();
        this.voteSize = blogVo.getVoteSize();
    }

}

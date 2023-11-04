package com.wang.service.impl;

import com.wang.EsConstants;
import com.wang.pojo.JdContent;
import com.wang.pojo.QueryJdContentRes;
import com.wang.service.JdContentService;
import com.wang.util.JsonUtil;
import com.wang.util.JsoupUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class JdContentServiceImpl implements JdContentService {

    @Autowired
    RestHighLevelClient restHighLevelClient;


    @Override
    public boolean saveJdContent(String keyWord) {
        // 获取数据
        BulkResponse response = null;
        try{
            List<JdContent> jdInfo = JsoupUtil.getJDInfo(keyWord);
            BulkRequest request = new BulkRequest();
            request.timeout("2m");
            for (int i = 0; i < jdInfo.size(); i++) {
                System.out.println(JsonUtil.toJsonString(jdInfo.get(i)));
                request.add(new IndexRequest("wang_index").id(""+(i+1)).source(JsonUtil.toJsonString(jdInfo.get(i)), XContentType.JSON));
            }
            response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e){
            e.printStackTrace();
        }
        return response!=null&&!response.hasFailures();
    }

    @Override
    public QueryJdContentRes queryJdContent(String keyWord, int pageNo, int pageSize) throws IOException {
        // 创建查询请求对象
        SearchRequest request = new SearchRequest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 查询条件
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title",keyWord);
        searchSourceBuilder.query(termQueryBuilder);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 分页
        searchSourceBuilder.from(pageNo*EsConstants.PAGE_SIZE_DEFAULT);
        searchSourceBuilder.size(pageSize);


        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);

        // 放入查询请求对象
        request.source(searchSourceBuilder);
        // 客户端查询请求
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        // restHighLevelClient.close();
        List<Map<String,Object>> map = new ArrayList<>();
        for (SearchHit searchHit : search.getHits().getHits()) {
            Map<String,Object> sourceMap = searchHit.getSourceAsMap();
            //  替换title的高亮字段
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            if (title!=null){
                Text[] fragments = title.fragments();
                StringBuilder sb = new StringBuilder();
                for (Text fragment : fragments) {
                    sb.append(fragment);
                }
                sourceMap.put("title",sb.toString());
            }
            map.add(sourceMap);
        }
        QueryJdContentRes dto = new QueryJdContentRes();
        dto.setContent(map);
        dto.setTotal(map.size());
        return dto;
    }


}

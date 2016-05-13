package com.doaim;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by Doaim on 16/5/13.
 */
public class ElasticSearchHandler {

    private Client client;

    public ElasticSearchHandler(){
        this("192.168.3.230");
    }
    public ElasticSearchHandler(String ipAddress){
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "LH-ES_CLUSTER")
                .put("client.transport.sniff",true)
                .build();

        client = TransportClient.builder().settings(settings).build()
                .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("192.168.3.230",9300)));
    }

    /**
     * 创建索引
     * @param jsondata
     * @return
     */
    public IndexResponse createIndex(String indexname, String type, String jsondata){
        IndexResponse response = client.prepareIndex(indexname, type)
                .setSource(jsondata)
                .execute()
                .actionGet();
        return response;
    }

    /**
     * 执行搜索
     * @param queryBuilder
     * @param indexname
     * @param type
     * @return
     */
    public SearchResponse  searche(QueryBuilder queryBuilder, String indexname, String type){
        SearchResponse searchResponse = client.prepareSearch(indexname).setTypes(type)
                .setQuery(queryBuilder)
                .execute()
                .actionGet();
        return searchResponse;
    }
}
package com.ademozalp.elasticsearch.util;

import co.elastic.clients.elasticsearch._types.query_dsl.*;

import java.util.function.Supplier;

public class ESUtil {

    public static Query createMatchAllQuery(){
        return  Query.of(q -> q.matchAll(new MatchAllQuery.Builder().build()));
    }

    public static Supplier<Query> buildQueryForFieldAndValue(String fieldName, String searchValue){
      return () -> Query.of(q-> q.match(buildMatchQueryForFieldAndValue(fieldName, searchValue)));
    }


    private static MatchQuery buildMatchQueryForFieldAndValue(String fieldName, String searchValue){
        return new MatchQuery.Builder()
                .field(fieldName)
                .query(searchValue)
                .build();
    }

    public static Supplier<Query> createBoolQuery(String field, String value){
            return  () -> Query.of(q -> q.bool(boolQuery(field, value)));
    }


    private static BoolQuery boolQuery(String key, String value){
        return new BoolQuery.Builder()
                .filter(termQuery(key, value))
                .must(matchQuery(key, value))
                .build();
    }

    private static Query termQuery(String field, String value){
        return Query.of(q -> q.term(new TermQuery.Builder()
                .field(field)
                .value(value)
                .build()));
    }

    private static Query matchQuery(String field, String value){
        return Query.of(q -> q.match(new MatchQuery.Builder()
                .field(field)
                .query(value)
                .build()));
    }

    public static Query buildAutoSuggestQuery(String name){
        return Query.of(q -> q.match(createAutoSuggestMatchQuery(name)));
    }

    private static MatchQuery createAutoSuggestMatchQuery(String name) {
        return new MatchQuery.Builder()
                .field("name")
                .query(name)
                .analyzer("custom_index")
                .build();
    }

}

package com.example.tobyl.databaseviewer;

import java.util.ArrayList;

/**
 * Created by tobyl on 8/23/2017.
 */

public class Query {
    String name;
    String sql;
    int access_level;

    static Query showTable = new Query("Show Table","show table;",0);
    static Query actorTable = new Query("Actor","select * from actor;",1);
    static Query CityTable = new Query("City","select * from ORDERM",1);

    static Query[] queries = {showTable,actorTable,CityTable};

    public Query(String name,String sql,int access_level){
        this.name = name;
        this.sql = sql;
        this.access_level = access_level;
    }


    public static Query[] getQueries(int access_level) {
        ArrayList<Query> queries_list = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            if (queries[i].access_level == access_level) {
                queries_list.add(queries[i]);
            }
        }
        Query[] queries_array = new Query[queries_list.size()];
        for (int i = 0; i < queries_list.size(); i++) {
            queries_array[i] = queries_list.get(i);
        }
        return queries_array;
    }


    public String toString(){return name;}
    public String getName(){return name;}
    public String getSql(){return sql;}
    public int getAccess_level(){return access_level;}
}

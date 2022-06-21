package com.hua;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTX {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);

        jedis.flushDB();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        jsonObject.put("name", "hua");

        Transaction multi = jedis.multi();
        String res = jsonObject.toJSONString();
//        jedis.watch(res);
        try {
            multi.set("user", res);
            multi.set("user1", res);
            int i = 1 / 0;
            multi.exec();
        } catch (Exception e) {
            multi.discard();
            e.printStackTrace();
        } finally {
            System.out.println(jedis.get("user"));
            System.out.println(jedis.get("user1"));
            jedis.close();
        }
    }
}

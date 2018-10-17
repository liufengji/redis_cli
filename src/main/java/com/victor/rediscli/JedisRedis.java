package com.victor.rediscli;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisRedis {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.2.102", 6379);

        System.out.println( jedis.ping());

        jedis.set("k1000", "v1000");
        System.out.println(jedis.get("k1000"));

        Set<String> keys = jedis.keys("*");

        for (String string : keys) {
            System.out.println(string);
        }
        jedis.close();
    }
}

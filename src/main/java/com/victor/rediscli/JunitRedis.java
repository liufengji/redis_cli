package com.victor.rediscli;

import java.io.IOException;
import java.util.*;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

public class JunitRedis {

    @Test
    public  void connection() {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //查看服务是否运行，打出pong表示OK
        System.out.println("connection is OK==========>: "+jedis.ping());
    }

    @Test
    public  void Jedis() {

        Jedis jedis = new Jedis("192.168.67.143", 6379);

        System.out.println(jedis.ping());

        jedis.set("k1000", "v1000");

        System.out.println(jedis.get("k1000"));

        Set<String> keys = jedis.keys("*");

        for (String string : keys) {
            System.out.println(string);
        }

        jedis.close();

    }

    //Redis集群的Jedis开发
    @Test
    public static void JedisSet() {

        try {
            Set<HostAndPort> nodes = new HashSet<HostAndPort>();

            nodes.add(new HostAndPort("192.168.67.143", 6391));

            @SuppressWarnings("resource")
            JedisCluster jedisCluster = new JedisCluster(nodes);

            jedisCluster.set("k1000", "v1000");

            System.out.println(jedisCluster.get("k1000"));

            jedisCluster.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public static void Jedis_key_API() {

        Jedis jedis = new Jedis("192.168.67.143", 6379);

        Set<String> keys = jedis.keys("*");
        for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            System.out.println(key);
        }
        System.out.println("jedis.exists====>"+jedis.exists("k2"));
        System.out.println(jedis.ttl("k1"));

        jedis.close();

    }

    @Test
    public static void Jedis_String_API() {

        Jedis jedis = new Jedis("192.168.67.143", 6379);

        System.out.println(jedis.get("k1"));
        jedis.set("k4","k4_Redis");

        System.out.println("----------------------------------------");

        jedis.mset("str1","v1","str2","v2","str3","v3");
        System.out.println(jedis.mget("str1","str2","str3"));

        jedis.close();
    }

    @Test
    public static void Jedis_List_API() {

        Jedis jedis = new Jedis("192.168.67.143", 6379);

        List<String> list = jedis.lrange("mylist",0,-1);
        for (String element : list) {
            System.out.println(element);
        }

        jedis.close();
    }

    @Test
    public static void Jedis_set_API() {

        Jedis jedis = new Jedis("192.168.67.143", 6379);

        jedis.sadd("orders","jd001");
        jedis.sadd("orders","jd002");
        jedis.sadd("orders","jd003");
        Set<String> set1 = jedis.smembers("orders");
        for (Iterator iterator = set1.iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            System.out.println(string);
        }
        jedis.srem("orders","jd002");

        jedis.close();
    }

    @Test
    public static void Jedis_hash_API() {

        Jedis jedis = new Jedis("192.168.67.143", 6379);

        jedis.hset("hash1","userName","lisi");
        System.out.println(jedis.hget("hash1","userName"));
        Map<String,String> map = new HashMap<String,String>();
        map.put("telphone","13810169999");
        map.put("address","victor");
        map.put("email","liufengji@aliyun.com");
        jedis.hmset("hash2",map);
        List<String> result = jedis.hmget("hash2", "telphone","email");
        for (String element : result) {
            System.out.println(element);
        }

        jedis.close();
    }

    @Test
    public static void Jedis_zset_API() {

        Jedis jedis = new Jedis("192.168.67.143", 6379);

        jedis.zadd("zset01",60d,"v1");
        jedis.zadd("zset01",70d,"v2");
        jedis.zadd("zset01",80d,"v3");
        jedis.zadd("zset01",90d,"v4");

        Set<String> s1 = jedis.zrange("zset01",0,-1);

        for (Iterator iterator = s1.iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            System.out.println(string);
        }

        jedis.close();
    }

}

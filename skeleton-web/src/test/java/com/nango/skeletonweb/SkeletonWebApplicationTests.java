package com.nango.skeletonweb;

import com.nango.skeletonweb.application.redisDemo.SimpleRedisLockService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@SpringBootTest
@Slf4j
class SkeletonWebApplicationTests {

    @Resource
    private SimpleRedisLockService simpleRedisLockService;

    private final BlockingQueue<String> bQueue = new ArrayBlockingQueue(5);

    @Test
    void contextLoads() {
    }


    @Test
    void blockingQueueTest() {

        bQueue.add("123");
    }

    private int productCnt = 20;

    private Random random = new Random(10);
    @Test
    void simpleLockTest() {
        for (int i=0;i<100;i++) {
            new Thread(() -> {
                String uuid = UUID.randomUUID().toString();
                Boolean lock = simpleRedisLockService.getLock("product-1", uuid, 2L);
                try {
                    if (lock) {
                        System.out.println(">>>" + Thread.currentThread().getName() + "余量>>>" + productCnt);
                        if (productCnt > 0) {
                            try {
                                //模拟业务操作耗时
                                Thread.sleep(random.nextInt(10));
                                productCnt = productCnt-1;
                            } catch (InterruptedException e) {
                                log.error("InterruptedException >>>> {}", e);
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error(">>>> {}", e);
                } finally {
                    if (lock) {
                        simpleRedisLockService.releaseLock("product-1", uuid);
                    }
                }


            }).start();
        }

        try {
            Thread.sleep(2000);
            System.out.println("剩余数量" + productCnt);
        } catch (InterruptedException e) {

        }

    }
}

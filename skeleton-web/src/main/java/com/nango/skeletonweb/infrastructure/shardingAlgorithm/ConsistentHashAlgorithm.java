package com.nango.skeletonweb.infrastructure.shardingAlgorithm;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @Author nango
 * @Date 2020/12/16
 * @Description 一致性hash算法
 */
@Slf4j
public class ConsistentHashAlgorithm {


    /**
     * 虚拟节点，key表示虚拟节点的hash值，value表示虚拟节点的名称
     */

    @Getter
    private SortedMap<Long, String> virtualNodes = new TreeMap<>();

    //虚拟节点的数目
    private static final int VIRTUAL_NODES = 1;

    private static final String SUB_STRING_KEY = "-";


    public ConsistentHashAlgorithm() {
    }


    public ConsistentHashAlgorithm(SortedMap<Long, String> virtualTableNodes, Collection<String> tableNodes) {
        if (Objects.isNull(virtualTableNodes)) {
            virtualTableNodes = initNodesToHashLoop(tableNodes);
        }

        this.virtualNodes = virtualTableNodes;
    }

    public SortedMap<Long, String> initNodesToHashLoop(Collection<String> tableNodes) {
        SortedMap<Long, String> virtualTableNodes = new TreeMap<>();
        for (String node : tableNodes) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                String s = String.valueOf(i);
                String virtualNodeName = node + "-VN" + s;
                long hash = getHash(virtualNodeName);
                log.debug("虚拟节点[" + virtualNodeName + "]被添加, hash值为" + hash);
                virtualTableNodes.put(hash, virtualNodeName);
            }
        }

        return virtualTableNodes;
    }

    /**
     * 通过计算key的hash
     * 计算映射的表节点
     *
     * @param key
     * @return
     */
    public String getTableNode(String key) {
        String virtualNode = getVirtualTableNode(key);
        //虚拟节点名称截取后获取真实节点
        if (StringUtils.isNotBlank(virtualNode)) {
            return virtualNode.substring(0, virtualNode.indexOf(SUB_STRING_KEY));
        }
        return null;
    }

    /**
     * 获取虚拟节点
     * @param key
     * @return
     */
    public String getVirtualTableNode(String key) {
        long hash = getHash(key);
        // 得到大于该Hash值的所有Map
        SortedMap<Long, String> subMap = virtualNodes.tailMap(hash);
        String virtualNode;
        if (subMap.isEmpty()) {
            //如果没有比该key的hash值大的，则从第一个node开始
            Long i = virtualNodes.firstKey();
            //返回对应的服务器
            virtualNode = virtualNodes.get(i);
        } else {
            //第一个Key就是顺时针过去离node最近的那个结点
            Long i = subMap.firstKey();
            //返回对应的服务器
            virtualNode = subMap.get(i);
        }

        return virtualNode;
    }

    /**
     * 使用FNV1_32_HASH算法计算key的Hash值
     *
     * @param key
     * @return
     */
    public long getHash(String key) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash ^ key.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;

        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }

}

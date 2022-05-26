package com.nango.skeletonweb.infrastructure.canal;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * @author nango
 * @package com.nango.skeletonweb.infrastructure.canalDataSyncBusiness
 * @class CanalClient
 * @date 2022/5/5 21:45
 * @description canalDataSyncBusiness client
 */
@Component
@Slf4j
public class CanalClient
        implements DisposableBean {
    @Value("${canalServer.host}")
    private String canalServerHost;

    @Value("${canalServer.port}")
    private Integer canalServerPort;

    @Value("${canalServer.destination}")
    private String destination;

    @Value("${canalServer.userName}")
    private String userName;

    @Value("${canalServer.password}")
    private String password;

    private transient CanalConnector canalConnector;

    /**
     * 获取canal链接
     * @return
     */
    public CanalConnector getCanalConnector() {

        if (!this.checkConnValid()) {
            synchronized (this) {
                if (!this.checkConnValid()) {
                    // 创建链接
                    this.canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress(canalServerHost,
                            canalServerPort), destination, userName, password);
                }
            }
        }


        return canalConnector;
    }

    /**
     * 判断是否合法链接
     * @return
     */
    public boolean checkConnValid() {
        return canalConnector != null && canalConnector.checkValid();
    }

    /**
     * Invoked by the containing {@code BeanFactory} on destruction of a bean.
     *
     * @throws Exception in case of shutdown errors. Exceptions will get logged
     *                   but not rethrown to allow other beans to release their resources as well.
     */
    @Override
    public void destroy() {
        if (this.checkConnValid()) {
            this.canalConnector.disconnect();
            this.canalConnector = null;
        }
    }
}

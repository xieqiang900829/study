package com.redisson;

import org.junit.Test;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;

public class TestServerDeploy {

    @Test
    public void  deploy(){
        Config config = new Config();
        SentinelServersConfig server = config.useSentinelServers();

    }
}

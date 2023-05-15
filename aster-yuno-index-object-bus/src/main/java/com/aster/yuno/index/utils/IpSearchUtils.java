package com.aster.yuno.index.utils;

import cn.hutool.core.io.FileUtil;
import com.aster.yuno.index.bo.IpAddressObj;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class IpSearchUtils {

    @Resource
    private ResourceLoader resourceLoader;

    private static String REGION_PATH;

    private static byte[] REGION_INDEX;

    @PostConstruct
    public void init() {
        try {
            InputStream in = resourceLoader.getResource("classpath:xdb/ip2region.xdb").getInputStream();
            File localIp2RegFile = FileUtil.writeFromStream(in, "localIp2Reg.xdb");
            in.close();
            REGION_PATH = localIp2RegFile.getPath();
            REGION_INDEX = Searcher.loadVectorIndexFromFile(REGION_PATH);
            log.info("[op:IpSearchUtils:init] ip package builder successful");
        } catch (Exception ex) {
            log.warn("[op:IpSearchUtils:init] ip package resources not found");
        }

    }

    public static String getIpName(String ip) {
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithVectorIndex(REGION_PATH, REGION_INDEX);
            String region = searcher.search(ip);
            searcher.close();
            IpAddressObj ipAddressObj = new IpAddressObj(region);
            if ("中国".equals(ipAddressObj.getCountry())) {
                return ipAddressObj.getProvince() + ipAddressObj.getCity();
            } else {
                return ipAddressObj.getCountry();
            }
        } catch (Exception ex) {
            log.error("[op:IpSearchUtils:init] ip [{}] convert fail", ip);
            ex.printStackTrace();
            if (null != searcher) {
                try {
                    searcher.close();
                } catch (IOException exception) {
                    log.error("[op:IpSearchUtils:init] ip package builder close fail");
                    exception.printStackTrace();
                }
            }
        }
        return null;
    }

}

package com.deye.xl.postBody;

import com.alibaba.fastjson.JSON;
import com.deye.xl.entity.XLBaseData;
import com.deye.xl.entity.XLRealtimeData;
import com.yzw.ibuild.open.api.IbuildOpenClient;
import com.yzw.ibuild.open.api.model.unloadingplatform.UnloadingPlatformDeviceParams;
import com.yzw.ibuild.open.api.model.unloadingplatform.UnloadingPlatformLiveData;
import com.yzw.ibuild.open.api.request.UnloadingPlatformDeviceParamsUploadRequest;
import com.yzw.ibuild.open.api.request.UnloadingPlatformLiveDataUploadRequest;
import com.yzw.ibuild.open.api.response.IbuildOpenResponse;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 筑智云
 */
@Component
public class ZZYPost {

    private static final Logger log = LoggerFactory.getLogger(ZZYPost.class);

    /**
     * 基础参数
     */
    public void BaseDataUnloadingPlatform(XLBaseData baseDataCrane) {
        UnloadingPlatformDeviceParamsUploadRequest request = new UnloadingPlatformDeviceParamsUploadRequest();
        UnloadingPlatformDeviceParams unloadingPlatformDeviceParams = new UnloadingPlatformDeviceParams();
        unloadingPlatformDeviceParams.setDeviceId("construction");
        unloadingPlatformDeviceParams.setStdLoadWeight(50.0);
        request.setUnloadingPlatformDeviceParams(unloadingPlatformDeviceParams);
        try {
            IbuildOpenClient client = new IbuildOpenClient("appId", "appSecret");
            IbuildOpenResponse response = client.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 实时数据
     */
    public void RealtimeDataElevator(XLRealtimeData xlRealtimeData) {
        UnloadingPlatformLiveDataUploadRequest request
                = new UnloadingPlatformLiveDataUploadRequest();
        UnloadingPlatformLiveData unloadingPlatformLiveData = new UnloadingPlatformLiveData();
        unloadingPlatformLiveData.setDeviceId("unloading-Test");
        unloadingPlatformLiveData.setDeviceTime(new Date());
        unloadingPlatformLiveData.setWarning("01010101");
        unloadingPlatformLiveData.setLoadWeight(123.2);
        request.setUnloadingPlatformLiveData(unloadingPlatformLiveData);
        try {
            IbuildOpenClient client = new IbuildOpenClient("appId", "appSecret");
            IbuildOpenResponse response = client.execute(request);
            System.out.println(JSON.toJSONString(response));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

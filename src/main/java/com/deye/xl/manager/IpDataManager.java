package com.deye.demo.manager;

import com.deye.demo.entity.IpData;
import com.deye.demo.repo.IpDataRepository;
import com.deye.demo.util.DateUtils;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpDataManager {

    @Autowired
    IpDataRepository ipDataRepository;

    public void save(IpData ipData) {
        ipDataRepository.save(ipData);
    }

    /**
     * 获取所有IpData
     */
    public List<IpData> getIpDatas() {

        return ipDataRepository.getIpDatas();
    }

    /**
     * 根据 HxzFactory HxzId 查找ip
     */
    public IpData getIpByHxzIdAndHxzFactory(String hxzFactory, String hxzId) {
        return ipDataRepository.getIpDataByHxzFactory(hxzFactory, hxzId);
    }

    /**
     * 更新ip时间 ip
     */
    public void upDateIp(String hxzIp, String hxzFactory, String hxzId) {
        String dateTime = DateUtils.getCurrentTime();
        ipDataRepository.upDateIp(hxzIp, dateTime, hxzFactory, hxzId);
    }

    public void pDownlineVF(String hxzFactory, String hxzId) {
        ipDataRepository.pDownlineVF(hxzFactory, hxzId);
    }


    public String getdateTime(String hxzFactory, String hxzId) {
        return ipDataRepository.getdateTime(hxzFactory, hxzId);

    }
}

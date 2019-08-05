package com.deye.demo.manager;


import com.deye.demo.entity.TowerCrane;
import com.deye.demo.repo.TowerCraneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TowerCraneManager {

    @Autowired
    TowerCraneRepository towerCraneRepository;

    public TowerCrane getTowerCraneByHxzIdAndHxzFactory(String hxzFactory, String hxzId) {
        return towerCraneRepository.getTowerCraneDataByHxzFactory(hxzFactory, hxzId);
    }

    public String getProjectid(String hxzFactory, String hxzId) {
        return towerCraneRepository.getProjectid(hxzFactory, hxzId);
    }

    /*
    获取备注 海南接口获取视频系列号
     */
    public String getRemark(String hxzFactory, String hxzId) {
        return towerCraneRepository.getRemark(hxzFactory, hxzId);
    }
}

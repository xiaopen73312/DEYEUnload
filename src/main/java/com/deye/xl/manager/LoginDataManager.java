package com.deye.xl.manager;


import com.deye.xl.entity.LoginData;
import com.deye.xl.repo.LoginDataRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginDataManager {

    @Autowired
    LoginDataRepository loginDataRepository;

    public List<LoginData> getAllLoginDatas() {
        return loginDataRepository.findAll();
    }

    //所有在线数据 Online='1'
    public List<LoginData> getAllOnlineLoginDatas() {
        return loginDataRepository.getAllOnLine();
    }

    /**
     * 保存loginData数据
     */
    public void save(LoginData loginData) {
        loginDataRepository.save(loginData);
    }


    public String getOnLine(String hxzFactory, String hxzId) {
        return loginDataRepository.getOnLine(hxzFactory, hxzId);
    }

    /**
     * 通过黑匣子获取 登录信息
     */
    public LoginData getLoginDataByHxzIdAndHxzFactory(String hxzFactory, String hxzId) {
        return loginDataRepository.getLoginDataByHxzFactory(hxzFactory, hxzId);
    }

    /**
     * OnLine='1', Type=1
     */
    public void upDateLoginData(String HardwareVer, String SoftwareVer,
            String SimCardNo, String hxzFactory,
            String hxzId, String online, int type) {

        loginDataRepository.upLoginData(HardwareVer,
                SoftwareVer,
                SimCardNo,
                hxzFactory,
                hxzId, online, type);
    }

    public void upOnlineFlg(String hxzFactory,
            String hxzId, String online) {
        loginDataRepository.upOnlineFlg(
                hxzFactory,
                hxzId, online);
    }
}

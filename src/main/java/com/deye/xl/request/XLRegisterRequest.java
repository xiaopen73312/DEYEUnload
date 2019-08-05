package com.deye.demo.request;

/**
 * 注册请求参数
 */
public class RegisterRequest {

    String hxzFactory; //黑匣子厂家 大写英文缩写 DEYE
    String hxzId;//黑匣子编号 格式YYMMDDXX 19012104
    String protocolVer;//协议版本号 V2.F
    String Cmd;//命令 0
    String hardwareVer;//硬件版本号 DYL-I-TC-H-V2.00
    String softwareVer;//软件版本号 DYL-I-TC-S-V4.10
    String simCardNo;//SIM卡号 133970797906

    public String getHxzFactory() {
        return hxzFactory;
    }

    public void setHxzFactory(String hxzFactory) {
        this.hxzFactory = hxzFactory;
    }

    public String getHxzId() {
        return hxzId;
    }

    public void setHxzId(String hxzId) {
        this.hxzId = hxzId;
    }

    public String getProtocolVer() {
        return protocolVer;
    }

    public void setProtocolVer(String protocolVer) {
        this.protocolVer = protocolVer;
    }

    public String getCmd() {
        return Cmd;
    }

    public void setCmd(String cmd) {
        Cmd = cmd;
    }

    public String getHardwareVer() {
        return hardwareVer;
    }

    public void setHardwareVer(String hardwareVer) {
        this.hardwareVer = hardwareVer;
    }

    public String getSoftwareVer() {
        return softwareVer;
    }

    public void setSoftwareVer(String softwareVer) {
        this.softwareVer = softwareVer;
    }

    public String getSimCardNo() {
        return simCardNo;
    }

    public void setSimCardNo(String simCardNo) {
        this.simCardNo = simCardNo;
    }
}

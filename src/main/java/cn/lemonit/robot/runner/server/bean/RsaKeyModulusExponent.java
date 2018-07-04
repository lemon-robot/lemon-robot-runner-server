package cn.lemonit.robot.runner.server.bean;

import org.apache.commons.codec.binary.Hex;

import java.security.interfaces.RSAPublicKey;

/**
 * RSA密钥的模和系数组合
 *
 * @author LemonIT.CN
 */
public class RsaKeyModulusExponent {

    /**
     * 模
     */
    private String modules;
    /**
     * 系数
     */
    private String exponent;

    public RsaKeyModulusExponent(RSAPublicKey publicKey) {
        this.modules = Hex.encodeHexString(publicKey.getModulus().toByteArray());
        this.exponent = Hex.encodeHexString(publicKey.getPublicExponent().toByteArray());
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getExponent() {
        return exponent;
    }

    public void setExponent(String exponent) {
        this.exponent = exponent;
    }
}

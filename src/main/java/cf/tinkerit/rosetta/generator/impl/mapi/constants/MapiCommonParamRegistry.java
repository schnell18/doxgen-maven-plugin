package cf.tinkerit.rosetta.generator.impl.mapi.constants;

import java.util.HashMap;
import java.util.Map;

public class MapiCommonParamRegistry {

    private static final Map<String, String> nameDescMap = new HashMap<String, String>(60);

    static  {
        nameDescMap.put("format","format 返回值格式,取值为枚举SerializeType中的定义,取值范围JSON/XML");
        nameDescMap.put("location", "_lo");
        nameDescMap.put("token", "user token 代表访问者身份,完成用户登入流程后获取");
        nameDescMap.put("deviceToken", "device token 代表访问设备的身份,完成设备注册流程后获取");
        nameDescMap.put("webDeviceToken", "web device token 代表访问设备的身份,完成设备注册流程后获取");
        nameDescMap.put("method", "method 请求的资源名");
        nameDescMap.put("signature", "signature 参数字符串签名");
        nameDescMap.put("applicationId","application id 应用编号,访问查看具体定义http://doc.tinkerit.cf/pages/viewpage.action?pageId=983175");
        nameDescMap.put("callId","call id 客户端调用编号");
        nameDescMap.put("deviceId","device id 设备标示符");
        nameDescMap.put("userId","user id 用户标示符");
        nameDescMap.put("clientIp","client ip 用户ip");
        nameDescMap.put("versionCode","version code 客户端数字版本号");
        nameDescMap.put("signatureMethod","signature method 签名算法 hmac,md5,sha1,rsa,ecc;https请求上送na表示客户端不上送签名，网关不做验签及返回签名；");
        nameDescMap.put("phoneNumber","动态密码验证对应的手机号");
        nameDescMap.put("dynamic","动态密码验证对应的动态码");
        nameDescMap.put("jsonpCallback","jsonp callback名");
        nameDescMap.put("thirdPartyId","第三方集成的身份标识(第三方集成情景下使用)");
        nameDescMap.put("domainId","domain id 用户域id");
        nameDescMap.put("channel","channel 发放渠道");
        nameDescMap.put("userAgent","agent 客户端信息");
        nameDescMap.put("fieldEncryption","字段域是否加密，如果客户端不加密，上送na；否则不上送该字段");
        nameDescMap.put("serviceVersion","服务版本，可选，如果有指定项目标识，客户端上送版本号");
        nameDescMap.put("RELEASE_VERSION","配管的release version，新安全体系必送，格式符合SCM的定义");
        nameDescMap.put("ORIGIN","标识请求来源Native or H5，新安全体系必送");
        nameDescMap.put("CLIENT_TIMESTAMPE","客户端加入时间戳字段 , 单位毫秒，新安全体系必送");
        nameDescMap.put("_ft","format 返回值格式,取值为枚举SerializeType中的定义,取值范围JSON/XML");
        nameDescMap.put("_lo", "_lo");
        nameDescMap.put("_tk", "user token 代表访问者身份,完成用户登入流程后获取");
        nameDescMap.put("_dtk", "device token 代表访问设备的身份,完成设备注册流程后获取");
        nameDescMap.put("_webDtk", "web device token 代表访问设备的身份,完成设备注册流程后获取");
        nameDescMap.put("_mt", "method 请求的资源名");
        nameDescMap.put("_sig", "signature 参数字符串签名");
        nameDescMap.put("_aid","application id 应用编号,访问查看具体定义http://doc.tinkerit.cf/pages/viewpage.action?pageId=983175");
        nameDescMap.put("_cid","call id 客户端调用编号");
        nameDescMap.put("_did","device id 设备标示符");
        nameDescMap.put("_uid","user id 用户标示符");
        nameDescMap.put("_cip","client ip 用户ip");
        nameDescMap.put("_vc","version code 客户端数字版本号");
        nameDescMap.put("_sm","signature method 签名算法 hmac,md5,sha1,rsa,ecc;https请求上送na表示客户端不上送签名，网关不做验签及返回签名；");
        nameDescMap.put("_pn","动态密码验证对应的手机号");
        nameDescMap.put("_dyn","动态密码验证对应的动态码");
        nameDescMap.put("_cb","jsonp callback名");
        nameDescMap.put("_tpid","第三方集成的身份标识(第三方集成情景下使用)");
        nameDescMap.put("_domid","domain id 用户域id");
        nameDescMap.put("_chl","channel 发放渠道");
        nameDescMap.put("_ua","agent 客户端信息");
        nameDescMap.put("_ec","字段域是否加密，如果客户端不加密，上送na；否则不上送该字段");
        nameDescMap.put("_sv","服务版本，可选，如果有指定项目标识，客户端上送版本号");
        nameDescMap.put("_rv","配管的release version，新安全体系必送，格式符合SCM的定义");
        nameDescMap.put("_og","标识请求来源Native or H5，新安全体系必送");
        nameDescMap.put("_ct","客户端加入时间戳字段 , 单位毫秒，新安全体系必送");
    }

    public static String getDescription(String paramName) {
        return nameDescMap.get(paramName);
    }

}

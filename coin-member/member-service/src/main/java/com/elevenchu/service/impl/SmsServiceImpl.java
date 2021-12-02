package com.elevenchu.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.alicloud.sms.ISmsService;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.elevenchu.mapper.SmsMapper;
import com.elevenchu.domain.Sms;
import com.elevenchu.service.SmsService;
@Service
@Slf4j
public class SmsServiceImpl extends ServiceImpl<SmsMapper, Sms> implements SmsService{
    @Autowired
    private ISmsService smsService;

    @Override

    public boolean sendSms(Sms sms) {


        log.info("发送短信{}", JSON.toJSONString(sms, true));
        SendSmsRequest request = buildSmsRequest(sms);
        try {
            SendSmsResponse sendSmsResponse = smsService.sendSmsRequest(request);
            log.info("发送的结果为{}", JSON.toJSONString(sendSmsResponse, true));
            String code = sendSmsResponse.getCode();
            if ("OK".equals(code)) { // 发送成功,否则失败
                sms.setStatus(1);
                return save(sms);
            } else {
                return false;
            }
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    private SendSmsRequest buildSmsRequest(Sms sms) {
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(sms.getMobile()); //发送给谁
        // Required:SMS-SignName-could be found in sms console
       // Config signConfig = configService.getConfigByCode("SIGN");
       // sendSmsRequest.setSignName(signConfig.getValue()); // 设置签名---公司里面不会随便的改变--Config 查询签名
        // Required:Template-could be found in sms console
       // Config configByCode = configService.getConfigByCode(sms.getTemplateCode());
       // if (configByCode == null) {
            throw new IllegalArgumentException("您输入的签名不存在");
      //  }
      //  sendSmsRequest.setTemplateCode(configByCode.getValue()); // 模板的Code--动态改变
        // Required:The param of sms template.For exmaple, if the template is "Hello,your verification code is ${code}". The param should be like following value
        //String code = RandomUtil.randomNumbers(6);
        // 需要把code 保存到redis 里面
        // key: SMS:VERIFY_OLD_PHONE:15827293117     value: 123456
       // redisTemplate.opsForValue().set("SMS:" + sms.getTemplateCode() + ":" + sms.getMobile(), code,5, TimeUnit.MINUTES);
       // sendSmsRequest.setTemplateParam("{\"code\":\"" + code + "\"}");
       // String desc = configByCode.getDesc(); //  sign:您的验证码${code}，该验证码5分钟内有效，请勿泄漏于他人！
       // String content = signConfig.getValue() + ":" + desc.replaceAll("\\$\\{code\\}", code);
       // sms.setContent(content); // 最后短信的内容
        //return sendSmsRequest;


    }
}

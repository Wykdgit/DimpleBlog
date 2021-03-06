package com.dimple.project.system.controller;

import com.alibaba.fastjson.JSON;
import com.dimple.common.constant.ConfigKey;
import com.dimple.framework.web.controller.BaseController;
import com.dimple.framework.web.domain.AjaxResult;
import com.dimple.project.system.domain.Config;
import com.dimple.project.system.domain.EmailSetting;
import com.dimple.project.system.domain.SiteSetting;
import com.dimple.project.system.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @className: SettingController
 * @description:
 * @author: Dimple
 * @date: 01/08/20
 */
@RestController
@RequestMapping("system/setting")
public class SettingController extends BaseController {
    @Autowired
    ConfigService configService;

    @GetMapping("/about")
    public AjaxResult about() {
        Config config = configService.selectConfigByKey(ConfigKey.CONFIG_KEY_ABOUT);
        return AjaxResult.success(config == null ? "" : config.getConfigValue());
    }

    @PutMapping("/about")
    public AjaxResult editAbout(String content, String htmlContent) {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        map.put("htmlContent", htmlContent);
        //convert to json
        String jsonString = JSON.toJSONString(map);
        Config config = new Config();
        config.setConfigKey(ConfigKey.CONFIG_KEY_ABOUT);
        config.setConfigValue(jsonString);
        return AjaxResult.success(configService.updateConfigByConfigKey(config));
    }

    @GetMapping("/siteSetting")
    public AjaxResult siteSetting() {
        Config config = configService.selectConfigByKey(ConfigKey.CONFIG_KEY_SITE_SETTING);
        //convert to site setting
        if (config != null) {
            SiteSetting siteSetting = (SiteSetting) JSON.parse(config.getConfigValue());
            return AjaxResult.success(siteSetting);
        }
        return AjaxResult.success(new SiteSetting());
    }

    @PutMapping("siteSetting")
    public AjaxResult editSiteSetting(SiteSetting siteSetting) {
        String jsonString = JSON.toJSONString(siteSetting);
        Config config = new Config();
        config.setConfigKey(ConfigKey.CONFIG_KEY_SITE_SETTING);
        config.setConfigValue(jsonString);
        return AjaxResult.success(configService.updateConfigByConfigKey(config));
    }

    @GetMapping("/emailSetting")
    public AjaxResult emailSetting() {
        Config config = configService.selectConfigByKey(ConfigKey.CONFIG_KEY_EMAIL_SETTING);
        //convert to site setting
        if (config != null) {
            EmailSetting emailSetting = (EmailSetting) JSON.parse(config.getConfigValue());
            return AjaxResult.success(emailSetting);
        }
        return AjaxResult.success(new EmailSetting());
    }

    @PutMapping("emailSetting")
    public AjaxResult editEmailSetting(EmailSetting emailSetting) {
        String jsonString = JSON.toJSONString(emailSetting);
        Config config = new Config();
        config.setConfigKey(ConfigKey.CONFIG_KEY_EMAIL_SETTING);
        config.setConfigValue(jsonString);
        return AjaxResult.success(configService.updateConfigByConfigKey(config));
    }
}

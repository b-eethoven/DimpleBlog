package com.dimple.temp;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/data/push")
public class CloudProxyApi {

    @RequestMapping(value = "/modifyIndividualWorkOrders", method = RequestMethod.POST, produces = "application/x-www-form-urlencoded;charset=UTF-8")
    @ResponseBody
    public String flowIndividualWorkOrders(@RequestParam Map<String, String> params) {

        return null;
    }


}

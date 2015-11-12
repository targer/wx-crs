package org.open.crs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

/**
 * Created by xu.jun on 2015/8/11.
 */
@Controller
public class IndexController {
    private static Logger logger = Logger.getLogger(String.valueOf(IndexController.class));

    /*
    * 首页
    */
    @RequestMapping("/")
    public String index(Model model) {

//        model.addAttribute("userDTO", new UserDTO());
        return "index";
    }

}

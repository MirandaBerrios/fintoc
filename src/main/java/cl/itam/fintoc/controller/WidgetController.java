package cl.itam.fintoc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class WidgetController {
	@RequestMapping("/widget")
    public ModelAndView home( HttpSession session, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("widget");
        return mav;
    }
	
}

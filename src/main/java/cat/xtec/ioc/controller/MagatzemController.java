package cat.xtec.ioc.controller;

import cat.xtec.ioc.domain.*;
import cat.xtec.ioc.service.MagatzemService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author professor
 */
@Controller
@RequestMapping("/magatzems")
public class MagatzemController {

    @Autowired
    MagatzemService magatzemService;

    /*
     TOTS ELS MAGATZEMS
     */
    @RequestMapping("/all")
    public ModelAndView allMagatzems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("magatzems");
        modelview.getModelMap().addAttribute("magatzems", magatzemService.getAllMagatzems());
        return modelview;
    }

    /*
     MAGATZEMS D'UN LOCALITAT
     */
    @RequestMapping("/{localitat}")
    public ModelAndView getMagatzemsByLocalitat(@PathVariable("localitat") String localitat, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModelAndView modelview = new ModelAndView("magatzems");
        modelview.getModelMap().addAttribute("magatzems", magatzemService.getMagatzemsByLocalitat(localitat));
        return modelview;
    }

    

    @RequestMapping("/magatzem")
    public ModelAndView getMagatzemByCodi(@RequestParam("codi") String codi, @RequestParam("localitat") String localitat, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ModelAndView modelview = new ModelAndView("magatzem");

        String mycodi = request.getParameter("codi");
        Magatzem formMagatzem = null;

        String altrecodi = (String) request.getAttribute("codi");

        if (codi != "") {
            formMagatzem = magatzemService.getMagatzemByCodi(codi);
        } else {
            formMagatzem = new Magatzem();
            formMagatzem.setLocalitat(localitat);            
        }
        modelview.getModelMap().addAttribute("formMagatzem", formMagatzem);
        return modelview;
    }

    /*
     FORM DE MAGATZEM POST
     */
    @RequestMapping(value = "/magatzem/add", method = RequestMethod.POST)
    public String processMagatzemForm(@ModelAttribute("formMagatzem") Magatzem formMagatzem, BindingResult result) {
        magatzemService.updateMagatzem(formMagatzem);
        return "redirect:/magatzems/all";
    }

}

package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@Controller
public class SearchController {


	@Autowired
	  private ProducerTemplate producerTemplate;

	@RequestMapping("/")
    public String index() {
        return "index";
    }


    @RequestMapping(value="/search")
    @ResponseBody
    public Object search(@RequestParam("q") String q) {
        HashMap<String, Object> header = new HashMap<String, Object>();
        if(q.contains("max:")) {
            String[] part = q.split(" max:");
            header.put("CamelTwitterKeywords", part[0]);
            header.put("CamelTwitterCount", part[1]);
        }
        else{
            header.put("CamelTwitterKeywords", q);
        }
        return producerTemplate.requestBodyAndHeaders("direct:search", "", header);
}
}
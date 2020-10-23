package foods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FoodsController {

	private DB db;

	@Autowired
	public void setStoryService(DB db) {
		this.db = db;
	}

	@RequestMapping("/")
	public String home(Model model) {
		// model.addAttribute("access", db.find("SELECT 1 AS val"));
		//model.addAttribute("access", db.find("SELECT COUNT(*) FROM contents"));
		//model.addAttribute("access", db.find("SELECT source_id, orig_source_name, orig_content, orig_unit FROM contents LIMIT 5"));
		model.addAttribute("access", db.find("SELECT source_id, orig_source_name, orig_content, orig_unit"
				+ " FROM contents WHERE orig_food_common_name "
				+ "= \"Milk, whole, konventional (not organic), 3.5 % fat\" " + "AND orig_source_name IS NOT NULL"));
		return "index";
	}
}

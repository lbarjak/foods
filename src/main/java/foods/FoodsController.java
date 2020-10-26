package foods;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class FoodsController {

	private DB db;

	@Autowired
	public void inject(DB db) {
		this.db = db;
	}

	// model.addAttribute("access", db.find("SELECT 1 AS val"));
	// model.addAttribute("access", db.find("SELECT COUNT(*) FROM contents"));
	// model.addAttribute("access", db.find("SELECT source_id, orig_source_name,
	// orig_content, orig_unit FROM contents LIMIT 5"));
//		model.addAttribute("access", db.find("SELECT source_id, orig_source_name, orig_content, orig_unit"
//				+ " FROM contents WHERE orig_food_common_name "
//				+ "= \"Milk, whole, konventional (not organic), 3.5 % fat\" " + "AND orig_source_name IS NOT NULL"));

	private List<String> queries = new ArrayList<>();

	@PostMapping()
	public String handlePostQueries(String query) {
		queries.add(query);
		System.out.println(query);
		return "redirect:/";
	}

	@GetMapping()
	public String handleGetQueries(Model model) {
		model.addAttribute("queries", queries);
		return "index";
	}

}

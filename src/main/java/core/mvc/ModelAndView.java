package core.mvc;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
	private View view;
	private Map<String, Object> model;
	
	public ModelAndView(View view) {
		this.view = view;
		this.model = new HashMap<>();
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public View getView() {
		return view;
	}
	
	public void addModel(String key, Object value){
		model.put(key, value);
	}
	
	public Map<String, Object> getModel() {
		return model;
	}

}

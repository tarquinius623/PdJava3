package official;

import org.puredata.core.utils.PdDispatcher;

public class PdUiDispatcher extends PdDispatcher {
	
	public PdUiDispatcher(){
		
	}

	@Override
	public void print(String arg0) {
		System.out.println(arg0);
	}


}

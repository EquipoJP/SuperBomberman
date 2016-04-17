package logic;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import logic.Input.KEY;
import utils.IniUtils;
import utils.SaveSystemService;

public class KeyMapper {
	
	private static final String KEYMAPPER_FILE = "keymap.ini";
	private static final String KM_SECTION = "default";
	
	private Map<KEY, Integer> mapper;
	
	public KeyMapper(){
		mapper = new HashMap<KEY, Integer>();
		
		if(!loadKeyMapper()){
			// Create initial association
			createKeyMapper();
		}
	}
	
	public int getCodeKey(KEY key){
		return mapper.get(key);
	}
	
	public void changeKeyCode(KEY key, int code){
		mapper.put(key, code);
		saveKeyMapper();
	}
	
	private boolean loadKeyMapper(){
		String file = SaveSystemService.PATH + KEYMAPPER_FILE;
		File f = new File(file);
		
		if(f.exists() && f.isFile()){
			for(KEY key : KEY.values()){
				String strValue = IniUtils.getValue(file, KM_SECTION, key.toString());
				if(strValue != null){
					mapper.put(key, Integer.parseInt(strValue));
				}
			}
			return true;
		}
		return false;
	}
	
	private boolean createKeyMapper(){
		// up
		KEY key = KEY.UP;
		int value = KeyEvent.VK_UP;
		mapper.put(key, value);
		
		// down
		key = KEY.DOWN;
		value = KeyEvent.VK_DOWN;
		mapper.put(key, value);
		
		// left
		key = KEY.LEFT;
		value = KeyEvent.VK_LEFT;
		mapper.put(key, value);
		
		// right
		key = KEY.RIGHT;
		value = KeyEvent.VK_RIGHT;
		mapper.put(key, value);
		
		// bomb
		key = KEY.BOMB;
		value = KeyEvent.VK_SPACE;
		mapper.put(key, value);
		
		// pause
		key = KEY.PAUSE;
		value = KeyEvent.VK_ESCAPE;
		mapper.put(key, value);
		
		// confirm
		key = KEY.CONFIRM;
		value = KeyEvent.VK_ENTER;
		mapper.put(key, value);
		
		return saveKeyMapper();
	}
	
	public boolean saveKeyMapper(){
		String file = SaveSystemService.PATH + KEYMAPPER_FILE;
		
		File directory = new File(SaveSystemService.PATH);
		if (directory.exists()) {
			; // directory exists
		} else if (directory.mkdirs()) {
			; // directory created
		} else {
			return false;
		}
		
		File f = new File(file);
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(f.exists() && f.isFile()){
			for(KEY key : mapper.keySet()){
				Integer intValue = mapper.get(key);
				IniUtils.addValue(file, KM_SECTION, key.toString(), intValue + "");
			}
			return true;
		}
		return false;
	}

}
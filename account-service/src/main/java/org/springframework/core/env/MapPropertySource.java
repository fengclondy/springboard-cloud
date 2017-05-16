package org.springframework.core.env;

import java.util.Map;

import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.util.StringUtils;

import com.alibaba.druid.filter.config.ConfigTools;

/**
 * {@link PropertySource} that reads keys and values from a {@code Map} object.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @see PropertiesPropertySource
 */
public class MapPropertySource extends EnumerablePropertySource<Map<String, Object>> {

	public MapPropertySource(String name, Map<String, Object> source) {
		super(name, source);
	}


	@Override
	public Object getProperty(String name) {
		// add by fanjun, 20160222
		// 加密properties文件中的密码
		Object value = this.source.get(name);
		if (value != null) {
			if (name.toLowerCase().endsWith(".password")) {
				try {
					String pw = ConfigTools.decrypt((String) value);
					return pw;
				} catch (Exception e) {
					logger.error("password decrypt error.", e);
					return value;
				}
			} 
		}
		return value;
		// add end
		
		// old
//		return this.source.get(name); 
	}

	@Override
	public boolean containsProperty(String name) {
		return this.source.containsKey(name);
	}

	@Override
	public String[] getPropertyNames() {
		return StringUtils.toStringArray(this.source.keySet());
	}

}

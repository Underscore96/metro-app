package presentation.application;

import java.util.HashSet;
import java.util.Set;

import exception.ServerExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("rest")
public class MyApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<>();
		s.add(ServerExceptionMapper.class);

		return s;
	}
}
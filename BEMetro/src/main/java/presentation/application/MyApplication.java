package presentation.application;

import java.util.HashSet;
import java.util.Set;

import exception.ServerExceptionMapper;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import presentation.rest.FermataFERest;
import presentation.rest.FermataRest;
import presentation.rest.GestioneDBRest;
import presentation.rest.LineaRest;

@ApplicationPath("rest")
public class MyApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<>();
		s.add(FermataRest.class);
		s.add(LineaRest.class);
		s.add(FermataFERest.class);
		s.add(GestioneDBRest.class);
		s.add(ServerExceptionMapper.class);

		return s;
	}
}
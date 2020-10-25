package com.resourceserver.concepts.oauth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConfigurationProperties(prefix = "spring.database")
public class SimpleCorsFilter implements Filter {
	
	private String env;
	private static final String ALLOW_ORIGIN_HEADER="Access-Control-Allow-Origin";
	
	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public SimpleCorsFilter() {
		super();
	}

	private static List<String> originList = new ArrayList<>();

	static {
		originList.add("http://localhost:4200");
		originList.add("https://qa.shortlist.net");
		originList.add("https://www.shortlist.net");
		originList.add("https://calling.shortlist.net");
		originList.add("https://charlie.shortlist.net");
		originList.add("https://charlieqa.shortlist.net");
		originList.add("https://shortlist.net");
		originList.add("https://testenv.shortlist.net");
		originList.add("https://beta.shortlist.net");

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		response.setHeader(ALLOW_ORIGIN_HEADER, "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE,PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Server", "Custoem server header");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, Server, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
		response.setHeader("Strict-Transport-Security", "max-age=7776000; includeSubdomains");
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
		//empty
	}

	@Override
	public void destroy() {
		//empty
	}
}

package fr.eni.eniencheres.conf;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LogFilter implements Filter {

	private static final Logger LOG = MyLogger.getLogger(LogFilter.class);
	
	private static final Level HTTP_DEBUG = Level.INFO;

	/**
	 * Called by the web container to indicate to a filter that it is being placed
	 * into service. The servlet container calls the init method exactly once after
	 * instantiating the filter. The init method must complete successfully before
	 * the filter is asked to do any filtering work. <br>
	 * <br>
	 * The web container cannot place the filter into service if the init method
	 * either<br>
	 * 1.Throws a ServletException <br>
	 * 2.Does not return within a time period defined by the web container
	 *
	 * @param filterConfig
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("Initialise My Own access log for output to console");
	}

	/**
	 * The <code>doFilter</code> method of the Filter is called by the container
	 * each time a request/response pair is passed through the chain due to a client
	 * request for a resource at the end of the chain. The FilterChain passed in to
	 * this method allows the Filter to pass on the request and response to the next
	 * entity in the chain. A typical implementation of this method would follow the
	 * following pattern:- <br>
	 * 1. Examine the request<br>
	 * 2. Optionally wrap the request object with a custom implementation to filter
	 * content or headers for input filtering <br>
	 * 3. Optionally wrap the response object with a custom implementation to filter
	 * content or headers for output filtering <br>
	 * 4. a) <strong>Either</strong> invoke the next entity in the chain using the
	 * FilterChain object (<code>chain.doFilter()</code>), <br>
	 * 4. b) <strong>or</strong> not pass on the request/response pair to the next
	 * entity in the filter chain to block the request processing<br>
	 * 5. Directly set headers on the response after invocation of the next entity
	 * in the filter chain.
	 *
	 * @param request
	 * @param response
	 * @param chain
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		StringBuilder buildInfo = new StringBuilder();
		if (LOG.isLoggable(Level.INFO) || LOG.isLoggable(HTTP_DEBUG)) {
			HttpServletRequest httpRequest;
			httpRequest = (HttpServletRequest) request;
			buildInfo.append(httpRequest.getMethod()).append(" ").append(httpRequest.getServletPath());
			if (LOG.isLoggable(HTTP_DEBUG)) {
				// HEADERS
				Enumeration<String> headerNames = httpRequest.getHeaderNames();	
				if (headerNames.hasMoreElements()) {
					StringBuilder sbHeaders = new StringBuilder(buildInfo).append(" req headers : [");
					while (headerNames.hasMoreElements()) {
						String headerName = headerNames.nextElement();
						sbHeaders.append(headerName).append(":").append(httpRequest.getHeader(headerName)).append(",");
					}
					sbHeaders.append("]");
					LOG.log(HTTP_DEBUG,sbHeaders.toString());
				}
				// PARAMETERS
				Enumeration<String> parameterNames = httpRequest.getParameterNames();
				if (parameterNames.hasMoreElements()) {
					StringBuilder sbParameters = new StringBuilder(buildInfo).append(" req parameters : [");
					while (parameterNames.hasMoreElements()) {
						String parameterName = parameterNames.nextElement();
						sbParameters.append(parameterName).append(":").append(httpRequest.getParameter(parameterName))
								.append(",");
					}
					sbParameters.append("]");
					LOG.log(HTTP_DEBUG,sbParameters.toString());
				}
				// ATTRIBUTES
				Enumeration<String> attributeNames = httpRequest.getAttributeNames();
				if (attributeNames.hasMoreElements()) {
					StringBuilder sbAttributes = new StringBuilder(buildInfo).append(" req attributes : [");
					while (attributeNames.hasMoreElements()) {
						String attributeName = attributeNames.nextElement();
						sbAttributes.append(attributeName).append(":").append(httpRequest.getAttribute(attributeName))
								.append(",");
					}
					sbAttributes.append("]");
					LOG.log(HTTP_DEBUG,sbAttributes.toString());
				}
			}
		}

		// DO FILTER
		chain.doFilter(request, response);

		if (LOG.isLoggable(Level.INFO) || LOG.isLoggable(Level.FINE)) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			if (LOG.isLoggable(HTTP_DEBUG)) {
				Collection<String> headerNames = httpResponse.getHeaderNames();
				StringBuilder sbHeaders = new StringBuilder(buildInfo).append(" resp headers : [");
				for (String headerName : headerNames) {
					Collection<String> headerValues = httpResponse.getHeaders(headerName);
					String headerValue = (headerValues.size() > 1) ? headerValues.toString()
							: headerValues.iterator().next();
					sbHeaders.append(headerName).append(":").append(headerValue).append(",");
				}
				LOG.log(HTTP_DEBUG,sbHeaders.toString());
			}
			buildInfo.append(" : ").append(httpResponse.getStatus()).append(" | ").append(httpResponse.getContentType())
					.append(" | ").append(httpResponse.getBufferSize());
			if(httpResponse.getStatus()>399) {
				LOG.warning(buildInfo.toString());
			}else {
				LOG.info(buildInfo.toString());
			}
			
		}

	}

	/**
	 * Called by the web container to indicate to a filter that it is being taken
	 * out of service. This method is only called once all threads within the
	 * filter's doFilter method have exited or after a timeout period has passed.
	 * After the web container calls this method, it will not call the doFilter
	 * method again on this instance of the filter. <br>
	 * <br>
	 * This method gives the filter an opportunity to clean up any resources that
	 * are being held (for example, memory, file handles, threads) and make sure
	 * that any persistent state is synchronized with the filter's current state in
	 * memory.
	 */
	@Override
	public void destroy() {
	}



}

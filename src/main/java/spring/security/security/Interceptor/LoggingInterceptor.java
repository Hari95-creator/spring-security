package spring.security.security.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //here before hitting the controller the request can mdoify  such as modifying the parameter and sent back to controller

        if (request.getQueryString() != null && !request.getQueryString().isEmpty()) {

            String data = request.getParameter("data");

            if (data != null && !data.trim().isEmpty()) {
                String modifiedData = data.toUpperCase();
                request.setAttribute("data", modifiedData);
            }

        }

        System.out.println("Pre Handled Request URI" + request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

        //vice versa of pre handle after returning back to controller (just a use case)
        System.out.println("Post handled Request URI :" + request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        System.out.println("Completed Execution of the Requested URI :" + request.getRequestURI());
    }


}

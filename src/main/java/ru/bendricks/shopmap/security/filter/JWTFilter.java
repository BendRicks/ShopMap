package ru.bendricks.shopmap.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.bendricks.shopmap.security.UserDetailsInfo;
import ru.bendricks.shopmap.security.util.JWTUtil;
import ru.bendricks.shopmap.service.UserService;
import ru.bendricks.shopmap.util.constraints.GeneralConstants;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final UserService userService;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authToken == null){
            var cookie = Arrays.stream(request.getCookies()).filter(el -> el.getName().equals("jwt-token")).findAny();
            if (cookie.isPresent()){
                authToken = cookie.get().getValue();
            }
        }

        if (authToken != null && !authToken.isBlank() && authToken.startsWith(GeneralConstants.BEARER_HEADER_TOKEN)){
            String jwt = authToken.substring(GeneralConstants.JWT_START_INDEX);

            if (jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid jwt token");
            } else {
                try {
                    int id = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetailsInfo userDetails = new UserDetailsInfo(userService.findById(id));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                } catch (JWTVerificationException e){
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getOutputStream().print("Unauthorized");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
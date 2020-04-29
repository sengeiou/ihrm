//package com.ihrm.common.config;
//
//import com.ihrm.common.filter.ImageCodeFilter;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.alibaba.fastjson.JSONObject;import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.security.NoSuchAlgorithmException;
//import java.security.Security;
//import java.util.concurrent.TimeUnit;
//import java.security.Security;
//import java.util.concurrent.TimeUnit;
//
///**
// *
// * Description:安全配置
// * @author huangweicheng
// * @date 2019/10/21
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    /**
//     * 日志记录
//     */
//    private static final Logger log = LoggerFactory.getLogger(Security.class);
//
//    @Autowired
//    private RedisTemplate redisTemplate;
//
////    @Autowired
////    protected SysUserDetailsServiceImpl sysUserDetailsService;
//
//    @Autowired
//    private ImageCodeFilter imageCodeFilter;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//    /**
//     *
//     * Description:资源角色配置登录
//     * @param http
//     * @author huangweicheng
//     * @date 2019/10/21
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception
//    {
//        /*图片验证码过滤器设置在密码验证之前*/
//        http.addFilterBefore(imageCodeFilter, UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/", "/*.html", "favicon.ico", "/**/*.html", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
//                .antMatchers("/user/**","/login").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/hwc/**").hasRole("USER")
//                .anyRequest().authenticated()
//                .and().formLogin().loginProcessingUrl("/user/login")
//                /*自定义登录成功处理，返回token值*/
//                .successHandler((HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication)->
//                {
//                    log.info("用户为====>" + httpServletRequest.getParameter("username") + "登录成功");
//                    httpServletResponse.setContentType("application/json;charset=utf-8");
//                    /*获取用户权限信息*/
//                    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//                    String token = jwtTokenUtil.generateToken(userDetails);
//                    /*存储redis并设置了过期时间*/
//                    redisTemplate.boundValueOps(userDetails.getUsername() + "hwc").set(token,10, TimeUnit.MINUTES);
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("code", ResultModel.SUCCESS);
//                    jsonObject.put("msg","登录成功");
//                    /*认证信息写入header*/
//                    httpServletResponse.setHeader("Authorization",token);
//                    httpServletResponse.getWriter().write(jsonObject.toJSONString());
//                })
//                /*登录失败处理*/
//                .failureHandler((HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)->
//                {
//                    log.info("用户为====>" + request.getParameter("username") + "登录失败");
//                    String content = exception.getMessage();
//                    //TODO 后期改进密码错误方式，统一处理
//                    String temp = "Bad credentials";
//                    if (temp.equals(exception.getMessage()))
//                    {
//                        content = "用户名或密码错误";
//                    }
//                    response.setContentType("application/json;charset=utf-8");
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("code", ResultModel.ERROR);
//                    jsonObject.put("msg",content);
//                    jsonObject.put("content",exception.getMessage());
//                    response.getWriter().write(jsonObject.toJSONString());
//                })
//                /*无权限访问处理*/
//                .and().exceptionHandling().accessDeniedHandler((HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e)->
//        {
//            httpServletResponse.setContentType("application/json;charset=utf-8");
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code", HttpStatus.FORBIDDEN);
//            jsonObject.put("msg", "无权限访问");
//            jsonObject.put("content",e.getMessage());
//            httpServletResponse.getWriter().write(jsonObject.toJSONString());
//        })
//                /*匿名用户访问无权限资源时的异常*/
//                .and().exceptionHandling().authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)->
//        {
//            response.setContentType("application/json;charset=utf-8");
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("code",HttpStatus.FORBIDDEN);
//            jsonObject.put("msg","无访问权限");
//            response.getWriter().write(jsonObject.toJSONString());
//        })
//                .and().authorizeRequests()
//                /*基于token，所以不需要session*/
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                /*由于使用的是jwt，这里不需要csrf防护并且禁用缓存*/
//                .and().csrf().disable().headers().cacheControl();
//        /*token过滤*/
//        http.addFilterBefore(authenticationTokenFilterBean(),UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
//    {
//        authenticationManagerBuilder.userDetailsService(sysUserDetailsService).passwordEncoder(new PasswordEncoder()
//        {
//            /**
//             *
//             * Description:用户输入的密码加密
//             * @param charSequence
//             * @author huangweicheng
//             * @date 2019/10/21
//             */
//            @Override
//            public String encode(CharSequence charSequence)
//            {
//                try {
//                    return Common.md5(charSequence.toString());
//                }catch (NoSuchAlgorithmException e){
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            /**
//             *
//             * Description: 与数据库的密码匹配
//             * @param charSequence 用户密码
//             * @param encodedPassWord 数据库密码
//             * @author huangweicheng
//             * @date 2019/10/21
//             */
//            @Override
//            public boolean matches(CharSequence charSequence, String encodedPassWord)
//            {
//                try {
//                    return encodedPassWord.equals(Common.md5(charSequence.toString()));
//                }catch (NoSuchAlgorithmException e){
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
//    }
//　　//token过滤器
//    @Bean
//    public JwtAuthenticationFilter authenticationTokenFilterBean()
//    {
//        return new JwtAuthenticationFilter();
//    }
//}

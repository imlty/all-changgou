package com.changgou.oauth.config;

import com.changgou.oauth.util.UserJwt;
import com.changgou.user.feign.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*****
 * 自定义授权认证类
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    private UserFeign userFeign;

    /****
     * 自定义授权认证
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 授权码认证===============================客户端信息认证开始================================
        //取出身份，如果身份为空说明没有认证
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //没有认证统一采用 httpbasic 认证，httpbasic 中存储了 client_id 和 client_secret，开始认证 client_id 和 client_secret
        if (authentication == null) {
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
            if (clientDetails != null) {
                //秘钥
                String clientSecret = clientDetails.getClientSecret();
                //静态方式
                //return new User(username,new BCryptPasswordEncoder().encode(clientSecret), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
                //数据库查找方式 (理解为 用户密码已经加密并存入数据库中,不需要加密)
                return new User(username, clientSecret, AuthorityUtils.commaSeparatedStringToAuthorityList(""));
            }
        }
        // ===============================客户端信息认证结束================================


        // 账号密码模式认证===============================用户信息认证开始================================
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        // 将硬编码模式替换成从数据库查询 -> 根据用户名查询用户信息
        //String pwd = new BCryptPasswordEncoder().encode("szitheima");
        String pwd = userFeign.findByUsername(username).getData().getPassword();
        //创建User对象  授予权限.GOODS_LIST  SECKILL_LIST
        String permissions = "goods_list,seckill_list";

        UserJwt userDetails = new UserJwt(username, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));

        //userDetails.setComy(songsi);
        // ===============================用户信息认证结束================================
        return userDetails;
    }

    public static void main(String[] args) {
        String zhangsan = new BCryptPasswordEncoder().encode("zhangsan");
        System.out.println(zhangsan);
    }
}

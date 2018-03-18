package chazen.ekanban.jwtauth;

import chazen.ekanban.entity.SysUser;
import chazen.ekanban.service.UserService;
import chazen.ekanban.util.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("JwtUserDetailsServiceImpl")
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.findUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return JwtUserFactory.create(user);
        }
    }
}
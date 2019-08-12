package com.java.contorller;

import com.java.model.vo.ArticleVo;
import com.java.model.vo.ReplyVo;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;

public abstract class CommonController {

	protected boolean isAdmin(Authentication authentication) {
		try {
			return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	protected boolean isOwner(Authentication authentication, @Nullable Object vo) {
		if (vo == null)
			return false;
		try {
			String username = authentication.getName();
			String author = null;
			if (vo instanceof ReplyVo) {
				author = ((ReplyVo) vo).getAuthor().getUsername();
			} else if (vo instanceof ArticleVo) {
				author = ((ArticleVo) vo).getAuthor().getUsername();
			}
			return username.equals(author);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	protected String extractLocator(HttpServletRequest request) {
		return request.getRequestURL().toString().replace(request.getRequestURI(), "");
	}

}

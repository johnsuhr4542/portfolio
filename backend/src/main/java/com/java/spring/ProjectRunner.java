package com.java.spring;

import com.java.model.service.ArticleService;
import com.java.model.service.MemberService;
import com.java.model.vo.ArticleVo;
import com.java.model.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ProjectRunner implements ApplicationRunner {

	@Autowired private MemberService memberService;
	@Autowired private ArticleService articleService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		MemberVo john = new MemberVo();
		john.setUsername("john");
		john.setPassword("123456");
		john.setEmail("john@example.com");
		memberService.save(john);

		MemberVo david = new MemberVo();
		david.setUsername("david");
		david.setPassword("123456");
		david.setEmail("david@example.com");
		memberService.save(david);

		for (int i = 1; i <= 123; i++) {
			ArticleVo articleVo = new ArticleVo();
			articleVo.setAuthor(john);
			articleVo.setTitle("test title_" + i);
			articleVo.setContent("test content_" + i);
			articleService.save(articleVo);
		}
	}

}

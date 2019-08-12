package com.java.model.service;

import com.java.model.domain.Article;
import com.java.model.domain.Member;
import com.java.model.domain.Reply;
import com.java.model.vo.ArticleVo;
import com.java.model.vo.MemberVo;
import com.java.model.vo.ReplyVo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class CommonService {

	@Autowired private ModelMapper modelMapper;

	protected Member convert(MemberVo memberVo) {
		return modelMapper.map(memberVo, Member.class);
	}

	protected MemberVo convert(Member member) {
		return modelMapper.map(member, MemberVo.class);
	}

	protected Article convert(ArticleVo articleVo) {
		Article article = modelMapper.map(articleVo, Article.class);
		if (articleVo.getAuthor() != null) {
			Member author = convert(articleVo.getAuthor());
			article.setAuthor(author);
		}
		return article;
	}

	protected ArticleVo convert(Article article) {
		MemberVo author = convert(article.getAuthor());
		ArticleVo articleVo = modelMapper.map(article, ArticleVo.class);
		articleVo.setAuthor(author);
		return articleVo;
	}

	protected Reply convert(ReplyVo replyVo) {
		Member author = convert(replyVo.getAuthor());
		Article article = convert(replyVo.getArticle());
		Reply reply = modelMapper.map(replyVo, Reply.class);
		reply.setAuthor(author);
		reply.setArticle(article);
		return reply;
	}

	protected ReplyVo convert(Reply reply) {
		MemberVo author = convert(reply.getAuthor());
		ReplyVo replyVo = modelMapper.map(reply, ReplyVo.class);
		replyVo.setAuthor(author);
		return replyVo;
	}

}

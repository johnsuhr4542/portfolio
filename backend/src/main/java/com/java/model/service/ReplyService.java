package com.java.model.service;

import com.java.model.domain.Reply;
import com.java.model.repository.ReplyRepository;
import com.java.model.vo.ReplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ReplyService extends CommonService {

	@Autowired private ReplyRepository replyRepository;

	@Nullable
	public ReplyVo findById(Long id) {
		ReplyVo replyVo = null;
		try {
			Reply reply = replyRepository.findById(id);
			replyVo = convert(reply);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return replyVo;
	}

	@Nullable
	public Long save(ReplyVo replyVo) {
		Long id = null;
		try {
			Reply reply = convert(replyVo);
			replyRepository.save(reply);
			id = reply.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	@Nullable
	public Long delete(Long id) {
		Long result = null;
		try {
			Reply reply = replyRepository.findById(id);
			reply.setDeleted(true);
			result = reply.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}

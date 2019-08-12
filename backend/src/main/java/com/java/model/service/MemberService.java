package com.java.model.service;

import com.java.model.domain.Member;
import com.java.model.repository.MemberRepository;
import com.java.model.vo.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class MemberService extends CommonService {

	@Autowired private MemberRepository memberRepository;
	@Autowired private PasswordEncoder passwordEncoder;

	@Nullable
	public String save(MemberVo memberVo) {
		String username = null;
		try {
			memberVo.setPassword(passwordEncoder.encode(memberVo.getPassword()));
			Member member = convert(memberVo);
			memberRepository.save(member);
			username = member.getUsername();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}

	@Nullable
	public MemberVo findByUsername(String username) {
		MemberVo memberVo = null;
		try {
			Member member = memberRepository.findByUsername(username);
			memberVo = convert(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberVo;
	}

	@Nullable
	public String modify(MemberVo memberVo) {
		String username = null;
		try {
			Member member = memberRepository.findByUsername(memberVo.getUsername());
			member.setPassword(passwordEncoder.encode(memberVo.getPassword()));
			member.setEmail(memberVo.getEmail());
			username = member.getUsername();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return username;
	}

	@Nullable
	public String delete(String username) {
		String result = null;
		try {
			Member member = memberRepository.findByUsername(username);
			member.setDeleted(true);
			result = member.getUsername();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean exists(String username) {
		try {
			Member alreadyExist = memberRepository.findByUsername(username);
			return alreadyExist != null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Nullable
	public String enhance(String username) {
		String result = null;
		try {
			memberRepository.enhance(username);
			result = username;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}

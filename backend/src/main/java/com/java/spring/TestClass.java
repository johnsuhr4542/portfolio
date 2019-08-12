package com.java.spring;

import com.java.model.domain.Member;
import com.java.model.vo.MemberVo;
import org.modelmapper.ModelMapper;

public class TestClass {
	public static void main(String[] args) {
		MemberVo test = null;
		new ModelMapper().map(test, Member.class);
	}
}

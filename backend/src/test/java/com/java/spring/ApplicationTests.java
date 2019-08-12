package com.java.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

//	@Autowired private WebApplicationContext wac;
//	@Autowired private PasswordEncoder passwordEncoder;
//
//	private MockMvc mockMvc;
//	private ObjectMapper mapper;
//
//	@Before
//	public void init() {
//		this.mockMvc = MockMvcBuilders
//			.webAppContextSetup(this.wac)
//			.apply(springSecurity())
//			.build();
//		this.mapper = new ObjectMapper();
//	}

//	@Test
//	public void memberInsertTestWhenNormal() throws Exception {
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		String username = mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk())
//			.andDo(print())
//			.andReturn().getResponse().getContentAsString();
//
//		assertEquals(username, john.getUsername());
//	}

//	@Test
//	public void memberInsertTestWhenIncorrectParameter() throws Exception {
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john"); // wrong format "email"
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isBadRequest())
//			.andDo(print());
//	}

//	@Test
//	public void memberInsertTestWhenAlreadyExistEntity() throws Exception {
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		// this is ok
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		// and this one isn't
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isBadRequest())
//			.andDo(print());
//	}

//	@Test
//	public void memberSelectTestWhenNormal() throws Exception {
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		// save john
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		// parameters for token authentication
//		Map<String, String> params = new HashMap<>();
//		params.put("username", john.getUsername());
//		params.put("password", john.getPassword());
//
//		// get access token
//		String responseAsString = mockMvc
//			.perform(
//				post("/auth/token")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(params)))
//			.andExpect(status().isOk())
//			.andReturn().getResponse().getContentAsString();
//
//		Map<String, Object> responseAsMap =
//			mapper.readValue(responseAsString, new TypeReference<Map<String, Object>>() {});
//		String accessToken = (String) responseAsMap.get("accessToken");
//
//		// get user information
//		String resp = mockMvc
//			.perform(
//				get("/member/john")
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isOk())
//			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//			.andDo(print())
//			.andReturn().getResponse().getContentAsString();
//
//		MemberVo savedJohn = mapper.readValue(resp, new TypeReference<MemberVo>() {});
//
//		// check equaility
//		assertEquals(john.getUsername(), savedJohn.getUsername());
//		assertTrue(passwordEncoder.matches(john.getPassword(), savedJohn.getPassword()));
//		assertEquals(john.getEmail(), savedJohn.getEmail());
//
//	}

//	@Test
//	public void MemberSelectTestWhenRequestByAdmin() throws Exception {
//		MemberVo admin = new MemberVo();
//		admin.setUsername("admin");
//		admin.setPassword("admin");
//		admin.setEmail("admin@example.com");
//
//		// save admin account
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(admin)))
//			.andExpect(status().isOk());
//
//		// !! development only !!
//		// enhance authority of admin from "ROLE_USER" to "ROLE_ADMIN"
//		mockMvc
//			.perform(
//				get("/member/enhance/admin"))
//			.andExpect(status().isOk());
//
//		// save user "john"
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		// get access token by using admin account
//		Map<String, String> paramsForGetAccessToken = new HashMap<>();
//		paramsForGetAccessToken.put("username", admin.getUsername());
//		paramsForGetAccessToken.put("password", admin.getPassword());
//
//		String tokenResponseAsString = mockMvc
//			.perform(
//				post("/auth/token")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(paramsForGetAccessToken)))
//			.andExpect(status().isOk())
//			.andReturn().getResponse().getContentAsString();
//
//		Map<String, Object> tokenResponseAsMap =
//			mapper.readValue(tokenResponseAsString, new TypeReference<Map<String, Object>>() {});
//		String accessToken = (String) tokenResponseAsMap.get("accessToken");
//
//		String memberResponseAsString = mockMvc
//			.perform(
//				get("/member/john")
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isOk())
//			.andDo(print())
//			.andReturn().getResponse().getContentAsString();
//
//		MemberVo savedJohn = mapper.readValue(memberResponseAsString, new TypeReference<MemberVo>() {});
//
//		assertEquals(john.getUsername(), savedJohn.getUsername());
//		assertTrue(passwordEncoder.matches(john.getPassword(), savedJohn.getPassword()));
//		assertEquals(john.getEmail(), savedJohn.getEmail());
//	}

//	@Test
//	public void memberUpdateTestWhenNormal() throws Exception {
//
//		// save john
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		Map<String, String> tokenParams = new HashMap<>();
//		tokenParams.put("username", john.getUsername());
//		tokenParams.put("password", john.getPassword());
//
//		// get access token
//		String tokenResponseAsString = mockMvc
//			.perform(
//				post("/auth/token")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(tokenParams)))
//			.andExpect(status().isOk())
//			.andReturn().getResponse().getContentAsString();
//
//		Map<String, Object> tokenReponseAsMap =
//			mapper.readValue(tokenResponseAsString, new TypeReference<Map<String, Object>>() {});
//		String accessToken = (String) tokenReponseAsMap.get("accessToken");
//
//		// modify entity
//		john.setEmail("john123@example.com");
//
//		mockMvc
//			.perform(
//				put("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john))
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isOk());
//
//		// retrieve saved entity
//		String memberResponseAsString = mockMvc
//			.perform(
//				get("/member/john")
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isOk())
//			.andDo(print())
//			.andReturn().getResponse().getContentAsString();
//
//		MemberVo savedJohn = mapper.readValue(memberResponseAsString, new TypeReference<MemberVo>() {});
//
//		assertEquals(john.getEmail(), savedJohn.getEmail());
//
//	}

//	@Test
//	public void memberDeleteTest() throws Exception {
//
//		// save member entity
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		// get access token
//		Map<String, Object> params = new HashMap<>();
//		params.put("username", john.getUsername());
//		params.put("password", john.getPassword());
//
//		String tokenResponseAsString = mockMvc
//			.perform(
//				post("/auth/token")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(params)))
//			.andExpect(status().isOk())
//			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//			.andReturn().getResponse().getContentAsString();
//
//		Map<String, Object> tokenResponseAsMap =
//			mapper.readValue(tokenResponseAsString, new TypeReference<Map<String, Object>>() {});
//		String accessToken = (String) tokenResponseAsMap.get("accessToken");
//
//		// delete member entity
//		mockMvc
//			.perform(
//				delete("/member/john")
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isOk());
//
//		// fetch member entity "john" and expect 204 NO_CONTENT
//		mockMvc
//			.perform(
//				get("/member/john")
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isNoContent())
//			.andDo(print());
//
//	}

//	@Test
//	public void memberDeleteTestByUnauthorizedMember() throws Exception {
//
//		// save member entity "john"
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		// save member entity "david"
//		MemberVo david = new MemberVo();
//		david.setUsername("david");
//		david.setPassword("123456");
//		david.setEmail("david@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(david)))
//			.andExpect(status().isOk());
//
//		// get access token by using information about david.
//		Map<String, Object> params = new HashMap<>();
//		params.put("username", david.getUsername());
//		params.put("password", david.getPassword());
//
//		String tokenResponseAsString = mockMvc
//			.perform(
//				post("/auth/token")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(params)))
//			.andExpect(status().isOk())
//			.andReturn().getResponse().getContentAsString();
//
//		Map<String, Object> tokenResponseAsMap =
//			mapper.readValue(tokenResponseAsString, new TypeReference<Map<String, Object>>() {});
//		String accessToken = (String) tokenResponseAsMap.get("accessToken");
//
//		// send request which "david" attempt to delete "john"
//		mockMvc
//			.perform(
//				delete("/member/john")
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isUnauthorized())
//			.andDo(print());
//
//	}

//	@Test
//	public void memberDeleteTestByAdmin() throws Exception {
//
//		// save member entity "john"
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		// save member entity "admin"
//		MemberVo admin = new MemberVo();
//		admin.setUsername("admin");
//		admin.setPassword("admin");
//		admin.setEmail("admin@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(admin)))
//			.andExpect(status().isOk());
//
//		// enhance admin authority to "ROLE_ADMIN"
//		mockMvc
//			.perform(
//				get("/member/enhance/admin"))
//			.andExpect(status().isOk());
//
//		// get access token by using "admin"
//		Map<String, Object> params = new HashMap<>();
//		params.put("username", admin.getUsername());
//		params.put("password", admin.getPassword());
//
//		String tokenResponseAsString = mockMvc
//			.perform(
//				post("/auth/token")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(params)))
//			.andExpect(status().isOk())
//			.andReturn().getResponse().getContentAsString();
//
//		Map<String, Object> tokenResponseAsMap =
//			mapper.readValue(tokenResponseAsString, new TypeReference<Map<String, Object>>() {});
//		String accessToken = (String) tokenResponseAsMap.get("accessToken");
//
//		// send request that admin attempt to delete member "john"
//		mockMvc
//			.perform(
//				delete("/member/john")
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isOk());
//
//		// confirm that member entity "john" is deleted.
//		mockMvc
//			.perform(
//				get("/member/john")
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isNoContent())
//			.andDo(print());
//	}

//	@Test
//	public void articleInsertTest() throws Exception {
//
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		Map<String, Object> params = new HashMap<>();
//		params.put("username", john.getUsername());
//		params.put("password", john.getPassword());
//
//		String tokenResponseAsString = mockMvc
//			.perform(
//				post("/auth/token")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(params)))
//			.andExpect(status().isOk())
//			.andReturn().getResponse().getContentAsString();
//
//		Map<String, Object> tokenResponseAsMap =
//			mapper.readValue(tokenResponseAsString, new TypeReference<Map<String, Object>>() {});
//		String accessToken = (String) tokenResponseAsMap.get("accessToken");
//
//		// save article
//		ArticleVo articleVo = new ArticleVo();
//		articleVo.setAuthor(john);
//		articleVo.setTitle("test title");
//		articleVo.setContent("test content");
//
//		String articleIdAsString = mockMvc
//			.perform(
//				post("/article")
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.content(mapper.writeValueAsString(articleVo))
//					.header("Authorization", "Bearer " + accessToken))
//				.andExpect(status().isOk())
//				.andReturn().getResponse().getContentAsString();
//
//		Long articleId = Long.parseLong(articleIdAsString);
//
//		// when if author is null?
//		articleVo.setAuthor(null);
//
//		mockMvc
//			.perform(
//				post("/article")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(articleVo))
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isBadRequest());
//
//		String articleResponseAsString = mockMvc
//			.perform(
//				get("/article/" + articleId))
//			.andExpect(status().isOk())
//			.andDo(print())
//			.andReturn().getResponse().getContentAsString();
//
//		ArticleVo savedArticleVo = mapper.readValue(articleResponseAsString, new TypeReference<ArticleVo>() {});
//
//		assertEquals(articleVo.getTitle(), savedArticleVo.getTitle());
//		assertEquals(articleVo.getContent(), savedArticleVo.getContent());
//		assertEquals(john.getUsername(), savedArticleVo.getAuthor().getUsername());
//
//	}

//	@Test
//	public void articleTestDeleteByDifferentMember() throws Exception {
//
//		// save member entity "john"
//		MemberVo john = new MemberVo();
//		john.setUsername("john");
//		john.setPassword("123456");
//		john.setEmail("john@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.content(mapper.writeValueAsString(john)))
//			.andExpect(status().isOk());
//
//		// save member entity "david"
//		MemberVo david = new MemberVo();
//		david.setUsername("david");
//		david.setPassword("123456");
//		david.setEmail("david@example.com");
//
//		mockMvc
//			.perform(
//				post("/member")
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.content(mapper.writeValueAsString(david)))
//			.andExpect(status().isOk());
//
//		// john insert one article.
//		Map<String, Object> params = new HashMap<>();
//		params.put("username", john.getUsername());
//		params.put("password", john.getPassword());
//
//		String tokenResponseAsString = mockMvc
//			.perform(
//				post("/auth/token")
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.content(mapper.writeValueAsString(params)))
//			.andExpect(status().isOk())
//			.andReturn().getResponse().getContentAsString();
//
//		Map<String, Object> tokenResponseAsMap =
//			mapper.readValue(tokenResponseAsString, new TypeReference<Map<String, Object>>() {});
//		String accessToken = (String) tokenResponseAsMap.get("accessToken");
//
//		ArticleVo articleVo = new ArticleVo();
//		articleVo.setAuthor(john);
//		articleVo.setTitle("test title");
//		articleVo.setContent("test content");
//
//		String articleIdAsString = mockMvc
//			.perform(
//				post("/article")
//					.contentType(MediaType.APPLICATION_JSON_UTF8)
//					.content(mapper.writeValueAsString(articleVo))
//					.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isOk())
//			.andReturn().getResponse().getContentAsString();
//
//		Long articleId = Long.parseLong(articleIdAsString);
//
//		// set PK and modify title
//		articleVo.setId(articleId);
//		articleVo.setTitle("modified title!!");
//
//		// attempt to modify article entity
//		mockMvc
//			.perform(
//				put("/article")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(mapper.writeValueAsString(articleVo))
//				.header("Authorization", "Bearer " + accessToken))
//			.andExpect(status().isOk());
//
//		String articleResponseAsString = mockMvc
//			.perform(
//				get("/article/" + articleId))
//			.andExpect(status().isOk())
//			.andDo(print())
//			.andReturn().getResponse().getContentAsString();
//
//		ArticleVo savedArticle =
//			mapper.readValue(articleResponseAsString, new TypeReference<ArticleVo>() {});
//
//		assertEquals(articleVo.getTitle(), savedArticle.getTitle());
//		assertEquals(articleVo.getContent(), savedArticle.getContent());
//		assertEquals(john.getUsername(), savedArticle.getAuthor().getUsername());
//
//	}

	@Test
	public void test() {

	}

}
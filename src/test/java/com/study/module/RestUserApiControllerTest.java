package com.study.module;


import com.study.RestDocumentConfig;
import com.study.module.user.adapter.input.RestUserApiController;
import com.study.module.user.application.port.input.UserFindQuery;
import com.study.module.user.domain.ExternalUserDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestUserApiController.class)
@AutoConfigureRestDocs
public class RestUserApiControllerTest extends RestDocumentConfig {

    private static String signUp = "/api/v1/user/signUp";
    private static String findUser = "/api/v1/user/find";
    private static String findId = "/api/v1/user/findId";

    @MockBean private UserFindQuery userFindQuery;
    @Test
    @DisplayName("회원 가입")
    void register() throws Exception {
        //given


        //when
        ResultActions actions = mockMvc.perform(
                post(signUp)
                        .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        actions.andDo(
                document(
                        "{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())
                )

        ).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원 정보 조회")
    void findUserInfo() throws Exception {
        ExternalUserDomain response = new ExternalUserDomain(
                "test13",
                "test13",
                "test13@naver.com",
                LocalDateTime.now(),
                null
        );

        //given
        given(userFindQuery.findOne("test13")).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                get(findUser)
                        .queryParam("userId", "test13")
        );

        //then
        actions.andDo(
                document(
                        "{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("userId").description("사용자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("userId").description("사용자 아이디"),
                                fieldWithPath("userName").description("사용자 이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("createDate").description("생성일자"),
                                fieldWithPath("modifyDate").description("수정일자")
                        )
                )
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("아이디 찾기")
    void findId() throws Exception {
        //given
        List<String> response = List.of("test13");

        given(userFindQuery.findUserIdByEmail("test13@naver.com")).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                get(findId)
                        .queryParam("email", "test13@naver.com")
        );

        //then
        actions.andDo(
                document(
                        "{class-name}/{method-name}",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("email").description("이메일")
                        ),
                        responseFields(
                                fieldWithPath("[]").description("사용자 아이디")
                        )

                )
        ).andExpect(status().isOk());
    }
}
